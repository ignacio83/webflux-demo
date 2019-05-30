package com.demo.webflux.controller;

import com.demo.webflux.controller.contract.CreateEmployeeContract;
import com.demo.webflux.controller.contract.EmployeeContract;
import com.demo.webflux.controller.contract.ExportContract;
import com.demo.webflux.controller.contract.StatisticsContract;
import com.demo.webflux.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
class EmployeeController {
  private final EmployeeService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<EmployeeContract> create(@RequestBody @Valid CreateEmployeeContract employee) {
    // TODO Gravar um funcionário (id, nome, salário e chefe).
    return Mono.empty();
  }

  @GetMapping
  public Flux<EmployeeContract> list() {
    // TODO Listar todos os funcionários.
    return Flux.empty();
  }

  @GetMapping("/{id}/all")
  public Flux<EmployeeContract> findAllByBoss(@PathVariable("id") Integer id) {
    // TODO Listar a árvore de funcionários a partir de um chefe.
    return Flux.empty();
  }

  @GetMapping("/{id}/all/cost")
  public Mono<Integer> costByBoss(@PathVariable("id") Integer id) {
    // TODO Calcular o valor total dos salários dos funcionários de um chefe
    return Mono.empty();
  }

  @PostMapping("/export")
  public Flux<ExportContract> export() {
    // TODO Exportar os funcionários para arquivos CSV, máximo de 2 funcionários por arquivo.
    return Flux.empty();
  }

  @GetMapping("/statistics")
  public Mono<StatisticsContract> statistics() {
    // TODO
    return Mono.empty();
  }
}
