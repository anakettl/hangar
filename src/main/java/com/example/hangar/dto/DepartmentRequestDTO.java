package com.example.hangar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentRequestDTO(
  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 100)
  String name,

  @NotBlank(message = "Code is required")
  @Size(min = 3, max = 10)
  String code
) {}