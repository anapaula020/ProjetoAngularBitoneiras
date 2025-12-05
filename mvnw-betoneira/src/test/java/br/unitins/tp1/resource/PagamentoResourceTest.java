package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.dto.PagamentoRequestDTO; 
import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.model.StatusPagamento; 
import br.unitins.tp1.model.TipoPagamento; 
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
public class PagamentoResourceTest {

    private String adminToken;
    private String userToken;
    private Long userAddressId = 1L; 
    private Long adminAddressId = 2L; 

    
    private Long samplePedidoId;

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");

        ItemPedidoRequestDTO item = new ItemPedidoRequestDTO(1L, 1, 1500.0); 
        PedidoRequestDTO pedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(item), userAddressId);

        samplePedidoId = given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(pedido)
                .when().post("/pedidos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    @Test
    public void testCreatePagamentoAsAdmin() {
        PagamentoRequestDTO pagamento = new PagamentoRequestDTO(
                samplePedidoId,
                TipoPagamento.PIX.ordinal() + 1 
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pagamento)
                .when().post("/pagamentos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("statusPagamento", is("PROCESSANDO")); 
    }
    
    @Test
    public void testFindAllPagamentosAsAdmin() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/pagamentos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2)); 
    }

    @Test
    public void testFindAllPagamentosAsUserForbidden() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pagamentos")
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindPagamentoByIdAsUserSelfOwned() {
        PagamentoRequestDTO pagamentoReq = new PagamentoRequestDTO(
                samplePedidoId,
                TipoPagamento.BOLETO.ordinal() + 1 
        );

        Long pagamentoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pagamentoReq)
                .when().post("/pagamentos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pagamentos/" + pagamentoId)
                .then()
                .statusCode(200)
                .body("id", is(pagamentoId.intValue()));
    }

    @Test
    public void testFindPagamentoByIdAsUserOtherUserForbidden() {
        Long adminOwnedPaymentId = 2L; 

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pagamentos/" + adminOwnedPaymentId)
                .then()
                .statusCode(403); 
    }

    @Test
    public void testFindPagamentoByIdAsAdminAnyPayment() {
        Long userOwnedPaymentId = 1L; 

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/pagamentos/" + userOwnedPaymentId)
                .then()
                .statusCode(200)
                .body("id", is(userOwnedPaymentId.intValue()));
    }
    
    
    
    @Test
    public void testUpdatePagamentoStatusAsAdmin() {
        PagamentoRequestDTO pagamentoReq = new PagamentoRequestDTO(
                samplePedidoId,
                TipoPagamento.BOLETO.ordinal() + 1 
        );

        Long pagamentoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pagamentoReq)
                .when().post("/pagamentos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().put("/pagamentos/processar/" + pagamentoId + "/APROVADO")
                .then()
                .statusCode(204); 
    }

    @Test
    public void testDeletePagamentoAsAdmin() {
        PagamentoRequestDTO pagamentoReq = new PagamentoRequestDTO(
                samplePedidoId,
                TipoPagamento.PIX.ordinal() + 1 
        );

        Long pagamentoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pagamentoReq)
                .when().post("/pagamentos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/pagamentos/" + pagamentoId)
                .then()
                .statusCode(204); 
    }
}