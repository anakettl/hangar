package com.example.hangar.controller;

import com.example.hangar.dto.DepartmentRequestDTO;
import com.example.hangar.model.Department;
import com.example.hangar.repository.DepartmentRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/department")
public class DepartmentController {

  @Autowired
  private DepartmentRepository repository;

  @GetMapping
  public List<Department> list() {
    return repository.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Department create(@RequestBody @Valid DepartmentRequestDTO dto) {
    Department department = new Department();
    department.setName(dto.name());
    department.setCode(dto.code());

    return repository.save(department);
  }
}
