// src/main/java/br/unitins/tp1/resource/PedidoResource.java
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.PedidoRequestDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.service.PedidoService;
import jakarta.annotation.security.RolesAllowed; // Importar esta anotação
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
    @RolesAllowed("USER") // Usuário comum cria pedido
    public Response create(PedidoRequestDTO dto) {
        // No service, você validará se o pedido está sendo criado pelo usuário autenticado
        PedidoResponseDTO newPedido = pedidoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newPedido).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode atualizar pedidos de outros
    // Para um USER atualizar o próprio pedido, a lógica estará no service
    public Response update(@PathParam("id") Long id, PedidoRequestDTO dto) {
        PedidoResponseDTO updatedPedido = pedidoService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedPedido).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode deletar pedidos
    public Response delete(@PathParam("id") Long id) {
        pedidoService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("ADMIN") // Apenas ADMIN pode listar todos os pedidos
    public Response findAll() {
        return Response.ok(pedidoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN pode buscar qualquer pedido. USER só pode buscar os próprios (validado no Service)
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(pedidoService.findById(id)).build();
    }

    @GET
    @Path("/meus-pedidos")
    @RolesAllowed("USER") // Endpoint específico para usuários verem seus próprios pedidos
    public Response getMyOrders() {
        // A lógica de filtragem já existe no PedidoService (getPurchaseHistoryForAuthenticatedUser)
        return Response.ok(pedidoService.getPurchaseHistoryForAuthenticatedUser()).build();
    }
}