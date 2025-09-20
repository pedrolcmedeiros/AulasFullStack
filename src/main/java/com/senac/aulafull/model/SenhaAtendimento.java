package com.senac.aulafull.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "senhas_atendimento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenhaAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @Enumerated(EnumType.STRING)
    private TipoSenha tipo;

    @Enumerated(EnumType.STRING)
    private StatusSenha status;
}