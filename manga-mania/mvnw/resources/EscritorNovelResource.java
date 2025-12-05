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
import mssaat.org.DTO.EscritorNovelDTO;
import mssaat.org.service.EscritorNovelServiceImpl;

@Path("/escritorNovel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EscritorNovelResource {
    @Inject
    EscritorNovelServiceImpl escritorNovelService;

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        return Response.ok(escritorNovelService.findAll(page, pageSize)).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return escritorNovelService.count();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") long id) {
        return Response.ok(escritorNovelService.findById(id)).build();
    }

    @GET
    @Path("/name/{name}")
    public Response findByName(@PathParam("name") String name) {
        return Response.ok(escritorNovelService.findByName(name)).build();
    }

    @POST
    @RolesAllowed("Administrador")
    public Response create(EscritorNovelDTO escritorNovel) {
        return Response.status(Status.CREATED).entity(escritorNovelService.create(escritorNovel)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response update(@PathParam("id") long id, EscritorNovelDTO escritorNovel) {
        escritorNovelService.update(id, escritorNovel);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response delete(@PathParam("id") long id) {
        escritorNovelService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}
