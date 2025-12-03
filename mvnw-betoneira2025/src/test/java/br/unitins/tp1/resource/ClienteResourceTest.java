package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.EnderecoRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class ClienteResourceTest {

    private String adminToken;
    private String userToken;

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

   

    @Test
    public void testUpdateClienteAsUserSelf() {
        // User updates their own profile
        Long clienteId = 1L; // ID of "João Silva" from import.sql

        // Create a DTO for update with slightly different data
        EnderecoRequestDTO endereco = new EnderecoRequestDTO(
                "Rua do Usuário",
                "500",
                null,
                "Centro",
                "Porto Nacional",
                "TO",
                "77500000"
        );

        ClienteRequestDTO updatedCliente = new ClienteRequestDTO(
                "João User Update",
                "joao.user.update@example.com",
                "userupdatedpass",
                "11122233344", // CPF might not change
                LocalDate.of(1992, 7, 20),
                "63999997777",
                "MASCULINO",
                Arrays.asList(endereco)
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(updatedCliente)
                .when().put("/clientes/" + clienteId)
                .then()
                .statusCode(200)
                .body("id", is(clienteId.intValue())) // intValue for JSONPath comparison
                .body("nome", is("João User Update"))
                .body("email", is("joao.user.update@example.com"));
    }

  
    @Test
    public void testDeleteClienteAsAdmin() {
        // Create a new client to delete
        EnderecoRequestDTO endereco = new EnderecoRequestDTO(
                "Rua Delete",
                "1",
                null,
                "Centro",
                "Palmas",
                "TO",
                "77000000"
        );

        ClienteRequestDTO clienteToDelete = new ClienteRequestDTO(
                "Cliente a Deletar",
                "deletar@example.com",
                "deletesenha",
                "98765432109",
                LocalDate.of(2000, 1, 1),
                "63999995555",
                "MASCULINO",
                Arrays.asList(endereco)
        );

        Long idToDelete = given()
                .contentType(ContentType.JSON)
                .body(clienteToDelete)
                .when().post("/clientes")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); // Fixed extraction

        // Delete as admin
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/clientes/" + idToDelete)
                .then()
                .statusCode(204); // No Content
    }

   
}