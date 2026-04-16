package com.example.hangar.controller;

import com.example.hangar.dto.DepartmentCreateDTO;
import com.example.hangar.repository.DepartmentRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class DepartmentControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DepartmentRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        // Inicializa o RestAssured com o contexto do seu app
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @Test
    void shouldCreateDepartmentSuccessfully() {
        var request = new DepartmentCreateDTO("Logistica", "LOG");

        given()
            .contentType("application/json")
            .body(request)
        .when()
            .post("/api/departments")
        .then()
            .statusCode(201)
            .body("name", equalTo("Logistica"))
            .body("code", equalTo("LOG"))
            .body("id", notNullValue());
    }
}
