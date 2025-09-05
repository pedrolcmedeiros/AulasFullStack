package com.senac.aulafull.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.senac.aulafull.dto.LoginRequestDto;
import com.senac.aulafull.model.Token;
import com.senac.aulafull.model.Usuario;
import com.senac.aulafull.repository.TokenRepository;
import com.senac.aulafull.repository.UsuarioRepository;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.secretkey}")
    private String secret;

    @Value("${spring.tempo_expiracao}")
    private Long tempo;

    //Emissor é que emite o token, pode ser o nome do projeto (setado auto)
    private String emissor = "DEVTEST";

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String gerarToken(LoginRequestDto loginRequestDto){

        var usuario = usuarioRepository.findByEmail(loginRequestDto.email()).orElse(null);

        //Serve para pegar a secret e gerar uma chave HMAC256
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(usuario.getEmail())
                .withExpiresAt(this.gerarDataExpiracao())
                .sign(algorithm);

        tokenRepository.save(new Token(null, token, usuario));

        return token;
    }

    public Usuario validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(emissor).build();

        verifier.verify(token);

        var tokenResult = tokenRepository.findByToken(token).orElse(null);

        if(tokenResult == null){
            throw new IllegalArgumentException("Token inválido");
        }
        return tokenResult.getUsuario();
    }

    private Instant gerarDataExpiracao(){
        var dataAtual = LocalDateTime.now();
        dataAtual = dataAtual.plusMinutes(tempo);

        return dataAtual.toInstant(ZoneOffset.of("-03:00"));

    }
}
