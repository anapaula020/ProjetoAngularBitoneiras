package mssaat.org.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import mssaat.org.DTO.AdministradorDTO;
import mssaat.org.DTO.AdministradorResponseDTO;
import mssaat.org.DTO.UsuarioDTO;
import mssaat.org.DTO.UsuarioResponseDTO;
import mssaat.org.service.AdministradorService;
import mssaat.org.service.UsuarioService;

@Path("/administradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministradorResource {
    @Inject
    public AdministradorService administradorService;

    @Inject
    public UsuarioService usuarioService;

    @GET
    @RolesAllowed("Administrador")
    public Response findAll(@QueryParam("page") @DefaultValue("0") int page, @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        return Response.ok(administradorService.findAll(page, pageSize)).build();
    }

    @GET
    @RolesAllowed("Administrador")
    @Path("/count")
    public long count() {
        return administradorService.count();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response findById(@PathParam("id") Long id) {
        AdministradorResponseDTO user = administradorService.findById(id);
        if (user == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @GET
    @Path("/search/username/{nome}")
    @RolesAllowed("Administrador")
    public Response findByUsername(@PathParam("nome") String username, @QueryParam("page") @DefaultValue("0") int page, @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        return Response.ok(administradorService.findByUsername(username,page,pageSize)).build();
    }

    @GET
    @Path("/search/email/{email}")
    @RolesAllowed("Administrador")
    public Response findByEmail(@PathParam("email") String email, @QueryParam("page") @DefaultValue("0") int page, @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        return Response.ok(administradorService.findByEmail(email, page, pageSize)).build();
    }

    @GET
    @Path("/search/cpf/{cpf}")
    @RolesAllowed("Administrador")
    public Response findByCpf(@PathParam("cpf") String cpf, @QueryParam("page") @DefaultValue("0") int page, @QueryParam("pageSize") @DefaultValue("100") int pageSize) {
        return Response.ok(administradorService.findByCpf(cpf, page,pageSize)).build();
    }

    @POST
    @RolesAllowed("Administrador")
    @Transactional
    public Response create(AdministradorDTO adminDto) {
        return Response.status(Status.CREATED).entity(administradorService.create(adminDto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Administrador")
    @Transactional
    public Response update(@PathParam("id") Long id, AdministradorDTO adminDto) {
        AdministradorResponseDTO adminBanco = administradorService.findById(id);
        if (adminBanco == null)
            return Response.status(Status.NOT_FOUND).build();
        administradorService.update(id, adminDto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PUT
    @RolesAllowed("Administrador")
    @Path("/usuarios/edit/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, UsuarioDTO userDto) {
        UsuarioResponseDTO usuarioBanco = usuarioService.findById(id);
        if (usuarioBanco == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        usuarioService.update(id, userDto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Administrador")
    @Transactional
    public Response deleteById(@PathParam("id") Long id) {
        administradorService.deleteById(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}