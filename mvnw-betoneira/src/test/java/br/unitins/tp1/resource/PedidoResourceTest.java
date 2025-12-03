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
    private Long userAddressId = 1L; // Assuming João Silva's address ID from import.sql
    private Long adminAddressId = 2L; // Assuming Maria Admin's address ID from import.sql


    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreatePedidoAsUser() {
        ItemPedidoRequestDTO item = new ItemPedidoRequestDTO(1L, 2, 1500.0); // Assuming Betoneira ID 1 exists
        PedidoRequestDTO pedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(item), userAddressId); // Pass address ID

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(pedido)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("totalPedido", is(3000.0F)); // 2 * 1500.0 = 3000.0
    }

    @Test
    public void testCreatePedidoAsAdminForbidden() {
        ItemPedidoRequestDTO item = new ItemPedidoRequestDTO(1L, 1, 100.0);
        PedidoRequestDTO pedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(item), adminAddressId); // Pass address ID

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pedido)
                .when().post("/pedidos")
                .then()
                .statusCode(403); // Admin cannot create order directly through this endpoint (only USER role is allowed in @RolesAllowed("USER"))
    }

    @Test
    public void testUpdatePedidoAsAdmin() {
        // Create a pedido as user first (using userToken)
        ItemPedidoRequestDTO itemOriginal = new ItemPedidoRequestDTO(1L, 1, 1500.0); // Using Betoneira ID 1 from import.sql
        PedidoRequestDTO originalPedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemOriginal), userAddressId); // Pass address ID

        Long pedidoId = given()
                .header("Authorization", "Bearer " + userToken) // Must use USER token to create order
                .contentType(ContentType.JSON)
                .body(originalPedido)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Update the pedido as admin. PedidoResource.update allows ADMIN.
        ItemPedidoRequestDTO itemUpdated = new ItemPedidoRequestDTO(2L, 2, 3000.0); // Assuming Betoneira ID 2 exists
        PedidoRequestDTO updatedPedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemUpdated), userAddressId); // Pass address ID

        given()
                .header("Authorization", "Bearer " + adminToken) // Use ADMIN token for update
                .contentType(ContentType.JSON)
                .body(updatedPedido)
                .when().put("/pedidos/" + pedidoId)
                .then()
                .statusCode(200) // Expect 200 OK
                .body("id", is(pedidoId.intValue())) // intValue for JSONPath comparison
                .body("totalPedido", is(6000.0F)); // 2 * 3000.0 = 6000.0
    }

   
        
    @Test
    public void testUpdatePedidoAsUserOtherUserForbidden() {
        // We'll rely on a pre-existing admin-owned order from import.sql (ID 3)
        Long adminPedidoId = 3L; // Pedido with ID 3 in import.sql is for admin@example.com

        ItemPedidoRequestDTO itemUpdated = new ItemPedidoRequestDTO(1L, 1, 1500.0);
        PedidoRequestDTO attemptedUpdate = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemUpdated), adminAddressId); // Pass admin address ID

        given()
                .header("Authorization", "Bearer " + userToken) // Authenticated as regular user
                .contentType(ContentType.JSON)
                .body(attemptedUpdate)
                .when().put("/pedidos/" + adminPedidoId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testDeletePedidoAsAdmin() {
        // Create a pedido as user first (using userToken) to delete it as admin
        ItemPedidoRequestDTO itemToDelete = new ItemPedidoRequestDTO(1L, 1, 1500.0);
        PedidoRequestDTO pedidoToDelete = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(itemToDelete), userAddressId); // Pass address ID

        Long pedidoId = given()
                .header("Authorization", "Bearer " + userToken) // Must use USER token to create order
                .contentType(ContentType.JSON)
                .body(pedidoToDelete)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Delete the pedido as admin. PedidoResource.delete allows ADMIN.
        given()
                .header("Authorization", "Bearer " + adminToken) // Use ADMIN token for delete
                .when().delete("/pedidos/" + pedidoId)
                .then()
                .statusCode(204); // Expect 204 No Content
    }

    

    @Test
    public void testDeletePedidoAsUserOtherUserForbidden() {
        // Try to delete an admin-owned order from import.sql (ID 3)
        Long adminPedidoId = 3L; // Pedido with ID 3 in import.sql is for admin@example.com

        given()
                .header("Authorization", "Bearer " + userToken) // Authenticated as regular user
                .when().delete("/pedidos/" + adminPedidoId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testFindAllPedidosAsAdmin() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/pedidos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2)); // From import.sql
    }

    @Test
    public void testFindAllPedidosAsUserForbidden() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos")
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testFindPedidoByIdAsUserSelfOwned() {
        // Use an ID from import.sql linked to joao@example.com (ID 1 or 2)
        Long userPedidoId = 1L; // Pedido with ID 1 in import.sql is for joao@example.com

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos/" + userPedidoId)
                .then()
                .statusCode(200)
                .body("id", is(userPedidoId.intValue())) // intValue for JSONPath comparison
                .body("clienteNome", is("João Silva"));
    }

    @Test
    public void testFindPedidoByIdAsUserOtherUserForbidden() {
        // Try to find an admin-owned order from import.sql (ID 3)
        Long adminPedidoId = 3L; // Pedido with ID 3 in import.sql is for admin@example.com

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos/" + adminPedidoId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testFindPedidoByIdAsAdminAnyOrder() {
        // Find a user's order (ID 1)
        Long userPedidoId = 1L; // Pedido with ID 1 in import.sql is for joao@example.com

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/pedidos/" + userPedidoId)
                .then()
                .statusCode(200)
                .body("id", is(userPedidoId.intValue())) // intValue for JSONPath comparison
                .body("clienteNome", is("João Silva"));
    }

    @Test
    public void testGetMyOrdersAsUser() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pedidos/meus-pedidos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1)); // At least one order from import.sql
    }
}