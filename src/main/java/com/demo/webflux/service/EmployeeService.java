package com.demo.webflux.service;

import com.demo.webflux.domain.Employee;
import com.demo.webflux.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Comparator;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeService {
  private final EmployeeRepository repository;
  private Disposable job;

  @PostConstruct
  public void startJob() {
    job = Flux.interval(Duration.ofSeconds(30)).flatMap(interval -> statistics()).subscribe();
  }

  @PreDestroy
  public void stopJob() {
    if (!job.isDisposed()) {
      job.dispose();
    }
  }

  public Mono<Employee> save(Mono<Employee> employee) {
    return employee
        .flatMap(repository::save)
        .doOnSuccess(
            savedEmployee ->
                log.debug(
                    "Employee {} of boss {} saved.",
                    savedEmployee.getId(),
                    savedEmployee.getBossId()));
  }

  public Flux<Employee> findAll() {
    return repository.findAll();
  }

  public Flux<Employee> findDirectsByBoss(Integer id) {
    return repository.findByBossId(id);
  }

  public Flux<Employee> findAllByBoss(Integer id) {
    return repository
        .findById(id)
        .expand(employee -> repository.findByBossId(employee.getId()))
        .filter(employee -> !employee.getId().equals(id))
        .doOnNext(employee -> log.trace("Employee {} found for boss {}", employee.getName(), id));
  }

  public Mono<Integer> costByBoss(Integer id) {
    return findAllByBoss(id)
        .map(Employee::getSalary)
        .reduce(0, Integer::sum)
        .doOnNext(cost -> log.trace("The total cost for boss {} is {}", id, cost));
  }

  public Flux<String> export() {
    return findAll()
        .buffer(2)
        .flatMap(
            employees ->
                Mono.fromCallable(() -> FileExporter.execute(employees))
                    .publishOn(Schedulers.elastic()))
        .doOnNext(absolutePath -> log.info("Employees exported to file {}", absolutePath));
  }

  public Mono<Tuple2<Integer, Employee>> statistics() {
    final Flux<Employee> roots = repository.findByBossIdIsNull();

    final Mono<Integer> totalCost =
        roots.map(Employee::getId).flatMap(this::costByBoss).reduce(0, Integer::sum);

    final Mono<Employee> employeeWithHighestSalary =
        roots
            .map(Employee::getId)
            .flatMap(this::findAllByBoss)
            .sort(Comparator.comparingInt(Employee::getSalary).reversed())
            .take(1)
            .singleOrEmpty()
            .cache();

    return totalCost
        .zipWith(employeeWithHighestSalary)
        .doOnSuccess(
            tuple -> {
              final Integer cost = tuple.getT1();
              final Employee expensive = tuple.getT2();
              log.debug(
                  "The total cost of employees is {}. The most expensive employer is {} ({})",
                  cost,
                  expensive.getName(),
                  expensive.getSalary());
            });
  }
}
