package com.example.hangar.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(ApiExceptionHandler.class)
@WebMvcTest(TestErrorController.class)
class ApiExceptionHandlerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnBadRequestWhenValidationFails() throws Exception {
        var body = """
            {
              "code": ""
            }
            """;

        mockMvc.perform(post("/test/errors/validation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.statusCode").value(400))
            .andExpect(jsonPath("$.reason").value("Bad Request"))
            .andExpect(jsonPath("$.message", startsWith("Validation failed for object='")))
            .andExpect(jsonPath("$.errors.code", hasItem("Code is required")))
            .andExpect(jsonPath("$.path").value("/test/errors/validation"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/test/errors/not-found"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.statusCode").value(404))
            .andExpect(jsonPath("$.reason").value("Not Found"))
            .andExpect(jsonPath("$.message").value("Resource not found: Department not found"))
            .andExpect(jsonPath("$.errors.global[0]").value("Entity not found"))
            .andExpect(jsonPath("$.path").value("/test/errors/not-found"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnConflict() throws Exception {
        mockMvc.perform(get("/test/errors/conflict"))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.statusCode").value(409))
            .andExpect(jsonPath("$.reason").value("Conflict"))
            .andExpect(jsonPath("$.message").value("Data integrity violation: duplicate key"))
            .andExpect(jsonPath("$.errors.global[0]").value("Operation violates a database constraint"))
            .andExpect(jsonPath("$.path").value("/test/errors/conflict"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnInternalServerError() throws Exception {
        mockMvc.perform(get("/test/errors/generic"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.statusCode").value(500))
            .andExpect(jsonPath("$.reason").value("Internal Server Error"))
            .andExpect(jsonPath("$.message").value("Unexpected internal error: boom"))
            .andExpect(jsonPath("$.errors.global[0]").value("An unexpected error occurred"))
            .andExpect(jsonPath("$.path").value("/test/errors/generic"))
            .andExpect(jsonPath("$.timestamp").exists());
    }
}
