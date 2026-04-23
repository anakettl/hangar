package com.example.hangar.controller;

import com.example.hangar.dto.DepartmentCreateDTO;
import com.example.hangar.dto.DepartmentResponseDTO;
import com.example.hangar.dto.DepartmentUpdateDTO;
import com.example.hangar.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/departments", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

  private final DepartmentService service;

  public DepartmentController(DepartmentService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<DepartmentResponseDTO>> list() {
    var response = service.findAll();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DepartmentResponseDTO> show(@PathVariable Long id) {
    var response = service.findById(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<DepartmentResponseDTO> create(@RequestBody @Valid DepartmentCreateDTO dto) {
    var response = service.save(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DepartmentResponseDTO> update(
      @PathVariable Long id, @RequestBody @Valid DepartmentUpdateDTO dto) {
    var response = service.update(id, dto);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
