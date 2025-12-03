package br.unitins.tp1.service;

import br.unitins.tp1.client.MercadoPagoPixRestClient;
import br.unitins.tp1.dto.PixDTO;
import br.unitins.tp1.dto.mercadopago.IdentificationDTO;
import br.unitins.tp1.dto.mercadopago.MercadoPagoPixRequestDTO;
import br.unitins.tp1.dto.mercadopago.MercadoPagoPixResponseDTO;
import br.unitins.tp1.dto.mercadopago.PayerDTO;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.util.DTOValidator; // Importação do DTOValidator
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.UUID;

@ApplicationScoped
public class PixService {

    @Inject
    @RestClient
    MercadoPagoPixRestClient pixRestClient;

    @ConfigProperty(name = "mercadopago.access.token")
    String accessToken;

    @Inject
    DTOValidator dtoValidator; // Injeção do DTOValidator

    /**
     * Classe interna estática para representar os dados de uma requisição de pagamento com cartão.
     * Usada no PagamentoResource para mapear a entrada.
     */
    public static class CardPaymentRequest {
        public String cardNumber;
        public String securityCode;
        public String expirationDate; // Formato MM/AA
        public String cardHolderName;
        public Double amount;
        public String email; // Email do cliente
        public String cpf; // CPF do cliente
    }

    /**
     * Gera os detalhes de um pagamento PIX para um determinado valor e cliente.
     * Este método é chamado pelo PagamentoServiceImpl.
     *
     * @param amount O valor do pagamento.
     * @param cliente O objeto Cliente que está realizando o pagamento.
     * @return Um PixDTO contendo os detalhes da resposta PIX (QR Code, status, etc.).
     */
    public PixDTO generatePix(Double amount, Cliente cliente) {
        MercadoPagoPixRequestDTO pixRequest = new MercadoPagoPixRequestDTO();
        pixRequest.setTransactionAmount(amount);
        pixRequest.setDescription("Pagamento via PIX"); // Descrição genérica para PIX
        pixRequest.setPaymentMethodId("pix");

        PayerDTO payer = new PayerDTO();
        payer.setEmail(cliente.getEmail());
        payer.setFirstName(cliente.getNome());
        // CORREÇÃO: Garante que lastName não esteja em branco para passar a validação
        payer.setLastName("Não Informado"); // Definindo um valor padrão para evitar erro de validação

        IdentificationDTO identification = new IdentificationDTO();
        identification.setType("CPF"); // Assumindo CPF para clientes
        identification.setNumber(cliente.getCpf());

        payer.setIdentification(identification);
        pixRequest.setPayer(payer);

        // Valida o DTO antes de enviar para o Mercado Pago
        dtoValidator.validate(pixRequest);

        String idempotencyKey = UUID.randomUUID().toString();
        MercadoPagoPixResponseDTO response = pixRestClient.createPixPayment("Bearer " + accessToken, idempotencyKey, pixRequest);

        // Mapeia a resposta do Mercado Pago para um PixDTO mais simples para a aplicação
        PixDTO pixResponse = new PixDTO();
        pixResponse.setId(response.getId());
        pixResponse.setStatus(response.getStatus());
        if (response.getPointOfInteraction() != null && response.getPointOfInteraction().getTransactionData() != null) {
            pixResponse.setQrCode(response.getPointOfInteraction().getTransactionData().getQrCode());
            pixResponse.setQrCodeBase64(response.getPointOfInteraction().getTransactionData().getQrCodeBase64());
            pixResponse.setTicketUrl(response.getPointOfInteraction().getTransactionData().getTicketUrl());
        }
        return pixResponse;
    }

    /**
     * Processa um pagamento com cartão de crédito (Simulação/Mock).
     *
     * @param cardRequest Os dados da requisição de pagamento com cartão.
     * @param cliente O objeto Cliente que está realizando o pagamento.
     * @return Um PixDTO (reutilizado para simplificar o retorno) com o status do pagamento simulado.
     */
    public PixDTO createCardPayment(CardPaymentRequest cardRequest, Cliente cliente) {
        // --- INÍCIO DO MOCK/SIMULAÇÃO DE PAGAMENTO COM CARTÃO ---
        System.out.println("Simulando pagamento com cartão para o cliente: " + cliente.getEmail());
        System.out.println("Valor: " + cardRequest.amount);
        System.out.println("Número do Cartão (últimos 4): ****" + cardRequest.cardNumber.substring(cardRequest.cardNumber.length() - 4));

        // Em uma integração real, aqui você chamaria a API de Cartões do Mercado Pago.
        // Isso normalmente envolveria:
        // 1. Tokenização do cartão (para não trafegar dados sensíveis do cartão).
        // 2. Envio da requisição de pagamento com o token e demais dados para a API.
        // 3. Tratamento da resposta da API de Cartões.

        PixDTO simulatedResponse = new PixDTO();
        simulatedResponse.setStatus("approved"); // Status simulado
        simulatedResponse.setId(UUID.randomUUID().toString()); // ID de transação simulado

        // --- FIM DO MOCK/SIMULAÇÃO ---

        return simulatedResponse;
    }

    /**
     * Método para processar pagamentos PIX com base em um ID de Pedido e PixDTO.
     * Mantido para compatibilidade, mas o `generatePix` acima é mais genérico.
     *
     * @param pedidoId O ID do pedido.
     * @param pixDto   O DTO contendo os dados do pagador e identificação para o PIX.
     * @return O DTO de resposta do Mercado Pago.
     */
    public MercadoPagoPixResponseDTO processPixPayment(Long pedidoId, PixDTO pixDto) {
        // Esta é uma alternativa para iniciar um PIX que já tem um PixDTO de entrada.
        // Em um cenário completo, você provavelmente buscaria o pedido aqui para obter o `amount` e `description`
        // e então chamaria `generatePix` internamente para evitar duplicação de lógica.

        MercadoPagoPixRequestDTO pixRequest = new MercadoPagoPixRequestDTO();
        pixRequest.setTransactionAmount(pixDto.getAmount()); // Assumindo que PixDTO agora tem um 'amount'
        pixRequest.setDescription("Pagamento de Compra via PIX (Pedido #" + pedidoId + ")");
        pixRequest.setPaymentMethodId("pix");

        PayerDTO payer = new PayerDTO();
        payer.setEmail(pixDto.getEmail());
        payer.setFirstName(pixDto.getFirstName());
        payer.setLastName(pixDto.getLastName());

        IdentificationDTO identification = new IdentificationDTO();
        identification.setType(pixDto.getIdentificationType());
        identification.setNumber(pixDto.getIdentificationNumber());

        payer.setIdentification(identification);
        pixRequest.setPayer(payer);

        dtoValidator.validate(pixRequest);

        String idempotencyKey = UUID.randomUUID().toString();
        MercadoPagoPixResponseDTO response = pixRestClient.createPixPayment("Bearer " + accessToken, idempotencyKey, pixRequest);

        return response;
    }
}