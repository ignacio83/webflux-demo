package com.demo.webflux;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class EmployerContract {
  private final String id;
  private final String name;
  private final int salary;
}
