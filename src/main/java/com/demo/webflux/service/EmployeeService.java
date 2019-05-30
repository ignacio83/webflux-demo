package com.demo.webflux.service;

import com.demo.webflux.domain.Employee;
import com.demo.webflux.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeService {
  private final EmployeeRepository repository;

  public Mono<Employee> save(Mono<Employee> employee) {
    // TODO Gravar um funcionário (id, nome, salário e chefe).
    return Mono.empty();
  }

  public Flux<Employee> findAll() {
    // TODO Listar todos os funcionários.
    return Flux.empty();
  }

  public Flux<Employee> findAllByBoss(Integer id) {
    // TODO Listar a árvore de funcionários a partir de um chefe.
    return Flux.empty();
  }

  public Mono<Integer> costByBoss(Integer id) {
    // TODO Calcular o valor total dos salários dos funcionários de um chefe
    return Mono.empty();
  }

  public Flux<String> export() {
    // TODO Exportar os funcionários para arquivos CSV, máximo de 2 funcionários por arquivo.
    return Flux.empty();
  }
}
