package br.unitins.tp1.resource;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {
    @Inject
    PedidoService pedidoService;

    @POST
    @Transactional
    @RolesAllowed("USER")
    public Response create(PedidoRequestDTO dto) {
        PedidoResponseDTO newPedido = pedidoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newPedido).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, PedidoRequestDTO dto) {
        PedidoResponseDTO updatedPedido = pedidoService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedPedido).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        pedidoService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("ADMIN")
    public Response findAll() {
        return Response.ok(pedidoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(pedidoService.findById(id)).build();
    }

    @GET
    @Path("/meus-pedidos")
    @RolesAllowed("USER")
    public Response getMyOrders() {
        return Response.ok(pedidoService.getPurchaseHistoryForAuthenticatedUser()).build();
    }
}