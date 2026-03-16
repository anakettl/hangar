package com.example.hangar.controller;

import com.example.hangar.dto.DepartmentCreateDTO;
import com.example.hangar.dto.DepartmentUpdateDTO;
import com.example.hangar.dto.DepartmentResponseDTO;
import com.example.hangar.model.Department;
import com.example.hangar.repository.DepartmentRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/{id}")
  public ResponseEntity<DepartmentResponseDTO> show(@PathVariable Long id) {
    return repository.findById(id)
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

    Department saved = repository.save(department);

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

    return repository.findById(id)
            .map(existingDept -> {
              existingDept.setName(dto.name());
              existingDept.setCode(dto.code());

              Department updated_department = repository.save(existingDept);

              return ResponseEntity.ok(new DepartmentResponseDTO(
                updated_department.getId(),
                updated_department.getName(),
                updated_department.getCode()
              ));
            })
            .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return repository.findById(id)
            .map(dept -> {
              repository.delete(dept);
              return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
  }
}
