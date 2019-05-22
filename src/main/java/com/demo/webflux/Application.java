package com.demo.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @RequiredArgsConstructor
  @RestController
  @RequestMapping("/employers")
  class Controller {
    private final EmployerRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmployerContract> create(@RequestBody Mono<CreateEmployerContract> employer) {
      return employer
          .map(contract -> new EmployerDocument(contract.getName(), contract.getSalary()))
          .flatMap(repository::save)
          .map(doc -> new EmployerContract(doc.getId(), doc.getName(), doc.getSalary()));
    }
  }
}
