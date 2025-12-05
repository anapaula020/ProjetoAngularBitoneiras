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
    
    private Long fabricanteIdFromSql = 1L; 
    private Long tipoBetoneiraIdFromSql = 1L; 

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreateBetoneiraAsAdmin() {
        BetoneiraRequestDTO betoneira = new BetoneiraRequestDTO(
                "Betoneira Teste",
                "Descrição da Betoneira Teste",
                1234.50,
                5,
                tipoBetoneiraIdFromSql, 
                fabricanteIdFromSql     
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
                .statusCode(403); 
    }

    @Test
    public void testUpdateBetoneiraAsAdmin() {
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
                .extract().jsonPath().getLong("id"); 

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
                .body("id", is(betoneiraId.intValue())) 
                .body("nome", is("Betoneira Updated"))
                .body("preco", is(750.0F));
    }

    @Test
    public void testUpdateBetoneiraAsUserForbidden() {
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
                .extract().jsonPath().getLong("id"); 

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
                .statusCode(403); 
    }

    @Test
    public void testDeleteBetoneiraAsAdmin() {
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
                .extract().jsonPath().getLong("id"); 

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/betoneiras/" + betoneiraId)
                .then()
                .statusCode(204); 
    }

    @Test
    public void testDeleteBetoneiraAsUserForbidden() {
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
                .extract().jsonPath().getLong("id"); 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().delete("/betoneiras/" + betoneiraId)
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindAllBetoneiras() {
        given()
                .header("Authorization", "Bearer " + userToken) 
                .when().get("/betoneiras")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testFindBetoneiraById() {
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
                .extract().jsonPath().getLong("id"); 

        given()
                .header("Authorization", "Bearer " + userToken) 
                .when().get("/betoneiras/" + betoneiraId)
                .then()
                .statusCode(200)
                .body("id", is(betoneiraId.intValue())) 
                .body("nome", is("Betoneira Find By Id"));
    }

    @Test
    public void testFindBetoneiraByNome() {
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
                .header("Authorization", "Bearer " + userToken) 
                .when().get("/betoneiras/search/nome/Betoneira Pesquisa Nome")
                .then()
                .statusCode(200)
                .body("[0].nome", is("Betoneira Pesquisa Nome"));
    }
}