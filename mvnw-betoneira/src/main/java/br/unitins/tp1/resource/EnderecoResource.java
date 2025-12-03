// src/main/java/br/unitins/tp1/resource/EnderecoResource.java
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.EnderecoRequestDTO;
import br.unitins.tp1.dto.EnderecoResponseDTO;
import br.unitins.tp1.service.EnderecoService;
import jakarta.annotation.security.RolesAllowed; // Importar esta anotação
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    @POST
    @Transactional
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN pode criar para qualquer um, USER cria para si mesmo
    public Response create(EnderecoRequestDTO dto) {
        EnderecoResponseDTO newEndereco = enderecoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newEndereco).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN pode atualizar qualquer one, USER só o próprio
    public Response update(@PathParam("id") Long id, EnderecoRequestDTO dto) {
        EnderecoResponseDTO updatedEndereco = enderecoService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedEndereco).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN pode deletar qualquer one, USER só o próprio
    public Response delete(@PathParam("id") Long id) {
        enderecoService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("ADMIN") // Apenas ADMIN pode listar todos os endereços
    public Response findAll() {
        return Response.ok(enderecoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN pode buscar qualquer one, USER só o próprio
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(enderecoService.findById(id)).build();
    }

    // Adicional: Endpoint para o usuário ver seus próprios endereços
    @GET
    @Path("/meus-enderecos")
    @RolesAllowed("USER")
    public Response findMyAddresses() {
        return Response.ok(enderecoService.findByAuthenticatedUser()).build();
    }
}