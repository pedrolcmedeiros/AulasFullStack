package com.senac.aulafull.application.services;

import com.senac.aulafull.domain.entities.SenhaAtendimento;
import com.senac.aulafull.domain.enuns.StatusSenha;
import com.senac.aulafull.domain.enuns.TipoSenha;
import com.senac.aulafull.domain.repository.SenhaAtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SenhaAtendimentoService {

    @Autowired
    private SenhaAtendimentoRepository senhaAtendimentoRepository;

    private int numeroSenhaNormal = 0;
    private int numeroSenhaPreferencial = 0;

    public SenhaAtendimento gerarSenha(TipoSenha tipo) {
        String numeroGerado;
        if (tipo.equals(TipoSenha.PREFERENCIAL)) {
            numeroSenhaPreferencial++;
            numeroGerado = "P" + String.format("%03d", numeroSenhaPreferencial);
        } else {
            numeroSenhaNormal++;
            numeroGerado = "N" + String.format("%03d", numeroSenhaNormal);
        }

        SenhaAtendimento novaSenha = new SenhaAtendimento();
        novaSenha.setNumero(numeroGerado);
        novaSenha.setTipo(tipo);
        novaSenha.setStatus(StatusSenha.AGUARDANDO);

        return senhaAtendimentoRepository.save(novaSenha);
    }

    public Optional<SenhaAtendimento> chamarProximaSenha() {
        // 1. Tenta encontrar a próxima senha preferencial aguardando
        Optional<SenhaAtendimento> proximaPreferencial = senhaAtendimentoRepository.findFirstByStatusAndTipoOrderByNumeroAsc(StatusSenha.AGUARDANDO, TipoSenha.PREFERENCIAL);

        if (proximaPreferencial.isPresent()) {
            SenhaAtendimento senha = proximaPreferencial.get();
            senha.setStatus(StatusSenha.CHAMADA);
            return Optional.of(senhaAtendimentoRepository.save(senha));
        }

        // 2. Se não houver preferencial, busca a próxima senha normal aguardando
        Optional<SenhaAtendimento> proximaNormal = senhaAtendimentoRepository.findFirstByStatusAndTipoOrderByNumeroAsc(StatusSenha.AGUARDANDO, TipoSenha.NORMAL);

        if (proximaNormal.isPresent()) {
            SenhaAtendimento senha = proximaNormal.get();
            senha.setStatus(StatusSenha.CHAMADA);
            return Optional.of(senhaAtendimentoRepository.save(senha));
        }

        return Optional.empty();
    }

    public Optional<SenhaAtendimento> finalizarAtendimento(Long id) {
        return senhaAtendimentoRepository.findById(id).map(senha -> {
            senha.setStatus(StatusSenha.ATENDIDA);

            return senhaAtendimentoRepository.save(senha);
        });
    }
    public List<SenhaAtendimento> listarSenhasAtivas() {
        // Define quais status são considerados "ativos" para o atendente
        List<StatusSenha> statusesAtivos = Arrays.asList(
                StatusSenha.AGUARDANDO,
                StatusSenha.CHAMADA
        );

        return senhaAtendimentoRepository.findByStatusIn(statusesAtivos);
    }
    public List<SenhaAtendimento> listarSenhasAtendidas() {
        // Usa o método do Repository para buscar todas com o status 'ATENDIDA'
        return senhaAtendimentoRepository.findByStatus(StatusSenha.ATENDIDA);
    }
};
