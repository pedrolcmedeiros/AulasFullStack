package com.senac.aulafull.application.dto.usuario;

public record UsuarioRequestDto(String nome, String cpf, String email, String senha, String role) {
}
