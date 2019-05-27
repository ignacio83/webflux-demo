package com.demo.webflux.controller.contract;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ExportContract {
  private final String absoluteFilepath;
}
