package com.demo.webflux.repository;

import com.demo.webflux.domain.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, Integer> {
  Flux<Employee> findByBossIdIsNull();

  Flux<Employee> findByBossId(Integer bossId);
}
