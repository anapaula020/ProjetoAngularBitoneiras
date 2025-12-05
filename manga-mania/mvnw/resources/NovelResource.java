package mssaat.org.resources;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import mssaat.org.DTO.NovelDTO;
import mssaat.org.form.ImageForm;
import mssaat.org.service.MangaFileServiceImpl;
import mssaat.org.service.NovelServiceImpl;

@Path("/novel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NovelResource {
    @Inject
    NovelServiceImpl novelService;

    @Inject
    public MangaFileServiceImpl fileService;

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        return Response.ok(novelService.findAll(page, pageSize)).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return novelService.count();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") long id) {
        return Response.ok(novelService.findById(id)).build();
    }

    @GET
    @Path("/name/{name}")
    public Response findByName(@PathParam("name") String name) {
        return Response.ok(novelService.findByName(name)).build();
    }

    @GET
    @Path("/escritor/{escritorId}")
    public Response findByEscritor(@PathParam("escritorId") Long escritorId) {
        return Response.ok(novelService.findByEscritor(escritorId)).build();
    }

    @GET
    @Path("/genero/{genreId}")
    public Response findByGenre(@PathParam("genreId") int genreId) {
        return Response.ok(novelService.findByGenre(genreId)).build();
    }

    @POST
    @RolesAllowed("Administrador")
    public Response create(NovelDTO novel) {
        return Response.status(Status.CREATED).entity(novelService.create(novel)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response update(@PathParam("id") long id, NovelDTO novel) {
        novelService.update(id, novel);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response delete(@PathParam("id") long id) {
        novelService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
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
    @Path("/{id}/image/download/{imagem}")
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