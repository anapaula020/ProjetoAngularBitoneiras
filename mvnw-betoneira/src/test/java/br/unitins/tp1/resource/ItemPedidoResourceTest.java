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
    private Long userAddressId = 1L; 
    private Long fabricanteIdFromSql = 1L; 
    private Long tipoBetoneiraIdFromSql = 1L; 

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreateItemPedidoAsAdmin() {
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Item Teste", "Desc item", 100.0, 10, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); 

        ItemPedidoRequestDTO itemPedido = new ItemPedidoRequestDTO(
                betoneiraId,
                2,
                100.0 
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
                1L, 
                1,
                50.0
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(itemPedido)
                .when().post("/itens-pedido")
                .then()
                .statusCode(403); 
    }

    @Test
    public void testUpdateItemPedidoAsAdmin() {
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Update Item", "Desc update", 200.0, 5, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); 

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
                .extract().jsonPath().getLong("id"); 

        ItemPedidoRequestDTO updatedItem = new ItemPedidoRequestDTO(
                betoneiraId,
                3,
                250.0 
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(updatedItem)
                .when().put("/itens-pedido/" + itemId)
                .then()
                .statusCode(200)
                .body("id", is(itemId.intValue())) 
                .body("quantidade", is(3))
                .body("precoUnitario", is(250.0F));
    }

    @Test
    public void testUpdateItemPedidoAsUserForbidden() {
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Update Forbidden", "Desc forbidden", 300.0, 1, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); 

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
                .extract().jsonPath().getLong("id"); 

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
                .statusCode(403); 
    }

    @Test
    public void testDeleteItemPedidoAsAdmin() {
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Delete Item", "Desc delete", 400.0, 1, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); 

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
                .extract().jsonPath().getLong("id"); 

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/itens-pedido/" + itemId)
                .then()
                .statusCode(204); 
    }

    @Test
    public void testDeleteItemPedidoAsUserForbidden() {
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira Delete Forbidden", "Desc delete forbidden", 500.0, 1, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); 

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
                .extract().jsonPath().getLong("id"); 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().delete("/itens-pedido/" + itemId)
                .then()
                .statusCode(403); 
    }


    @Test
    public void testFindAllItemPedidosAsAdmin() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/itens-pedido")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2)); 
    }

    @Test
    public void testFindAllItemPedidosAsUserForbidden() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/itens-pedido")
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindItemPedidoByIdAsAdmin() {
        Long itemId = 1L; 

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/itens-pedido/" + itemId)
                .then()
                .statusCode(200)
                .body("id", is(itemId.intValue())) 
                .body("quantidade", is(1));
    }

    @Test
    public void testFindItemPedidoByIdAsUserSelfOwned() {
        BetoneiraRequestDTO betoneiraDto = new BetoneiraRequestDTO(
                "Betoneira for User Item", "Desc user item", 600.0, 5, tipoBetoneiraIdFromSql, fabricanteIdFromSql
        );
        Long betoneiraId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(betoneiraDto)
                .when().post("/betoneiras")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id"); 


        ItemPedidoRequestDTO itemForPedido = new ItemPedidoRequestDTO(betoneiraId, 1, 600.0);
        PedidoRequestDTO pedidoDto = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemForPedido), userAddressId); 

        Long pedidoId = given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(pedidoDto)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); 

        Long itemPedidoId = given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos/" + pedidoId)
                .then().statusCode(200)
                .extract().jsonPath().getLong("itens[0].id"); 


        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/itens-pedido/" + itemPedidoId)
                .then()
                .statusCode(200)
                .body("id", is(itemPedidoId.intValue())) 
                .body("quantidade", is(1));
    }

    @Test
    public void testFindItemPedidoByIdAsUserOtherUserForbidden() {
        Long adminOwnedItemId = 3L; 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/itens-pedido/" + adminOwnedItemId)
                .then()
                .statusCode(403); 
    }
}