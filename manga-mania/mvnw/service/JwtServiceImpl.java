package mssaat.org.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import mssaat.org.DTO.AdministradorResponseDTO;
import mssaat.org.DTO.UsuarioResponseDTO;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {
    private static final Duration EXPIRATION_TIME = Duration.ofHours(12);

    @Override
    public String generateJwt(AdministradorResponseDTO adminDto) {
        Instant now = Instant.now();
        Instant expiryDateInstant = now.plus(EXPIRATION_TIME);

        Set<String> roles = new HashSet<String>();
        roles.add("Administrador");

        return Jwt.issuer("unitins-jwt").subject(adminDto.username()).groups(roles).expiresAt(expiryDateInstant).sign();
    }

    @Override
    public String generateJwt(UsuarioResponseDTO userDto) {
        Instant now = Instant.now();
        Instant expiryDateInstant = now.plus(EXPIRATION_TIME);

        Set<String> roles = new HashSet<String>();
        roles.add("Usuario");

        return Jwt.issuer("unitins-jwt").subject(userDto.username()).groups(roles).expiresAt(expiryDateInstant).sign();
    }
}
