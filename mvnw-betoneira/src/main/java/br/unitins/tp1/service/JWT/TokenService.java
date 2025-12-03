// src/main/java/br/unitins/tp1/service/JWT/TokenService.java
package br.unitins.tp1.service.JWT;

import br.unitins.tp1.model.Cliente;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
// Removed unused import: import java.util.stream.Collectors;

@ApplicationScoped
public class TokenService {

    private static final String ISSUER = "unitins-tp1-issuer";

    public String generateJwt(Cliente cliente) {
        Set<String> roles = cliente.getRolesAsSet();

        return Jwt.issuer(ISSUER)
                .upn(cliente.getEmail())
                .groups(roles)
                .expiresIn(Duration.ofHours(24))
                .issuedAt(Instant.now())
                .claim("userId", cliente.getId())
                .sign();
    }
}