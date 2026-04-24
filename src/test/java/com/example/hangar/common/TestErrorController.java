package com.example.hangar.common;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/errors")
class TestErrorController {

    @PostMapping("/validation")
    String validation(@RequestBody @Valid InputDTO input) {
        return "ok";
    }

    @GetMapping("/not-found")
    String notFound() {
        throw new EntityNotFoundException("Department not found");
    }

    @GetMapping("/conflict")
    String conflict() {
        throw new DataIntegrityViolationException("duplicate key");
    }

    @GetMapping("/generic")
    String generic() {
        throw new RuntimeException("boom");
    }

    record InputDTO(
        @NotBlank(message = "Code is required")
        @Size(min = 3, max = 10)
        String code
    ) {
    }
}
