// src/main/java/br/unitins/tp1/resource/BetoneiraResource.java
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;
import br.unitins.tp1.service.BetoneiraService;
import jakarta.annotation.security.RolesAllowed; // Importar esta anotação
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/betoneiras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BetoneiraResource {

    @Inject
    BetoneiraService betoneiraService;

    @POST
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode criar betoneiras
    public Response create(BetoneiraRequestDTO dto) {
        BetoneiraResponseDTO newBetoneira = betoneiraService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newBetoneira).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode atualizar betoneiras
    public Response update(@PathParam("id") Long id, BetoneiraRequestDTO dto) {
        BetoneiraResponseDTO updatedBetoneira = betoneiraService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedBetoneira).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode deletar betoneiras
    public Response delete(@PathParam("id") Long id) {
        betoneiraService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem listar betoneiras
    public Response findAll() {
        return Response.ok(betoneiraService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem buscar betoneiras por ID
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(betoneiraService.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem buscar betoneiras por nome
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(betoneiraService.findByNome(nome)).build();
    }
}