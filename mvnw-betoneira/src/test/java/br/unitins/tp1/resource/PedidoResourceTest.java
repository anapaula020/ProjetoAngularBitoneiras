package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.dto.PedidoRequestDTO;
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
public class PedidoResourceTest {

    private String adminToken;
    private String userToken;
    private Long userAddressId = 1L; 
    private Long adminAddressId = 2L; 


    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreatePedidoAsUser() {
        ItemPedidoRequestDTO item = new ItemPedidoRequestDTO(1L, 2, 1500.0); 
        PedidoRequestDTO pedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(item), userAddressId); 

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(pedido)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("totalPedido", is(3000.0F)); 
    }

    @Test
    public void testCreatePedidoAsAdminForbidden() {
        ItemPedidoRequestDTO item = new ItemPedidoRequestDTO(1L, 1, 100.0);
        PedidoRequestDTO pedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(item), adminAddressId); 

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pedido)
                .when().post("/pedidos")
                .then()
                .statusCode(403); 
    }

    @Test
    public void testUpdatePedidoAsAdmin() {
        ItemPedidoRequestDTO itemOriginal = new ItemPedidoRequestDTO(1L, 1, 1500.0); 
        PedidoRequestDTO originalPedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemOriginal), userAddressId); 

        Long pedidoId = given()
                .header("Authorization", "Bearer " + userToken) 
                .contentType(ContentType.JSON)
                .body(originalPedido)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        ItemPedidoRequestDTO itemUpdated = new ItemPedidoRequestDTO(2L, 2, 3000.0); 
        PedidoRequestDTO updatedPedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemUpdated), userAddressId); 

        given()
                .header("Authorization", "Bearer " + adminToken) 
                .contentType(ContentType.JSON)
                .body(updatedPedido)
                .when().put("/pedidos/" + pedidoId)
                .then()
                .statusCode(200) 
                .body("id", is(pedidoId.intValue())) 
                .body("totalPedido", is(6000.0F)); 
    }

   
    @Test
    public void testUpdatePedidoAsUserOtherUserForbidden() {
        Long adminPedidoId = 3L; 

        ItemPedidoRequestDTO itemUpdated = new ItemPedidoRequestDTO(1L, 1, 1500.0);
        PedidoRequestDTO attemptedUpdate = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemUpdated), adminAddressId); 

        given()
                .header("Authorization", "Bearer " + userToken) 
                .contentType(ContentType.JSON)
                .body(attemptedUpdate)
                .when().put("/pedidos/" + adminPedidoId)
                .then()
                .statusCode(403); 
    }

    @Test
    public void testDeletePedidoAsAdmin() {
        ItemPedidoRequestDTO itemToDelete = new ItemPedidoRequestDTO(1L, 1, 1500.0);
        PedidoRequestDTO pedidoToDelete = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemToDelete), userAddressId); 

        Long pedidoId = given()
                .header("Authorization", "Bearer " + userToken) 
                .contentType(ContentType.JSON)
                .body(pedidoToDelete)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .header("Authorization", "Bearer " + adminToken) 
                .when().delete("/pedidos/" + pedidoId)
                .then()
                .statusCode(204); 
    }

    

    @Test
    public void testDeletePedidoAsUserOtherUserForbidden() {
        Long adminPedidoId = 3L; 

        given()
                .header("Authorization", "Bearer " + userToken) 
                .when().delete("/pedidos/" + adminPedidoId)
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindAllPedidosAsAdmin() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/pedidos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2)); 
    }

    @Test
    public void testFindAllPedidosAsUserForbidden() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos")
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindPedidoByIdAsUserSelfOwned() {
        Long userPedidoId = 1L; 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos/" + userPedidoId)
                .then()
                .statusCode(200)
                .body("id", is(userPedidoId.intValue())) 
                .body("clienteNome", is("João Silva"));
    }

    @Test
    public void testFindPedidoByIdAsUserOtherUserForbidden() {
        Long adminPedidoId = 3L; 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos/" + adminPedidoId)
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindPedidoByIdAsAdminAnyOrder() {
        Long userPedidoId = 1L; 

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/pedidos/" + userPedidoId)
                .then()
                .statusCode(200)
                .body("id", is(userPedidoId.intValue())) 
                .body("clienteNome", is("João Silva"));
    }

    @Test
    public void testGetMyOrdersAsUser() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos/meus-pedidos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1)); 
    }
}