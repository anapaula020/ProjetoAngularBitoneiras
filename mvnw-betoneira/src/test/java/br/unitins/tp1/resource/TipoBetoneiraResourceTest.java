package br.unitins.tp1.resource;

import br.unitins.tp1.dto.TipoBetoneiraDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
public class TipoBetoneiraResourceTest {

    private String adminToken;
    private String userToken;

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreateTipoBetoneiraAsAdmin() {
        TipoBetoneiraDTO tipoBetoneira = new TipoBetoneiraDTO("Tipo Teste", "Descrição do Tipo Teste");

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(tipoBetoneira)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", is("Tipo Teste"));
    }

    @Test
    public void testCreateTipoBetoneiraAsUserForbidden() {
        TipoBetoneiraDTO tipoBetoneira = new TipoBetoneiraDTO("Tipo User Test", "Desc User Test");

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(tipoBetoneira)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testUpdateTipoBetoneiraAsAdmin() {
        // Create a tipoBetoneira first
        TipoBetoneiraDTO originalTipo = new TipoBetoneiraDTO("Tipo Original", "Original Desc");
        Long tipoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(originalTipo)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Update the tipoBetoneira
        TipoBetoneiraDTO updatedTipo = new TipoBetoneiraDTO("Tipo Atualizado", "Atualizada Desc");
        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(updatedTipo)
                .when().put("/tipos-betoneira/" + tipoId)
                .then()
                .statusCode(200)
                .body("id", is(tipoId.intValue())) // intValue for JSONPath comparison
                .body("nome", is("Tipo Atualizado"));
    }

    @Test
    public void testUpdateTipoBetoneiraAsUserForbidden() {
        // Create a tipoBetoneira as admin
        TipoBetoneiraDTO originalTipo = new TipoBetoneiraDTO("Tipo para Update User Test", "Desc");
        Long tipoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(originalTipo)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Attempt to update as a regular user
        TipoBetoneiraDTO updatedTipo = new TipoBetoneiraDTO("Tipo Tentativa User", "Desc User");
        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(updatedTipo)
                .when().put("/tipos-betoneira/" + tipoId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testDeleteTipoBetoneiraAsAdmin() {
        // Create a tipoBetoneira first
        TipoBetoneiraDTO tipoToDelete = new TipoBetoneiraDTO("Tipo a Deletar", "Desc Delete");
        Long tipoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(tipoToDelete)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Delete the tipoBetoneira
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/tipos-betoneira/" + tipoId)
                .then()
                .statusCode(204); // No Content
    }

    @Test
    public void testDeleteTipoBetoneiraAsUserForbidden() {
        // Create a tipoBetoneira as admin
        TipoBetoneiraDTO tipoToDelete = new TipoBetoneiraDTO("Tipo Delete User Test", "Desc");
        Long tipoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(tipoToDelete)
                .when().post("/tipos-betoneira")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Attempt to delete as a regular user
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().delete("/tipos-betoneira/" + tipoId)
                .then()
                .statusCode(403); // Forbidden
    }

    // Note: TipoBetoneiraService doesn't have findByNome method in resource.
    // So skipping findByNome test for TipoBetoneiraResource.
}