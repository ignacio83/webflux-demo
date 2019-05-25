package com.demo.webflux;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class CreateEmployeeContract {

  @NotNull private final Integer id;

  @NotEmpty private final String name;

  @NotNull private final Integer salary;

  private final Integer bossId;

  public Employee toDomain() {
    return new Employee(id, name, salary, bossId);
  }
}
