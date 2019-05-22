package com.demo.webflux;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateEmployerContract {
  private final String name;
  private final int salary;
}
