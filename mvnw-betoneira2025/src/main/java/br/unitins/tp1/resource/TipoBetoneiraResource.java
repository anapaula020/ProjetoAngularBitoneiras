// src/main/java/br/unitins/tp1/resource/TipoBetoneiraResource.java
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.TipoBetoneiraDTO;
import br.unitins.tp1.dto.TipoBetoneiraResponseDTO;
import br.unitins.tp1.service.TipoBetoneiraService;
import jakarta.annotation.security.RolesAllowed; // Importar esta anotação
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tipos-betoneira")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoBetoneiraResource {

    @Inject
    TipoBetoneiraService tipoBetoneiraService;

    @POST
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode criar tipos de betoneira
    public Response create(TipoBetoneiraDTO dto) {
        TipoBetoneiraResponseDTO newTipo = tipoBetoneiraService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newTipo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode atualizar tipos de betoneira
    public Response update(@PathParam("id") Long id, TipoBetoneiraDTO dto) {
        TipoBetoneiraResponseDTO updatedTipo = tipoBetoneiraService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedTipo).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode deletar tipos de betoneira
    public Response delete(@PathParam("id") Long id) {
        tipoBetoneiraService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem listar tipos de betoneira
    public Response findAll() {
        return Response.ok(tipoBetoneiraService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem buscar tipo de betoneira por ID
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(tipoBetoneiraService.findById(id)).build();
    }
}