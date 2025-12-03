package br.unitins.tp1.resource;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.TipoBetoneiraResponseDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
public class ItemPedidoResourceTest {

    private String adminToken;
    private String userToken;
    private Long userAddressId = 1L; // Assuming João Silva's address ID from import.sql
    private Long fabricanteIdFromSql = 1L; // "Fabricante A" from import.sql
    private Long tipoBetoneiraIdFromSql = 1L; // "Pequena" from import.sql

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreateItemPedidoAsAdmin() {
        // Use fixed IDs from import.sql for existing Fabricante and TipoBetoneira
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Item Teste", "Desc item", 100.0, 10, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        ItemPedidoRequestDTO itemPedido = new ItemPedidoRequestDTO(
                betoneiraId,
                2,
                100.0 // price per unit
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(itemPedido)
                .when().post("/itens-pedido")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("quantidade", is(2))
                .body("precoUnitario", is(100.0F));
    }

    @Test
    public void testCreateItemPedidoAsUserForbidden() {
        ItemPedidoRequestDTO itemPedido = new ItemPedidoRequestDTO(
                1L, // Assuming valid Betoneira ID
                1,
                50.0
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(itemPedido)
                .when().post("/itens-pedido")
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testUpdateItemPedidoAsAdmin() {
        // Create a betoneira
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Update Item", "Desc update", 200.0, 5, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Create an itemPedido first (as admin)
        ItemPedidoRequestDTO originalItem = new ItemPedidoRequestDTO(
                betoneiraId,
                1,
                200.0
        );
        Long itemId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(originalItem)
                .when().post("/itens-pedido")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Update the itemPedido
        ItemPedidoRequestDTO updatedItem = new ItemPedidoRequestDTO(
                betoneiraId,
                3,
                250.0 // updated price per unit
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(updatedItem)
                .when().put("/itens-pedido/" + itemId)
                .then()
                .statusCode(200)
                .body("id", is(itemId.intValue())) // intValue for JSONPath comparison
                .body("quantidade", is(3))
                .body("precoUnitario", is(250.0F));
    }

    @Test
    public void testUpdateItemPedidoAsUserForbidden() {
        // Create a betoneira
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Update Forbidden", "Desc forbidden", 300.0, 1, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Create an itemPedido first (as admin)
        ItemPedidoRequestDTO originalItem = new ItemPedidoRequestDTO(
                betoneiraId,
                1,
                300.0
        );
        Long itemId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(originalItem)
                .when().post("/itens-pedido")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Attempt to update as a regular user
        ItemPedidoRequestDTO attemptedUpdate = new ItemPedidoRequestDTO(
                betoneiraId,
                5,
                350.0
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(attemptedUpdate)
                .when().put("/itens-pedido/" + itemId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testDeleteItemPedidoAsAdmin() {
        // Create a betoneira
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Delete Item", "Desc delete", 400.0, 1, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Create an itemPedido first (as admin)
        ItemPedidoRequestDTO itemToDelete = new ItemPedidoRequestDTO(
                betoneiraId,
                1,
                400.0
        );
        Long itemId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(itemToDelete)
                .when().post("/itens-pedido")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Delete the itemPedido
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/itens-pedido/" + itemId)
                .then()
                .statusCode(204); // No Content
    }

    @Test
    public void testDeleteItemPedidoAsUserForbidden() {
        // Create a betoneira
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Delete Forbidden", "Desc delete forbidden", 500.0, 1, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Create an itemPedido first (as admin)
        ItemPedidoRequestDTO itemToDelete = new ItemPedidoRequestDTO(
                betoneiraId,
                1,
                500.0
        );
        Long itemId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(itemToDelete)
                .when().post("/itens-pedido")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Attempt to delete as a regular user
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().delete("/itens-pedido/" + itemId)
                .then()
                .statusCode(403); // Forbidden
    }


    @Test
    public void testFindAllItemPedidosAsAdmin() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/itens-pedido")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2)); // From import.sql or previous tests
    }

    @Test
    public void testFindAllItemPedidosAsUserForbidden() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/itens-pedido")
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testFindItemPedidoByIdAsAdmin() {
        // Use an ID from import.sql or create one
        Long itemId = 1L; // ItemPedido with ID 1 from import.sql

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/itens-pedido/" + itemId)
                .then()
                .statusCode(200)
                .body("id", is(itemId.intValue())) // intValue for JSONPath comparison
                .body("quantidade", is(1));
    }

    @Test
    public void testFindItemPedidoByIdAsUserSelfOwned() {
        // Create a Betoneira (as admin, as user cannot create it directly)
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira for User Item", "Desc user item", 600.0, 5, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction


        // Create a Pedido for the user that contains this item
        ItemPedidoRequestDTO itemForPedido = new ItemPedidoRequestDTO(betoneiraId, 1, 600.0);
        PedidoRequestDTO pedidoDto = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemForPedido), userAddressId); // Pass address ID

        Long pedidoId = given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(pedidoDto)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Now, find the itemPedido ID within this newly created pedido
        // Assuming there's a way to get item IDs from PedidoResponseDTO or query directly
        // For now, let's just assume the first item of the created order
        Long itemPedidoId = given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos/" + pedidoId)
                .then().statusCode(200)
                .extract().jsonPath().getLong("itens[0].id"); // Fixed extraction


        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/itens-pedido/" + itemPedidoId)
                .then()
                .statusCode(200)
                .body("id", is(itemPedidoId.intValue())) // intValue for JSONPath comparison
                .body("quantidade", is(1));
    }

    @Test
    public void testFindItemPedidoByIdAsUserOtherUserForbidden() {
        // Try to access an itemPedido that belongs to the admin's order (ID 3 in import.sql)
        Long adminOwnedItemId = 3L; // ItemPedido with ID 3 from import.sql is linked to Pedido ID 3, which is admin's

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/itens-pedido/" + adminOwnedItemId)
                .then()
                .statusCode(403); // Forbidden
    }
}