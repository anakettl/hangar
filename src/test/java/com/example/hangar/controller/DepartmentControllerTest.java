package com.example.hangar.controller;

import com.example.hangar.dto.DepartmentCreateDTO;
import com.example.hangar.dto.DepartmentResponseDTO;
import com.example.hangar.dto.DepartmentUpdateDTO;
import com.example.hangar.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService service;

    @Test
    void listShouldReturnAllDepartments() throws Exception {
        var departments = List.of(
            new DepartmentResponseDTO(1L, "Financeiro", "FIN"),
            new DepartmentResponseDTO(2L, "Recursos Humanos", "RH_HR")
        );

        given(service.findAll()).willReturn(departments);

        mockMvc.perform(get("/api/departments").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Financeiro"))
            .andExpect(jsonPath("$[0].code").value("FIN"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Recursos Humanos"))
            .andExpect(jsonPath("$[1].code").value("RH_HR"));

        verify(service).findAll();
    }

    @Test
    void showShouldReturnDepartmentById() throws Exception {
        var response = new DepartmentResponseDTO(1L, "Financeiro", "FIN");

        given(service.findById(1L)).willReturn(response);

        mockMvc.perform(get("/api/departments/{id}", 1L).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Financeiro"))
            .andExpect(jsonPath("$.code").value("FIN"));

        verify(service).findById(1L);
    }

    @Test
    void createShouldReturnCreatedDepartment() throws Exception {
        var requestBody = """
                {
                  "name": "Tecnologia",
                  "code": "TIC"
                }
                """;

        var response = new DepartmentResponseDTO(1L, "Tecnologia", "TIC");

        given(service.save(any(DepartmentCreateDTO.class))).willReturn(response);

        mockMvc.perform(post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Tecnologia"))
            .andExpect(jsonPath("$.code").value("TIC"));

        verify(service).save(any(DepartmentCreateDTO.class));
    }

    @Test
    void updateShouldReturnUpdatedDepartment() throws Exception {
        var requestBody = """
                {
                  "name": "Recursos Humanos",
                  "code": "RH_HR"
                }
                """;

        var response = new DepartmentResponseDTO(1L, "Recursos Humanos", "RH_HR");

        given(service.update(eq(1L), any(DepartmentUpdateDTO.class))).willReturn(response);

        mockMvc.perform(put("/api/departments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Recursos Humanos"))
            .andExpect(jsonPath("$.code").value("RH_HR"));

        verify(service).update(eq(1L), any(DepartmentUpdateDTO.class));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/departments/{id}", 1L))
            .andExpect(status().isNoContent());

        verify(service).deleteById(1L);
    }

    @Test
    void createShouldReturnBadRequestWhenBodyIsInvalid() throws Exception {
        var invalidRequestBody = """
                {
                  "name": "",
                  "code": "A"
                }
                """;

        mockMvc.perform(post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
            .andExpect(status().isBadRequest());

        verify(service, never()).save(any());
    }
}
