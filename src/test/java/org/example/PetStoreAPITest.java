package org.example;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class PetStoreAPITest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void addPet() {
        String requestBody = "{ \"id\": 11, \"name\": \"Fluffy\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);
    }

    @Test
    public void getPetById() {
        given()
                .when()
                .get("/pet/11")
                .then()
                .statusCode(200)
                .body("name", equalTo("Fluffy"));
    }

    @Test
    public void getNonExistentPet() {
        given()
                .when()
                .get("/pet/999")
                .then()
                .statusCode(404);
    }

    @Test
    public void getPetByHugeId() {
        given()
                .when()
                .get("/pet/999999999999")
                .then()
                .statusCode(404);
    }

    @Test
    public void updatePet() {
        String requestBody = "{ \"id\": 11, \"name\": \"fluffy\", \"status\": \"sold\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/pet/11")
                .then()
                .statusCode(200)
                .body("status", equalTo("sold"));
    }

    @Test
    public void deletePet() {
        String requestBody = "{ \"id\": 12, \"name\": \"pluffy\" }";
        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        given()
                .when()
                .delete("/pet/12")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/pet/12")
                .then()
                .statusCode(404);
    }
}
