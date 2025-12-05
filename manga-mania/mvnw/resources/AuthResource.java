package mssaat.org.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import mssaat.org.DTO.AdministradorResponseDTO;
import mssaat.org.DTO.AuthUsuarioDTO;
import mssaat.org.DTO.UsuarioResponseDTO;
import mssaat.org.service.AdministradorService;
import mssaat.org.service.HashService;
import mssaat.org.service.JwtService;
import mssaat.org.service.UsuarioService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    public AdministradorService administradorService;

    @Inject
    public UsuarioService usuarioService;

    @Inject
    public HashService hashService;

    @Inject
    public JwtService jwtService;

    @POST
    public Response login(AuthUsuarioDTO dto) {
        String hash = hashService.getHashSenha(dto.senha());
        AdministradorResponseDTO admin = administradorService.login(dto.username(), hash);
        UsuarioResponseDTO usuario = usuarioService.login(dto.username(), hash);
        if (admin != null) {
            return Response.ok(admin).header("Authorization", jwtService.generateJwt(admin)).build();
        } else if (usuario != null) {
            return Response.ok(usuario).header("Authorization", jwtService.generateJwt(usuario)).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}