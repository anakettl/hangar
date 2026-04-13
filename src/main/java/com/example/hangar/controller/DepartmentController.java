package com.example.hangar.controller;

import com.example.hangar.dto.DepartmentCreateDTO;
import com.example.hangar.dto.DepartmentUpdateDTO;
import com.example.hangar.dto.DepartmentResponseDTO;
import com.example.hangar.model.Department;
import com.example.hangar.service.Departmentservice;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/departments", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

  private final DepartmentService service;

  public DepartmentController(DepartmentService service) {
      this.service = service;
  }

  @GetMapping
  public List<DepartmentResponseDTO> list() {
    return service.findAll()
            .stream()
            .map(dept -> new DepartmentResponseDTO(
                dept.getId(),
                dept.getName(),
                dept.getCode()
            ))
            .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<DepartmentResponseDTO> show(@PathVariable Long id) {
    return service.findById(id)
            .map(dept -> {
              DepartmentResponseDTO response = new DepartmentResponseDTO(
                dept.getId(),
                dept.getName(),
                dept.getCode()
              );
              return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<DepartmentResponseDTO> create(@RequestBody @Valid DepartmentCreateDTO dto) {
    Department department = new Department();
    department.setName(dto.name());
    department.setCode(dto.code());

    Department saved = service.save(department);

    DepartmentResponseDTO response = new DepartmentResponseDTO(
      saved.getId(),
      saved.getName(),
      saved.getCode()
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DepartmentResponseDTO> update(
          @PathVariable Long id,
          @RequestBody @Valid DepartmentUpdateDTO dto) {

    return service.findById(id)
            .map(existingDept -> {
              existingDept.setName(dto.name());
              existingDept.setCode(dto.code());

              Department updatedDepartment = service.save(existingDept);

              return ResponseEntity.ok(new DepartmentResponseDTO(
                updatedDepartment.getId(),
                updatedDepartment.getName(),
                updatedDepartment.getCode()
              ));
            })
            .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return service.findById(id)
            .map(dept -> {
              service.delete(dept);
              return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
  }
}
