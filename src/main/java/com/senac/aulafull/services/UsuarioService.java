package com.senac.aulafull.services;

import com.senac.aulafull.dto.LoginRequestDto;
import com.senac.aulafull.dto.UsuarioRequestDto;
import com.senac.aulafull.model.Usuario;
import com.senac.aulafull.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    @Transactional
    public boolean login(LoginRequestDto loginRequestDto){
        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(loginRequestDto.email(),loginRequestDto.senha());
    }

    public Usuario registrarUsuario(UsuarioRequestDto usuarioRequestDto) {
        if (usuarioRepository.findByEmail(usuarioRequestDto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        Usuario novoUsuario = new Usuario(null, usuarioRequestDto.nome(), usuarioRequestDto.cpf(), usuarioRequestDto.senha(), usuarioRequestDto.email(), "ROLE_USER");
        return usuarioRepository.save(novoUsuario);
    }
}