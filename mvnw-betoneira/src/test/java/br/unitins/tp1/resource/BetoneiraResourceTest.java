package br.unitins.tp1.resource;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.dto.TipoBetoneiraResponseDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class BetoneiraResourceTest {

    private String adminToken;
    private String userToken;
    // Using fixed IDs from import.sql for stable references
    private Long fabricanteIdFromSql = 1L; // "Fabricante A" from import.sql
    private Long tipoBetoneiraIdFromSql = 1L; // "Pequena" from import.sql

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreateBetoneiraAsAdmin() {
        // Use fixed IDs from import.sql for existing Fabricante and TipoBetoneira
        // No need to fetch them explicitly if their IDs are known and stable from import.sql.
        BetoneiraRequestDTO betoneira = new BetoneiraRequestDTO(
                "Betoneira Teste",
                "Descrição da Betoneira Teste",
                1234.50,
                5,
                tipoBetoneiraIdFromSql, // Using fixed ID
                fabricanteIdFromSql     // Using fixed ID
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneira)
                .when().post("/betoneiras")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", is("Betoneira Teste"))
                .body("preco", is(1234.50F));
    }

    @Test
    public void testCreateBetoneiraAsUserForbidden() {
        BetoneiraRequestDTO betoneira = new BetoneiraRequestDTO(
                "Betoneira User Test",
                "Descrição da Betoneira User Test",
                100.00,
                1,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(betoneira)
                .when().post("/betoneiras")
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testUpdateBetoneiraAsAdmin() {
        // Create a betoneira first
        BetoneiraRequestDTO originalBetoneira = new BetoneiraRequestDTO(
                "Betoneira Original",
                "Original description",
                500.00,
                3,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(originalBetoneira)
                .when().post("/betoneiras")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Update the betoneira
        BetoneiraRequestDTO updatedBetoneira = new BetoneiraRequestDTO(
                "Betoneira Updated",
                "Updated description",
                750.00,
                8,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(updatedBetoneira)
                .when().put("/betoneiras/" + betoneiraId)
                .then()
                .statusCode(200)
                .body("id", is(betoneiraId.intValue())) // intValue for comparison in JSONPath
                .body("nome", is("Betoneira Updated"))
                .body("preco", is(750.0F));
    }

    @Test
    public void testUpdateBetoneiraAsUserForbidden() {
        // Create a betoneira as admin
        BetoneiraRequestDTO originalBetoneira = new BetoneiraRequestDTO(
                "Betoneira for Update Test",
                "Description",
                1000.00,
                2,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(originalBetoneira)
                .when().post("/betoneiras")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Attempt to update as a regular user should be forbidden
        BetoneiraRequestDTO updatedBetoneira = new BetoneiraRequestDTO(
                "Betoneira Updated By User Attempt",
                "Updated by user",
                900.00,
                1,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(updatedBetoneira)
                .when().put("/betoneiras/" + betoneiraId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testDeleteBetoneiraAsAdmin() {
        // Create a betoneira first
        BetoneiraRequestDTO betoneira = new BetoneiraRequestDTO(
                "Betoneira to Delete",
                "Description to delete",
                200.00,
                1,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneira)
                .when().post("/betoneiras")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Delete the betoneira
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/betoneiras/" + betoneiraId)
                .then()
                .statusCode(204); // No Content
    }

    @Test
    public void testDeleteBetoneiraAsUserForbidden() {
        // Create a betoneira as admin
        BetoneiraRequestDTO betoneira = new BetoneiraRequestDTO(
                "Betoneira to Delete by User Attempt",
                "Description",
                300.00,
                1,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneira)
                .when().post("/betoneiras")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Attempt to delete as a regular user should be forbidden
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().delete("/betoneiras/" + betoneiraId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testFindAllBetoneiras() {
        given()
                .header("Authorization", "Bearer " + userToken) // User can also list
                .when().get("/betoneiras")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testFindBetoneiraById() {
        // Create a betoneira as admin to ensure it exists
        BetoneiraRequestDTO betoneira = new BetoneiraRequestDTO(
                "Betoneira Find By Id",
                "Description for find by id",
                450.00,
                2,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneira)
                .when().post("/betoneiras")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        given()
                .header("Authorization", "Bearer " + userToken) // User can also find by ID
                .when().get("/betoneiras/" + betoneiraId)
                .then()
                .statusCode(200)
                .body("id", is(betoneiraId.intValue())) // intValue for comparison in JSONPath
                .body("nome", is("Betoneira Find By Id"));
    }

    @Test
    public void testFindBetoneiraByNome() {
        // Create a betoneira as admin to ensure it exists
        BetoneiraRequestDTO betoneira = new BetoneiraRequestDTO(
                "Betoneira Pesquisa Nome",
                "Description for name search",
                600.00,
                1,
                tipoBetoneiraIdFromSql,
                fabricanteIdFromSql
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneira)
                .when().post("/betoneiras")
                .then()
                .statusCode(201);

        given()
                .header("Authorization", "Bearer " + userToken) // User can also find by name
                .when().get("/betoneiras/search/nome/Betoneira Pesquisa Nome")
                .then()
                .statusCode(200)
                .body("[0].nome", is("Betoneira Pesquisa Nome"));
    }
}