package com.demo.webflux.controller.contract;

import com.demo.webflux.domain.Employee;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class EmployeeContract {
  private final Integer id;
  private final String name;
  private final Integer salary;
  private final Integer bossId;

  public EmployeeContract(Employee domain) {
    this.id = domain.getId();
    this.name = domain.getName();
    this.salary = domain.getSalary();
    this.bossId = domain.getBossId();
  }

  public Employee toDomain() {
    return new Employee(id, name, salary, bossId);
  }
}
