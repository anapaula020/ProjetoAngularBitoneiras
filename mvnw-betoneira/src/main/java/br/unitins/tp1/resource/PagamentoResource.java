package br.unitins.tp1.resource;

import br.unitins.tp1.service.PagamentoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import br.unitins.tp1.repository.ClienteRepository;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    // @Inject
    // PixService pixService;

    @Inject
    SecurityContext securityContext;

    @Inject
    ClienteRepository clienteRepository;

    // @POST
    // @RolesAllowed({ "USER" })
    // public Response create(PagamentoRequestDTO dto) {
    // PagamentoResponseDTO pagamento = pagamentoService.create(dto);
    // return Response.status(Response.Status.CREATED).entity(pagamento).build();
    // }

    // @POST
    // @Path("/mercadopago/pix")
    // @RolesAllowed({ "USER", "ADMIN" })
    // public Response processCardPayment(CardPaymentRequestDTO cardDto) {
    // String email = securityContext.getUserPrincipal().getName();
    // Cliente cliente = clienteRepository.findByEmail(email);

    // if (cliente == null) {
    // return
    // Response.status(Response.Status.UNAUTHORIZED).entity(Result.valueOf("Usuário
    // não encontrado."))
    // .build();
    // }

    // try {
    // PixDTO servicePixRequest = PixService.generatePix(cardDto.getAmount(),
    // cliente);
    // return Response.ok(servicePixRequest).build();
    // } catch (ServiceException e) {
    // System.err.println("Erro ao processar pagamento com pix: " + e.getMessage());
    // return Response.status(e.getStatusCode())
    // .entity(Result.valueOf("Falha no pagamento com pix: " +
    // e.getMessage())).build();
    // } catch (Exception e) {
    // System.err.println("Erro inesperado ao processar pagamento com pix: " +
    // e.getMessage());
    // return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
    // .entity(Result.valueOf("Erro inesperado no pagamento: " +
    // e.getMessage())).build();
    // }
    // servicePixRequest.cardNumber = cardDto.getCardNumber();
    // servicePixRequest.securityCode = cardDto.getSecurityCode();
    // servicePixRequest.expirationDate = cardDto.getExpirationDate();
    // servicePixRequest.cardHolderName = cardDto.getCardHolderName();
    // servicePixRequest.amount = cardDto.getAmount();
    // servicePixRequest.email = cliente.getEmail();
    // servicePixRequest.cpf = cliente.getCpf();

    // try {
    // PixDTO pixResponse = pixService.createCardPayment(servicePixRequest,
    // cliente);
    // return Response.ok(pixResponse).build();
    // } catch (ServiceException e) {
    // System.err.println("Erro ao processar pagamento com cartão: " +
    // e.getMessage());
    // return Response.status(e.getStatusCode())
    // .entity(Result.valueOf("Falha no pagamento com cartão: " +
    // e.getMessage())).build();
    // } catch (Exception e) {
    // System.err.println("Erro inesperado ao processar pagamento com cartão: " +
    // e.getMessage());
    // return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
    // .entity(Result.valueOf("Erro inesperado no pagamento: " +
    // e.getMessage())).build();
    // }
    // }

    @GET
    @RolesAllowed({ "ADMIN" })
    public Response findAll() {
        return Response.ok(pagamentoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "USER" })
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(pagamentoService.findById(id)).build();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "ADMIN" })
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(pagamentoService.findByNome(nome)).build();
    }
}