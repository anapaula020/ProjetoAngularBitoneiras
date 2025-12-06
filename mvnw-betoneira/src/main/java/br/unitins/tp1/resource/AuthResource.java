package br.unitins.tp1.resource;

import br.unitins.tp1.dto.AuthUsuarioDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.service.HashService;
import br.unitins.tp1.service.JwtService;
import br.unitins.tp1.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    ClienteService clienteService;

    @Inject
    JwtService jwtService;

    @POST
    public Response login(AuthUsuarioDTO authDTO) {
        String hash = hashService.getHashSenha(authDTO.senha());

        ClienteResponseDTO cliente = clienteService.findByLoginAndSenha(authDTO.login(), hash);

        if (cliente == null) {
            return Response.status(Status.NOT_FOUND)
                .entity("Usuario não encontrado").build();
        } 
        return Response.ok(cliente)
            .header("Authorization", jwtService.generateJwt(cliente))
            .build();
    }
}