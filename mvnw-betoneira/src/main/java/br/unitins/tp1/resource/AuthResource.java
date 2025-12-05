package br.unitins.tp1.resource;

import br.unitins.tp1.dto.LoginRequestDTO;
import br.unitins.tp1.dto.LoginResponseDTO;
import br.unitins.tp1.exception.ServiceException;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.service.JWT.TokenService;
import br.unitins.tp1.utils.HashUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    TokenService tokenService;

    @POST
    public Response login(LoginRequestDTO authRequest) {
        Cliente cliente = clienteRepository.findByEmail(authRequest.getEmail());

        if (cliente == null) {
            throw new ServiceException("Email ou senha inválidos.", Response.Status.UNAUTHORIZED);
        }

        if (!HashUtil.verifyPassword(authRequest.getSenha(), cliente.getSenha())) {
            throw new ServiceException("Email ou senha inválidos.", Response.Status.UNAUTHORIZED);
        }

        String token = tokenService.generateJwt(cliente);

        LoginResponseDTO responseDTO = new LoginResponseDTO(cliente.getId(), cliente.getEmail(), token);
        return Response.ok(responseDTO).build();
    }
}