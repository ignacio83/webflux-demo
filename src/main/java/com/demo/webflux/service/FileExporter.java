package com.demo.webflux.service;

import com.demo.webflux.domain.Employee;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
class FileExporter {

  public static String execute(List<Employee> employees) {
    final String userHomeDir = System.getProperty("user.home");
    final Path path;

    try {
      final Path baseDir = Paths.get(userHomeDir, "webflux-demo");
      if (!Files.exists(baseDir)) {
        Files.createDirectory(baseDir);
      }

      final String uuid = UUID.randomUUID().toString();
      final Path csvPath =
          Paths.get(baseDir.toAbsolutePath().toString(), String.format("export_%s.csv", uuid));
      path = Files.createFile(csvPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try (PrintWriter writer =
        new PrintWriter(Files.newBufferedWriter(path, Charset.forName("UTF-8")))) {
      writer.print("id");
      writer.print("|");
      writer.print("name");
      writer.print("|");
      writer.print("salary");
      writer.print("|");
      writer.print("bossId");
      writer.println();
      employees.forEach(
          employee -> {
            log.debug("Writing employee {}", employee.getName());
            writer.print(employee.getId());
            writer.print("|");
            writer.print(employee.getName());
            writer.print("|");
            writer.print(employee.getSalary());
            writer.print("|");
            writer.print(employee.getBossId());
            writer.println();
          });
      return path.toAbsolutePath().toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
