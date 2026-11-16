package ru.Alexander.NauJava.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskRestControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.authentication = RestAssured.basic("user1", "123");
    }

    @Test
    void getTasksByCriteria_ShouldReturn200() {
        given()
                .queryParam("keyword", "тест")
                .queryParam("status", "TODO")
                .when()
                .get("/api/tasks/search/criteria")
                .then()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    void getNonExistentPath_ShouldReturn404() {
        given()
                .when()
                .get("/api/tasks/non-existent-path")
                .then()
                .statusCode(anyOf(is(404), is(403)));
    }
}
