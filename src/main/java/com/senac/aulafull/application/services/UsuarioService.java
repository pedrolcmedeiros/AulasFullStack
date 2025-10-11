package com.senac.aulafull.application.services;

import com.senac.aulafull.application.dto.login.LoginRequestDto;
import com.senac.aulafull.application.dto.usuario.UsuarioRequestDto;
import com.senac.aulafull.application.dto.usuario.UsuarioResponseDto;
import com.senac.aulafull.domain.entities.Usuario;
import com.senac.aulafull.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
    public boolean login(LoginRequestDto loginRequestDto) {
        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(loginRequestDto.email(), loginRequestDto.senha());
    }

    public UsuarioResponseDto consultarPorId(Long id) {

        return usuarioRepository.findById(id)
                .map(UsuarioResponseDto::new)
                .orElse(null);
    }

    public List<UsuarioResponseDto> consultarTodosSemFiltro() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioResponseDto salvarUsuario(UsuarioRequestDto usuarioRequest) {
        var usuario = usuarioRepository.findByCpf(usuarioRequest.cpf())
                .map(u -> {
                    u.setNome(usuarioRequest.nome());
                    u.setSenha(usuarioRequest.senha());
                    u.setRole(usuarioRequest.role());
                    u.setEmail(usuarioRequest.email());
                    return u;
                })
                .orElse(new Usuario(usuarioRequest));

        usuarioRepository.save(usuario);

        return usuario.toDtoResponse();
    }

    public List<UsuarioResponseDto> consultarPaginadoFiltrado(Long take, Long page, String filtro) {

        return usuarioRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Usuario::getId).reversed())
                .filter(p -> p.getDataCadastro().isAfter(LocalDateTime.now().plusDays(-7)))
                .filter(a -> filtro != null ? a.getNome().contains(filtro) : true)
                .skip((long)page * take)
                .limit(take)
                .map(UsuarioResponseDto::new)
                .collect(Collectors.toList());
    }
}