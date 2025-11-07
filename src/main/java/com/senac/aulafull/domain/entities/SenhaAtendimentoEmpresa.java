package com.senac.aulafull.domain.entities;

import com.senac.aulafull.domain.entities.Empresa;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "senha_atendimento_empresa")
public class SenhaAtendimentoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // 🔑 CHAVE ESTRANGEIRA DA EMPRESA
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", nullable = false) // Coluna empresa_id na tabela
    private Empresa empresa;
}