package com.demo.webflux.service;

import com.demo.webflux.domain.Employee;
import com.demo.webflux.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeService {
  private final EmployeeRepository repository;
  private Disposable job;

  public Mono<Employee> save(Mono<Employee> employee) {
    // TODO
    return Mono.empty();
  }

  public Flux<Employee> findAll() {
    // TODO
    return Flux.empty();
  }

  public Flux<Employee> findAllByBoss(Integer id) {
    // TODO
    return Flux.empty();
  }

  public Flux<String> export() {
    // TODO
    return Flux.empty();
  }

  public Mono<Integer> costByBoss(Integer id) {
    // TODO
    return Mono.empty();
  }
}
