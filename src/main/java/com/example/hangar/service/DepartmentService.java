package com.example.hangar.service;

import com.example.hangar.dto.DepartmentCreateDTO;
import com.example.hangar.dto.DepartmentResponseDTO;
import com.example.hangar.dto.DepartmentUpdateDTO;
import com.example.hangar.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
  private final DepartmentRepository repository;

  public DepartmentService(DepartmentRepository repository) {
    this.repository = repository;
  }

  public List<DepartmentResponseDTO> findAll() {
    var response = this.repository.findAll();

    return response.stream().map(DepartmentResponseDTO::fromEntity).toList();
  }

  public DepartmentResponseDTO findById(Long id) {
    var response = this.repository.findById(id);

    return response
        .map(DepartmentResponseDTO::fromEntity)
        .orElseThrow(EntityNotFoundException::new);
  }

  public DepartmentResponseDTO save(DepartmentCreateDTO dto) {
    var response = this.repository.save(dto.toEntity());

    return DepartmentResponseDTO.fromEntity(response);
  }

  public DepartmentResponseDTO update(Long id, DepartmentUpdateDTO dto) {
    var entity = this.repository.findById(id);
    var response = entity.orElseThrow(EntityNotFoundException::new);

    dto.updateEntity(response);
    this.repository.save(response);

    return DepartmentResponseDTO.fromEntity(response);
  }

  public void deleteById(Long id) {
    this.repository.deleteById(id);
  }
}
