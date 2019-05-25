package com.demo.webflux;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Comparator;

@RequiredArgsConstructor
@Service
@Slf4j
class EmployeeService {
  private final EmployeeRepository repository;
  private final Scheduler costScheduler = Schedulers.newElastic("cost");
  private Disposable job;

  @PostConstruct
  public void startJob() {

    final Mono<Integer> totalCost =
        repository
            .findByBossIdIsNull()
            .map(Employee::getId)
            .flatMap(this::costByBoss)
            .reduce(0, Integer::sum);

    final Mono<Employee> employeeMostExpensive =
        repository
            .findByBossIdIsNull()
            .map(Employee::getId)
            .flatMap(this::findAllByBoss)
            .sort(Comparator.comparingInt(Employee::getSalary).reversed())
            .take(1)
            .singleOrEmpty();

    job =
        Flux.interval(Duration.ofSeconds(1))
            .flatMap(interval -> totalCost.zipWith(employeeMostExpensive))
            .subscribe(
                tuple -> {
                  final Integer cost = tuple.getT1();
                  final Employee expensive = tuple.getT2();
                  log.info(
                      "The total cost of employees is {}. The most expensive employer is {} ({})",
                      cost,
                      expensive.getName(),
                      expensive.getSalary());
                });
  }

  @PreDestroy
  public void stopJob() {
    if (!job.isDisposed()) {
      job.dispose();
    }
  }

  public Mono<Employee> save(Employee employee) {
    return Mono.just(employee)
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
        .doOnNext(employee -> log.trace("Employee {} found for boss {}", employee, id));
  }

  public Mono<Integer> costByBoss(Integer id) {
    return findAllByBoss(id)
        .map(Employee::getSalary)
        .publishOn(costScheduler)
        .reduce(0, Integer::sum)
        .doOnNext(cost -> log.trace("The total cost for boss {} is {}", id, cost));
  }
}
