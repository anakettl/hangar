package com.example.hangar.controller;

import com.example.hangar.dto.DepartmentCreateDTO;
import com.example.hangar.repository.DepartmentRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DepartmentControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private DepartmentRepository repository;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        repository.deleteAll();
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
            .statusCode(201);
    }
}
