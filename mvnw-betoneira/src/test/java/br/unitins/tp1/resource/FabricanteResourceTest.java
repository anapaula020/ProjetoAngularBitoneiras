package br.unitins.tp1.resource;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
public class FabricanteResourceTest {

    private String adminToken;
    private String userToken;

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreateFabricanteAsAdmin() {
        FabricanteRequestDTO fabricante = new FabricanteRequestDTO("Fabricante Teste");

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(fabricante)
                .when().post("/fabricantes")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", is("Fabricante Teste"));
    }

    @Test
    public void testCreateFabricanteAsUserForbidden() {
        FabricanteRequestDTO fabricante = new FabricanteRequestDTO("Fabricante User Test");

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(fabricante)
                .when().post("/fabricantes")
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testUpdateFabricanteAsAdmin() {
        // Create a fabricante first
        FabricanteRequestDTO originalFabricante = new FabricanteRequestDTO("Fabricante Original");
        Long fabricanteId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(originalFabricante)
                .when().post("/fabricantes")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Update the fabricante
        FabricanteRequestDTO updatedFabricante = new FabricanteRequestDTO("Fabricante Atualizado");
        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(updatedFabricante)
                .when().put("/fabricantes/" + fabricanteId)
                .then()
                .statusCode(200)
                .body("id", is(fabricanteId.intValue())) // intValue for JSONPath comparison
                .body("nome", is("Fabricante Atualizado"));
    }

    @Test
    public void testUpdateFabricanteAsUserForbidden() {
        // Create a fabricante as admin
        FabricanteRequestDTO originalFabricante = new FabricanteRequestDTO("Fabricante para Update User Test");
        Long fabricanteId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(originalFabricante)
                .when().post("/fabricantes")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Attempt to update as a regular user
        FabricanteRequestDTO updatedFabricante = new FabricanteRequestDTO("Fabricante Tentativa User");
        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(updatedFabricante)
                .when().put("/fabricantes/" + fabricanteId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testDeleteFabricanteAsAdmin() {
        // Create a fabricante first
        FabricanteRequestDTO fabricanteToDelete = new FabricanteRequestDTO("Fabricante a Deletar");
        Long fabricanteId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(fabricanteToDelete)
                .when().post("/fabricantes")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Delete the fabricante
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/fabricantes/" + fabricanteId)
                .then()
                .statusCode(204); // No Content
    }

    @Test
    public void testDeleteFabricanteAsUserForbidden() {
        // Create a fabricante as admin
        FabricanteRequestDTO fabricanteToDelete = new FabricanteRequestDTO("Fabricante Delete User Test");
        Long fabricanteId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(fabricanteToDelete)
                .when().post("/fabricantes")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Attempt to delete as a regular user
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().delete("/fabricantes/" + fabricanteId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testFindAllFabricantes() {
        given()
                .header("Authorization", "Bearer " + userToken) // User can also list
                .when().get("/fabricantes")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2)); // At least 2 from import.sql
    }

    @Test
    public void testFindFabricanteById() {
        // Find an existing one from import.sql
        Long fabricanteId = 1L; // Assuming ID 1 exists from import.sql

        given()
                .header("Authorization", "Bearer " + userToken) // User can also find by ID
                .when().get("/fabricantes/" + fabricanteId)
                .then()
                .statusCode(200)
                .body("id", is(fabricanteId.intValue())) // intValue for JSONPath comparison
                .body("nome", is("Fabricante A"));
    }

    @Test
    public void testFindFabricanteByNome() {
        // Find an existing one from import.sql
        given()
                .header("Authorization", "Bearer " + userToken) // User can also find by name
                .when().get("/fabricantes/search/nome/Fabricante A")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].nome", is("Fabricante A"));
    }
}