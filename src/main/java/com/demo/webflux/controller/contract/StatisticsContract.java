package com.demo.webflux.controller.contract;

import com.demo.webflux.domain.Employee;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class StatisticsContract {
  private final Integer totalCost;
  private final EmployeeContract highestSalary;

  public StatisticsContract(Integer totalCost, Employee domain) {
    this.totalCost = totalCost;
    this.highestSalary = new EmployeeContract(domain);
  }
}
