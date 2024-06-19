    package com.janiks.forumHub.infra.security;

    import com.auth0.jwt.JWT;
    import com.auth0.jwt.algorithms.Algorithm;
    import com.auth0.jwt.exceptions.JWTCreationException;
    import com.auth0.jwt.exceptions.JWTVerificationException;
    import com.janiks.forumHub.domain.user.User;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    import java.time.Instant;
    import java.time.LocalDateTime;
    import java.time.ZoneOffset;

    @Service
    public class TokenService {

        @Value("&{api.security.token.secret}")
        private String secret;

        private final String ISSUER = "ForumHub";


        public String gerarToken(User usuario){
            try {
                var algoritimo = Algorithm.HMAC256(secret);
                return JWT.create()
                        .withIssuer(ISSUER)
                        .withSubject(usuario.getEmail())
                        .withExpiresAt(dataExpiracao())
                        .sign(algoritimo);
            } catch (JWTCreationException exception){
                throw new RuntimeException("Erro ao gerar token jwt", exception);
            }
        }

        public String getSubject(String tokenJWT){
            try {
                var algoritimo = Algorithm.HMAC256(secret);
                return JWT.require(algoritimo)
                        .withIssuer(ISSUER)
                        .build()
                        .verify(tokenJWT)
                        .getSubject();
            } catch (JWTVerificationException exception){
                throw new RuntimeException("Token JWT inválido ou expirado"+ tokenJWT);
            }
        }


        private Instant dataExpiracao() {
            return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
        }
    }
