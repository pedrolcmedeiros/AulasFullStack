package com.senac.aulafull.domain.entities;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

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
        this.id = UUID.randomUUID(); // ou usar o @GeneratedValue do JPA
    }

    protected Empresa() {}

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}