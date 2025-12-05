package mssaat.org.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import mssaat.org.DTO.AutorMangaDTO;
import mssaat.org.service.AutorMangaServiceImpl;

@Path("/autorManga")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutorMangaResource {
    @Inject
    AutorMangaServiceImpl autorMangaService;

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        return Response.ok(autorMangaService.findAll(page, pageSize)).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return autorMangaService.count();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") long id) {
        return Response.ok(autorMangaService.findById(id)).build();
    }

    @GET
    @Path("/name/{name}")
    public Response findByName(@PathParam("name") String name) {
        return Response.ok(autorMangaService.findByName(name)).build();
    }

    @POST
    @RolesAllowed("Administrador")
    public Response create(AutorMangaDTO autorManga) {
        return Response.status(Status.CREATED).entity(autorMangaService.create(autorManga)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response update(@PathParam("id") long id, AutorMangaDTO autorManga) {
        autorMangaService.update(id, autorManga);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response delete(@PathParam("id") long id) {
        autorMangaService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}
