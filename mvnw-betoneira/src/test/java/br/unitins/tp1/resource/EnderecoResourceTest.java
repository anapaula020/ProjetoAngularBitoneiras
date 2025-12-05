package br.unitins.tp1.resource;

import br.unitins.tp1.dto.EnderecoRequestDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
public class EnderecoResourceTest {

    private String adminToken;
    private String userToken;

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");
    }

    @Test
    public void testCreateEnderecoAsUser() {
        EnderecoRequestDTO endereco = new EnderecoRequestDTO(
                "Rua do Cliente Teste",
                "123B",
                "Fundos",
                "Setor Central",
                "Palmas",
                "TO",
                "77000100"
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(endereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("logradouro", is("Rua do Cliente Teste"));
    }

    @Test
    public void testCreateEnderecoAsAdmin() {
        EnderecoRequestDTO endereco = new EnderecoRequestDTO(
                "Avenida do Admin",
                "456",
                "Prédio",
                "Centro",
                "Araguaina",
                "TO",
                "77800000"
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(endereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("logradouro", is("Avenida do Admin"));
    }

    @Test
    public void testUpdateEnderecoAsUserSelf() {
        EnderecoRequestDTO newEndereco = new EnderecoRequestDTO(
                "Rua para Atualizar",
                "789",
                "Casa A",
                "Bairro Teste",
                "Palmas",
                "TO",
                "77000200"
        );

        Long enderecoId = given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(newEndereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); 

        EnderecoRequestDTO updatedEndereco = new EnderecoRequestDTO(
                "Rua Atualizada do Cliente",
                "789-Updated",
                "Casa B",
                "Novo Bairro",
                "Palmas",
                "TO",
                "77000300"
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(updatedEndereco)
                .when().put("/enderecos/" + enderecoId)
                .then()
                .statusCode(200)
                .body("id", is(enderecoId.intValue())) 
                .body("logradouro", is("Rua Atualizada do Cliente"));
    }

    @Test
    public void testUpdateEnderecoAsUserOtherUserForbidden() {
        EnderecoRequestDTO adminEndereco = new EnderecoRequestDTO(
                "Rua do Admin para Teste",
                "111",
                null,
                "Admin Bairro",
                "Palmas",
                "TO",
                "77000400"
        );

        Long adminEnderecoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(adminEndereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); 

        EnderecoRequestDTO attemptedUpdate = new EnderecoRequestDTO(
                "Rua Tentativa User",
                "222",
                null,
                "User Bairro",
                "Palmas",
                "TO",
                "77000500"
        );

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(attemptedUpdate)
                .when().put("/enderecos/" + adminEnderecoId)
                .then()
                .statusCode(403); 
    }

    @Test
    public void testUpdateEnderecoAsAdminAnyAddress() {
        EnderecoRequestDTO userEndereco = new EnderecoRequestDTO(
                "Rua do Usuario para Admin Update",
                "333",
                null,
                "User Bairro",
                "Palmas",
                "TO",
                "77000600"
        );

        Long userEnderecoId = given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(userEndereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); 

        EnderecoRequestDTO adminUpdate = new EnderecoRequestDTO(
                "Rua Atualizada pelo Admin",
                "333-Admin",
                null,
                "Admin Bairro Update",
                "Palmas",
                "TO",
                "77000700"
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(adminUpdate)
                .when().put("/enderecos/" + userEnderecoId)
                .then()
                .statusCode(200)
                .body("id", is(userEnderecoId.intValue())) 
                .body("logradouro", is("Rua Atualizada pelo Admin"));
    }


    @Test
    public void testDeleteEnderecoAsUserSelf() {
        EnderecoRequestDTO newEndereco = new EnderecoRequestDTO(
                "Rua para Deletar",
                "111",
                null,
                "Setor Leste",
                "Palmas",
                "TO",
                "77000800"
        );

        Long enderecoId = given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(newEndereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().delete("/enderecos/" + enderecoId)
                .then()
                .statusCode(204); 
    }

    @Test
    public void testDeleteEnderecoAsUserOtherUserForbidden() {
        EnderecoRequestDTO adminEndereco = new EnderecoRequestDTO(
                "Rua do Admin para Deletar",
                "555",
                null,
                "Admin Setor",
                "Palmas",
                "TO",
                "77000900"
        );

        Long adminEnderecoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(adminEndereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().delete("/enderecos/" + adminEnderecoId)
                .then()
                .statusCode(403); 
    }

    @Test
    public void testDeleteEnderecoAsAdminAnyAddress() {
        EnderecoRequestDTO userEndereco = new EnderecoRequestDTO(
                "Rua do Usuario para Admin Delete",
                "666",
                null,
                "User Setor",
                "Palmas",
                "TO",
                "77001000"
        );

        Long userEnderecoId = given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(userEndereco)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id"); 

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/enderecos/" + userEnderecoId)
                .then()
                .statusCode(204); 
    }

    @Test
    public void testFindAllEnderecosAsAdmin() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/enderecos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2)); 
    }

    @Test
    public void testFindAllEnderecosAsUserForbidden() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/enderecos")
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindEnderecoByIdAsUserSelf() {
        Long joaoAddressId = 1L; 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/enderecos/" + joaoAddressId)
                .then()
                .statusCode(200)
                .body("id", is(joaoAddressId.intValue())) 
                .body("logradouro", is("Rua A"));
    }

    @Test
    public void testFindEnderecoByIdAsUserOtherUserForbidden() {
        Long adminAddressId = 2L; 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/enderecos/" + adminAddressId)
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindEnderecoByIdAsAdminAnyAddress() {
        Long joaoAddressId = 1L; 

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/enderecos/" + joaoAddressId)
                .then()
                .statusCode(200)
                .body("id", is(joaoAddressId.intValue())) 
                .body("logradouro", is("Rua A"));
    }

    @Test
    public void testFindMyAddressesAsUser() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/enderecos/meus-enderecos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1)) 
                .body("[0].logradouro", notNullValue());
    }
}