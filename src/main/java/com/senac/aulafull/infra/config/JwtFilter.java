package com.senac.aulafull.infra.config;

import com.senac.aulafull.application.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals("/auth/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //   Obter o token
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        //  Validar o token e autenticar
        try {
            var usuario = tokenService.validarToken(token);

            // Se o token for válido, cria a autenticação com as Authorities (Roles)
            var autorizacao = new UsernamePasswordAuthenticationToken(
                    usuario,
                    null,
                    usuario.autorizacao());

            SecurityContextHolder.getContext().setAuthentication(autorizacao);

        } catch (Exception e) {
            // Se o token for inválido/expirado, retorna 401 e encerra
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado. Acesse /auth/login novamente.");
            return;
        }

        // Continua a cadeia de filtros (para o SecurityFilterChain)
        filterChain.doFilter(request, response);
    }
}