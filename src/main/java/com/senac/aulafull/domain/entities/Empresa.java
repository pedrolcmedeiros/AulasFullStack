package com.senac.aulafull.domain.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empresa")
public class Empresa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cnpj;

    public Empresa(String nome, String cnpj) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da empresa é obrigatório.");
        }
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ da empresa é obrigatório.");
        }
        this.nome = nome;
        this.cnpj = cnpj;
    }

    }