package br.unitins.tp1.resource;

import br.unitins.tp1.dto.BetoneiraRequestDTO;
import br.unitins.tp1.dto.BetoneiraResponseDTO;
import br.unitins.tp1.form.ImageForm;
import br.unitins.tp1.service.BetoneiraFileServiceImpl;
import br.unitins.tp1.service.BetoneiraService;

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

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

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
    public Response create(BetoneiraRequestDTO dto) {
        BetoneiraResponseDTO newBetoneira = betoneiraService.create(dto);
        return Response.status(Response.Status.CREATED).entity(newBetoneira).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, BetoneiraRequestDTO dto) {
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
    
    @PATCH
    @Path("/{id}/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("Administrador")
    public Response upload(@PathParam("id") Long id, @MultipartForm ImageForm form) {
        fileService.salvar(id, form.getImageUrl(), form.getImagem());
        return Response.noContent().build();
    }

    @GET
    @Path("/image/download/{imagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("imagem") String imagem) {
        ResponseBuilder response = Response.ok(fileService.download(imagem));
        response.header("Content-Disposition", "attachment; filename=" + imagem);
        return response.build();
    }

    @DELETE
    @Path("/image/{id}/delete")
    @RolesAllowed("Administrador")
    public Response deleteImagem(@PathParam("id") Long id) {
        fileService.deleteImagem(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}