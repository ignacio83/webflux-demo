package com.demo.webflux;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Data
@Document
public class Employee {
  private final Integer id;
  private final String name;
  private final Integer salary;
  private final Integer bossId;
}
