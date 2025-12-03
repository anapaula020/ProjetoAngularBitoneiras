package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.dto.PagamentoRequestDTO; // Atualizado para o DTO correto
import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.model.StatusPagamento; // Necessário para StatusPagamento.APROVADO, etc.
import br.unitins.tp1.model.TipoPagamento; // Necessário para TipoPagamento.PIX, etc.
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
    private Long userAddressId = 1L; // Assuming João Silva's address ID from import.sql
    private Long adminAddressId = 2L; // Assuming Maria Admin's address ID from import.sql

    // Adicionado para obter o ID do pedido necessário para criar um pagamento
    private Long samplePedidoId;

    @BeforeEach
    public void setup() {
        adminToken = AuthResourceTest.getAuthToken("admin@example.com", "adminpassword");
        userToken = AuthResourceTest.getAuthToken("joao@example.com", "userpassword");

        // Criar um pedido de exemplo para ser usado nos testes de pagamento
        // Este setup precisa ser robusto para garantir que o pedido seja criado antes dos testes de pagamento
        ItemPedidoRequestDTO item = new ItemPedidoRequestDTO(1L, 1, 1500.0); // Assumindo Betoneira ID 1 existe
        PedidoRequestDTO pedido = new PedidoRequestDTO(LocalDateTime.now(), Arrays.asList(item), userAddressId);

        // Cria o pedido como USER, pois ADMIN não pode criar pedidos via este endpoint
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
        // Usa o samplePedidoId criado no setup
        // TipoPagamento.PIX.ordinal() + 1 => assume que PIX é o 1º na enum e os IDs começam em 1
        PagamentoRequestDTO pagamento = new PagamentoRequestDTO(
                samplePedidoId,
                TipoPagamento.PIX.ordinal() + 1 // ID do TipoPagamento (PIX)
        );

        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pagamento)
                .when().post("/pagamentos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("statusPagamento", is("PROCESSANDO")); // PIX inicialmente é PROCESSANDO ou APROVADO/PENDING
    }
    
    @Test
    public void testFindAllPagamentosAsAdmin() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/pagamentos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2)); // Pelo menos 2 (do import.sql + o criado no setup)
    }

    @Test
    public void testFindAllPagamentosAsUserForbidden() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pagamentos")
                .then()
                .statusCode(403); // Forbidden, usuário normal não lista todos
    }

    @Test
    public void testFindPagamentoByIdAsUserSelfOwned() {
        // Usa o samplePedidoId criado no setup e assume que o pagamento será criado para ele
        PagamentoRequestDTO pagamentoReq = new PagamentoRequestDTO(
                samplePedidoId,
                TipoPagamento.BOLETO.ordinal() + 1 // ID do TipoPagamento (BOLETO)
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
        // Assume um pagamento que não pertence ao usuário logado (ex: pagamento de admin do import.sql)
        Long adminOwnedPaymentId = 2L; // Pagamento do admin@example.com do import.sql

        given()
                .header("Authorization", "Bearer " + userToken)
                .when().get("/pagamentos/" + adminOwnedPaymentId)
                .then()
                .statusCode(403); // Forbidden
    }

    @Test
    public void testFindPagamentoByIdAsAdminAnyPayment() {
        // Usa um pagamento de usuário (ex: o criado no setup ou do import.sql)
        Long userOwnedPaymentId = 1L; // Pagamento de joao@example.com do import.sql

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().get("/pagamentos/" + userOwnedPaymentId)
                .then()
                .statusCode(200)
                .body("id", is(userOwnedPaymentId.intValue()));
    }
    
    // Testes de atualização e deleção (se aplicável ao escopo)
    // Exemplo de atualização:
    @Test
    public void testUpdatePagamentoStatusAsAdmin() {
        // Cria um pagamento no estado PENDENTE/PROCESSANDO para ser atualizado
        PagamentoRequestDTO pagamentoReq = new PagamentoRequestDTO(
                samplePedidoId,
                TipoPagamento.BOLETO.ordinal() + 1 // ID do TipoPagamento (BOLETO)
        );

        Long pagamentoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pagamentoReq)
                .when().post("/pagamentos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Atualiza o status do pagamento para APROVADO como admin
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().put("/pagamentos/processar/" + pagamentoId + "/APROVADO")
                .then()
                .statusCode(204); // No Content
    }

    @Test
    public void testDeletePagamentoAsAdmin() {
        // Cria um pagamento para ser deletado
        PagamentoRequestDTO pagamentoReq = new PagamentoRequestDTO(
                samplePedidoId,
                TipoPagamento.PIX.ordinal() + 1 // ID do TipoPagamento (PIX)
        );

        Long pagamentoId = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(pagamentoReq)
                .when().post("/pagamentos")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Deleta o pagamento como admin
        given()
                .header("Authorization", "Bearer " + adminToken)
                .when().delete("/pagamentos/" + pagamentoId)
                .then()
                .statusCode(204); // No Content
    }
}