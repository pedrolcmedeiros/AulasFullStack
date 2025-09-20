package com.senac.aulafull.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        return http.cors(Customizer.withDefaults())
                        .csrf(AbstractHttpConfigurer:: disable)
                        .authorizeHttpRequests( auth ->
                                        auth.requestMatchers("/auth/login").permitAll()

                                                // Rotas para gerar e listar senhas de atendimento
                                                .requestMatchers(HttpMethod.POST, "/senhas/gerar").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/senhas/listar").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/senhas/chamar").hasRole("ATENDENTE") //Lista que a penas o atendente pode acessar

                                                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                                                .requestMatchers("/swagger-resources/**").permitAll()
                                                .requestMatchers("/swagger-ui/**").permitAll()
                                                .requestMatchers("/v3/api-docs/**").permitAll()
                                                .requestMatchers("/usuarios").hasRole("ADMIN") //A Lista de usuarios apenas adm podem ver
                                                .anyRequest().authenticated()


                                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
