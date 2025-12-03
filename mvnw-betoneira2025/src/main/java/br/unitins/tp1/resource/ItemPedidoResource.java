// src/main/java/br/unitins/tp1/resource/ItemPedidoResource.java
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.dto.ItemPedidoResponseDTO;
import br.unitins.tp1.service.ItemPedidoService;
import jakarta.annotation.security.RolesAllowed; // Importar esta anotação
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/itens-pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemPedidoResource {

    @Inject
    ItemPedidoService itemPedidoService;

    @POST
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode criar itens de pedido diretamente (geralmente via Pedido)
    public Response create(ItemPedidoRequestDTO dto) {
        ItemPedidoResponseDTO newItemPedido = itemPedidoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newItemPedido).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode atualizar itens de pedido
    public Response update(@PathParam("id") Long id, ItemPedidoRequestDTO dto) {
        ItemPedidoResponseDTO updatedItemPedido = itemPedidoService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedItemPedido).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode deletar itens de pedido
    public Response delete(@PathParam("id") Long id) {
        itemPedidoService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("ADMIN") // Apenas ADMIN pode listar todos os itens de pedido
    public Response findAll() {
        return Response.ok(itemPedidoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN pode buscar qualquer um, USER só os que fazem parte de seus pedidos
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(itemPedidoService.findById(id)).build();
    }
}