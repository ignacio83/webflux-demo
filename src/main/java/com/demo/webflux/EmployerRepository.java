package com.demo.webflux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends ReactiveMongoRepository<EmployerDocument, String> {}
