package com.example.boot.h2postprocessor;

import com.example.boot.h2postprocessor.entity.Student;
import com.example.boot.h2postprocessor.repo.StudentJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class H2PostprocessorApplication implements CommandLineRunner {

  @Autowired
  StudentJdbcRepository repository;
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public static void main(String[] args) {
    SpringApplication.run(H2PostprocessorApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    logger.info("Student id 10001 -> {}", repository.findById(10001L));
    logger.info("All users 1 -> {}", repository.findAll());
    logger.info("Inserting -> {}", repository.insert(new Student(10010L, "John", "A1234657")));
    logger.info("Update 10001 -> {}",
        repository.update(new Student(10001L, "Name-Updated", "New-Passport")));
    repository.deleteById(10002L);
    logger.info("All users 2 -> {}", repository.findAll());

  }
}
