package com.example.hangar.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "department")
@Data

public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String code;
}
