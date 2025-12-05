package mssaat.org.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import mssaat.org.DTO.AvaliacaoDTO;
import mssaat.org.DTO.AvaliacaoResponseDTO;
import mssaat.org.service.AvaliacaoService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/avaliacao")
public class AvaliacaoResource {
    @Inject
    public AvaliacaoService avaliacaoService;

    @GET
    @RolesAllowed("Administrador")
    @Path("/count")
    public long count() {
        return avaliacaoService.count();
    }

    @GET
    public Response findAll() {
        return Response.ok(avaliacaoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        AvaliacaoResponseDTO avaliacao = avaliacaoService.findById(id);
        if (avaliacao == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(avaliacao).build();
    }

    @GET
    @Path("/search/comentario/{conteudo}")
    public Response findByComentario(@PathParam("conteudo") String conteudo) {
        return Response.ok(avaliacaoService.findByComentario(conteudo)).build();
    }

    @POST
    @Transactional
    public Response create(AvaliacaoDTO avaliacaoDto) {
        return Response.status(Status.CREATED).entity(avaliacaoService.create(avaliacaoDto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Usuario")
    @Transactional
    public Response update(@PathParam("id") Long id, AvaliacaoDTO avaliacaoDto) {
        AvaliacaoResponseDTO avaliacaoBanco = avaliacaoService.findById(id);
        if (avaliacaoBanco == null) return Response.status(Status.NOT_FOUND).build();
        avaliacaoService.update(id, avaliacaoDto);
        return Response.status(Status.NO_CONTENT).build();

    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Usuario")
    @Transactional
    public Response deleteById(@PathParam("id") Long id) {
        avaliacaoService.deleteById(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}