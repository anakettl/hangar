package com.example.hangar.dto;

import com.example.hangar.model.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentUpdateDTO(
  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 100)
  String name,

  @NotBlank(message = "Code is required")
  @Size(min = 3, max = 10)
  String code
) {
  public void updateEntity(Department entity) {
    entity.setName(this.name);
    entity.setCode(this.code);
  }
}
