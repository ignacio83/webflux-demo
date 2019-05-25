package com.demo.webflux;

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
  public Mono<EmployeeContract> create(@RequestBody @Valid Mono<CreateEmployeeContract> employee) {
    return employee
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
}
