package com.senac.aulafull.domain.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "usuario_com_empresa")
public class UsuarioComEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", nullable = false) // Coluna empresa_id na tabela
    private Empresa empresa;

}
