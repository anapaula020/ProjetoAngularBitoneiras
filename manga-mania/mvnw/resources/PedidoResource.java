package mssaat.org.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import mssaat.org.DTO.CartaoDTO;
import mssaat.org.DTO.PedidoDTO;
import mssaat.org.DTO.PedidoResponseDTO;
import mssaat.org.DTO.PixDTO;
import mssaat.org.service.PedidoService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/pedidos")
public class PedidoResource {
    @Inject
    public PedidoService pedidoService;

    @GET
    public Response findAll() {
        return Response.ok(pedidoService.findAll()).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed("Usuario")
    public long count() {
        return pedidoService.count();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findById(@PathParam("id") Long id) {
        PedidoResponseDTO pedido = pedidoService.findById(id);
        if (pedido == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(pedido).build();
    }

    @GET
    @Path("/search/endereco/{endereco}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findByEndereco(@PathParam("endereco") String endereco) {
        return Response.ok(pedidoService.findByEndereco(endereco)).build();
    }

    @GET
    @Path("/search/user/{id}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findComprasByUser(Long id) {
        return Response.ok(pedidoService.findComprasByUser(id)).build();
    }

    @POST
    @RolesAllowed({"Usuario"})
    @Transactional
    public Response create(PedidoDTO pedidoDTO) {
        return Response.status(Status.CREATED).entity(pedidoService.create(pedidoDTO)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Usuario"})
    @Transactional
    public Response update(@PathParam("id") Long id, PedidoDTO pedidoDto) {
        PedidoResponseDTO pedidoBanco = pedidoService.findById(id);
        if (pedidoBanco == null)
            return Response.status(Status.NOT_FOUND).build();
        pedidoService.update(id, pedidoDto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/pagar/pix")
    @RolesAllowed({"Usuario"})
    @Transactional
    public Response PagarPeloPix(PixDTO pix) {
        return pedidoService.PagarPeloPix(pix);
    }

    @PATCH
    @Path("/pagar/credito/{parcelas}")
    @RolesAllowed({"Usuario"})
    @Transactional
    public Response PagarPeloCredito(CartaoDTO cartao, @PathParam("parcelas") int parcelas) {
        return pedidoService.PagarPeloCredito(cartao, parcelas);
    }

    @PATCH
    @Path("/pagar/debito")
    @RolesAllowed({"Usuario"})
    @Transactional
    public Response PagarPeloDebito(CartaoDTO cartao) {
        return pedidoService.PagarPeloDebito(cartao);
    }

    @GET
    @Path("/meus")
    @RolesAllowed({"Usuario"})
    public Response findMyPedidos(){
        return Response.ok(pedidoService.findMyCompras()).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Usuario"})
    @Transactional
    public Response deleteById(@PathParam("id") Long id) {
        pedidoService.deleteById(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}