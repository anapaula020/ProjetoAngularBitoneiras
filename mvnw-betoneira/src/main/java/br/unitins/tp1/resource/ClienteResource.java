// src/main/java/br/unitins/tp1/resource/ClienteResource.java
package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ClienteRequestDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.service.ClienteService;
import jakarta.annotation.security.PermitAll; // Importar esta anotação
import jakarta.annotation.security.RolesAllowed; // Importar esta anotação
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @POST
    @Transactional
    @PermitAll // Qualquer um pode criar um cliente (cadastro público)
    public Response create(ClienteRequestDTO dto) {
        ClienteResponseDTO newCliente = clienteService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newCliente).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN", "USER"}) // Apenas ADMIN e USER podem atualizar (USER só o próprio perfil, validado no Service)
    public Response update(@PathParam("id") Long id, ClienteRequestDTO dto) {
        ClienteResponseDTO updatedCliente = clienteService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedCliente).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN") // Apenas ADMIN pode deletar um cliente (USER não deveria deletar o próprio perfil sem um fluxo específico)
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("ADMIN") // Apenas ADMIN pode listar todos os clientes (evitar exposição de dados)
    public Response findAll() {
        return Response.ok(clienteService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"}) // Apenas ADMIN e USER podem buscar por ID (USER só o próprio perfil, validado no Service)
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(clienteService.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"ADMIN", "USER"}) // ADMIN e USER podem buscar por nome (ADMIN todos, USER só os seus se houver lógica)
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(clienteService.findByNome(nome)).build();
    }
}