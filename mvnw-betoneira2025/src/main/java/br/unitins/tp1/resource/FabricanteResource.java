// src/main/java/br/unitins/tp1/resource/FabricanteResource.java
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.FabricanteRequestDTO;
import br.unitins.tp1.dto.FabricanteResponseDTO;
import br.unitins.tp1.service.FabricanteService;
import jakarta.annotation.security.RolesAllowed; // Importar esta anotação
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService fabricanteService;

    @POST
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode criar fabricantes
    public Response create(FabricanteRequestDTO dto) {
        FabricanteResponseDTO newFabricante = fabricanteService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newFabricante).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode atualizar fabricantes
    public Response update(@PathParam("id") Long id, FabricanteRequestDTO dto) {
        FabricanteResponseDTO updatedFabricante = fabricanteService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedFabricante).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode deletar fabricantes
    public Response delete(@PathParam("id") Long id) {
        fabricanteService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem listar fabricantes
    public Response findAll() {
        return Response.ok(fabricanteService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem buscar fabricante por ID
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(fabricanteService.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem buscar fabricante por nome
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(fabricanteService.findByNome(nome)).build();
    }
}