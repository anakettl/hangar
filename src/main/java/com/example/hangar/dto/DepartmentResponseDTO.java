package com.example.hangar.dto;

import com.example.hangar.model.Department;

public record DepartmentResponseDTO(Long id, String name, String code) {
  public static DepartmentResponseDTO fromEntity(Department entity) {
    return new DepartmentResponseDTO(entity.getId(), entity.getName(), entity.getCode());
  }
}
