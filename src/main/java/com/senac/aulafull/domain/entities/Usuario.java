package com.senac.aulafull.domain.entities;

import com.senac.aulafull.application.dto.usuario.UsuarioRequestDto;
import com.senac.aulafull.application.dto.usuario.UsuarioResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario implements UserDetails {


    //implementar o UserDetails em tds


    public Usuario(UsuarioRequestDto usuarioRequest) {
        this.setCpf(usuarioRequest.cpf());
        this.setNome(usuarioRequest.nome());
        this.setEmail(usuarioRequest.email());
        this.setSenha(usuarioRequest.senha());
        this.setRole(usuarioRequest.role());

        if(this.getDataCadastro() == null){
            this.setDataCadastro(LocalDateTime.now());
        }
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String senha;
    private String email;

    private String role;

    private LocalDateTime dataCadastro;

    private String tokenSenha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if ("ROLE_ATENDENTE".equals(this.role)) {
            return List.of(new SimpleGrantedAuthority("ROLE_ATENDENTE"),
                    new SimpleGrantedAuthority("ROLE_PACIENTE"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_PACIENTE"));
        }

    }


    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public UsuarioResponseDto toDtoResponse() {
        return new UsuarioResponseDto(this);
    }
}
