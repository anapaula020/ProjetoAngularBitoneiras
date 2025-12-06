package br.unitins.tp1.resource;

import br.unitins.tp1.dto.BetoneiraDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;
import br.unitins.tp1.service.BetoneiraFileServiceImpl;
import br.unitins.tp1.service.BetoneiraService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;

import java.io.IOException;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/betoneiras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BetoneiraResource {
    @Inject
    BetoneiraService betoneiraService;
    @Inject
    public BetoneiraFileServiceImpl fileService;
    
    @POST
    @Transactional
    @RolesAllowed("ADMIN")
    public Response create(BetoneiraDTO dto) {
        return Response.status(Response.Status.CREATED).entity(betoneiraService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, BetoneiraDTO dto) {
        BetoneiraResponseDTO updatedBetoneira = betoneiraService.update(id, dto);
        return Response.status(Response.Status.OK).entity(updatedBetoneira).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        betoneiraService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    public Response findAll() {
        return Response.ok(betoneiraService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(betoneiraService.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(betoneiraService.findByNome(nome)).build();
    }

    @GET
    @Path("/search/tipo/{nome}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response findByTipo(@PathParam("nome") String nome) {
        return Response.ok(betoneiraService.findByTipo(nome)).build();
    }
    
    @GET
    @Path("/image/download/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        return response.build();
    }

    @PATCH
    @Path("/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(
            @RestForm("idPlano") 
            @NotNull(message = "idPlano é obrigatório.")
            @Min(value = 1, message = "idPlano deve ser maior ou igual a 1.")
            Long idPlano,

            @RestForm("file") 
            @NotNull(message = "Arquivo de imagem é obrigatório.")
            FileUpload file) {

        try {
            fileService.salvar(idPlano, file);
            return Response.noContent().build();
        } catch (IOException e) {
            return Response.status(Status.CONFLICT).build();
        }

    }
}