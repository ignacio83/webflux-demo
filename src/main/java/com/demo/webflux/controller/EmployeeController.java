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
    return Mono.just(employee)
        .map(CreateEmployeeContract::toDomain)
        .flatMap(service::save)
        .map(EmployeeContract::new);
  }

  @GetMapping
  public Flux<EmployeeContract> list() {
    return service.findAll().map(EmployeeContract::new);
  }

  @GetMapping("/{id}/directs")
  public Flux<EmployeeContract> directs(@PathVariable("id") Integer id) {
    return service.findDirectsByBoss(id).map(EmployeeContract::new);
  }

  @GetMapping("/{id}/directs/count")
  public Mono<Long> directsCount(@PathVariable("id") Integer id) {
    return service.findDirectsByBoss(id).count();
  }

  @GetMapping("/{id}/all")
  public Flux<EmployeeContract> findAllByBoss(@PathVariable("id") Integer id) {
    return service.findAllByBoss(id).map(EmployeeContract::new);
  }

  @GetMapping("/{id}/all/count")
  public Mono<Long> countAllByBoss(@PathVariable("id") Integer id) {
    return service.findAllByBoss(id).count();
  }

  @GetMapping("/{id}/all/cost")
  public Mono<Integer> listAllByIdx(@PathVariable("id") Integer id) {
    return service.costByBoss(id);
  }

  @GetMapping("/statistics")
  public Mono<StatisticsContract> statistics() {
    return service.statistics().map(tuple -> new StatisticsContract(tuple.getT1(), tuple.getT2()));
  }

  @PostMapping("/export")
  public Flux<ExportContract> export() {
    return service.export().map(ExportContract::new);
  }
}
