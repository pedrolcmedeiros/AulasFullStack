package com.senac.aulafull.services;

import com.senac.aulafull.dto.UsuarioRequestDto;
import com.senac.aulafull.model.Usuario;
import com.senac.aulafull.repository.UsuarioRepository;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    public Usuario registrarUsuario(UsuarioRequestDto usuarioRequestDto) {
        if (usuarioRepository.findByEmail(usuarioRequestDto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        String senhaCriptografada = passwordEncoder.encode(usuarioRequestDto.senha());
        Usuario novoUsuario = new Usuario(null, usuarioRequestDto.nome(), usuarioRequestDto.cpf(), senhaCriptografada, usuarioRequestDto.email(), "ROLE_USER");
        return usuarioRepository.save(novoUsuario);
    }
}