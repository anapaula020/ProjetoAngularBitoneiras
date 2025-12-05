package br.unitins.tp1.resource;

import br.unitins.tp1.dto.PagamentoRequestDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.service.PagamentoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import br.unitins.tp1.application.Result;
import br.unitins.tp1.dto.PixDTO;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.service.PixService;
import br.unitins.tp1.dto.CardPaymentRequestDTO;
import br.unitins.tp1.exception.ServiceException;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    @Inject
    PixService pixService;

    @Inject
    SecurityContext securityContext;

    @Inject
    ClienteRepository clienteRepository;

    @POST
    @RolesAllowed({"ADMIN"})
    public Response create(PagamentoRequestDTO dto) {
        PagamentoResponseDTO pagamento = pagamentoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(pagamento).build();
    }

    @POST
    @Path("/cartao")
    @RolesAllowed({"USER", "ADMIN"})
    public Response processCardPayment(CardPaymentRequestDTO cardDto) {
        String email = securityContext.getUserPrincipal().getName();
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Result.valueOf("Usuário não encontrado.")).build();
        }

        PixService.CardPaymentRequest serviceCardRequest = new PixService.CardPaymentRequest();
        serviceCardRequest.cardNumber = cardDto.getCardNumber();
        serviceCardRequest.securityCode = cardDto.getSecurityCode();
        serviceCardRequest.expirationDate = cardDto.getExpirationDate();
        serviceCardRequest.cardHolderName = cardDto.getCardHolderName();
        serviceCardRequest.amount = cardDto.getAmount();
        serviceCardRequest.email = cliente.getEmail();
        serviceCardRequest.cpf = cliente.getCpf();

        try {
            PixDTO pixResponse = pixService.createCardPayment(serviceCardRequest, cliente);
            return Response.ok(pixResponse).build();
        } catch (ServiceException e) {
            System.err.println("Erro ao processar pagamento com cartão: " + e.getMessage());
            return Response.status(e.getStatusCode()).entity(Result.valueOf("Falha no pagamento com cartão: " + e.getMessage())).build();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar pagamento com cartão: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Result.valueOf("Erro inesperado no pagamento: " + e.getMessage())).build();
        }
    }

    @GET
    @RolesAllowed({"ADMIN"})
    public Response findAll() {
        return Response.ok(pagamentoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(pagamentoService.findById(id)).build();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"ADMIN"})
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(pagamentoService.findByNome(nome)).build();
    }
}