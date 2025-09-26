package com.senac.aulafull.config;

import com.senac.aulafull.services.TokenService;
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

    // Usamos shouldNotFilter APENAS para a rota de LOGIN,
    // pois o token é obtido nela.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Se a requisição for para o endpoint de login, não aplicamos o filtro JWT.
        return request.getRequestURI().equals("/auth/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Tenta obter o token
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            // Se não houver token, ou for inválido, o filtro passa adiante.
            // O Spring Security irá tratar isso mais à frente, barrando requisições @authenticated().
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        // 2. Tenta validar o token e autenticar
        try {
            var usuario = tokenService.validarToken(token);

            // Se o token for válido, cria a autenticação com as Authorities (Roles)
            var autorizacao = new UsernamePasswordAuthenticationToken(
                    usuario.getEmail(), null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(autorizacao);

        } catch (Exception e) {
            // 3. Se o token for inválido/expirado, retorna 401 e encerra
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado. Acesse /auth/login novamente.");
            return;
        }

        // 4. Continua a cadeia de filtros (para o SecurityFilterChain)
        filterChain.doFilter(request, response);
    }
}