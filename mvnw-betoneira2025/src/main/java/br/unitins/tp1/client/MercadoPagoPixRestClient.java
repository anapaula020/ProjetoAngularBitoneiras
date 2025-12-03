package br.unitins.tp1.client;

import br.unitins.tp1.dto.mercadopago.MercadoPagoPixRequestDTO;
import br.unitins.tp1.dto.mercadopago.MercadoPagoPixResponseDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey="mercadopago-pix-api")
@Path("/v1/payments")
public interface MercadoPagoPixRestClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    MercadoPagoPixResponseDTO createPixPayment(
        @HeaderParam("Authorization") String authorization,
        @HeaderParam("X-Idempotency-Key") String idempotencyKey, // <-- Adicione esta linha
        MercadoPagoPixRequestDTO request
    );
}