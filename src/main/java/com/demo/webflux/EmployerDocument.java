package com.demo.webflux;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Data
@Document
public class EmployerDocument {
  private String id;
  private final String name;
  private final int salary;
}
