package com.example.boot.h2postprocessor.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.Date;
import javax.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
public class Student {
  @Column(name="id")
  private Long id;
  @Column(name="name")
  private String name;
  @Column(name="passport_number")
  private String passportNumber;
  @Column(name="my_date")
  private Date myDate;
  @Column(name="my_datetime")
  private Date myDateTime;


  @Column(name="is_student")
  private Boolean isStudent;

  public Student() {
    super();
  }

  public Student(Long id, String name, String passportNumber) {
    super();
    this.id = id;
    this.name = name;
    this.passportNumber = passportNumber;
  }

  public Student(String name, String passportNumber) {
    super();
    this.name = name;
    this.passportNumber = passportNumber;
  }

//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public String getPassportNumber() {
//    return passportNumber;
//  }
//
//  public void setPassportNumber(String passportNumber) {
//    this.passportNumber = passportNumber;
//  }

//  @Override
//  public String toString() {
//    return String.format("Student [id=%s, name=%s, passportNumber=%s]", id, name, passportNumber);
//  }
}
