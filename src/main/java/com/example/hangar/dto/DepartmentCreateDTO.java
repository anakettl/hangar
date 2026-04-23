package com.example.hangar.dto;

import com.example.hangar.model.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentCreateDTO(
    @NotBlank(message = "Name is required") @Size(min = 3, max = 100) String name,
    @NotBlank(message = "Code is required") @Size(min = 3, max = 10) String code) {
  public Department toEntity() {
    return new Department(null, this.code, this.name);
  }
}
