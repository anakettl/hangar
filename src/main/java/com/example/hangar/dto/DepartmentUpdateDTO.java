package com.example.hangar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentUpdateDTO(
  @NotBlank(message = "O nome não pode ser vazio")
  @Size(min = 3, max = 100)
  String name,

  @NotBlank(message = "O codigo não pode ser vazio")
  @Size(min = 3, max = 10)
  String code
) {}