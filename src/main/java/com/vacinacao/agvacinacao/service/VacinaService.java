package com.vacinacao.agvacinacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vacinacao.agvacinacao.dto.VacinaDTO;
import com.vacinacao.agvacinacao.model.Vacina;
import com.vacinacao.agvacinacao.repository.VacinaRepository;

import java.util.List;

@Service
public class VacinaService {

    @Autowired
    private VacinaRepository vacinaRepository;

    public Vacina buscarPorId(Long id) {
        return vacinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada"));
    }

    // Listar todas as vacinas
    public List<Vacina> listarTodas() {
        return vacinaRepository.findAll();
    }

    // Cadastrar nova vacina
    public Vacina cadastrar(Vacina vacina) {
        return vacinaRepository.save(vacina);
    }

    public Vacina criarVacina(VacinaDTO dto) {
        Vacina vacina = new Vacina();
        vacina.setNome(dto.getNome());
        vacina.setDescricao(dto.getDescricao());
        vacina.setFabricante(dto.getFabricante());
        vacina.setQuantidadeDisponivel(dto.getQuantidadeDisponivel());
        vacina.setDiasParaReaplicacao(dto.getDiasParaReaplicacao());
        return vacinaRepository.save(vacina);
    }

    // Atualizar vacina
    public Vacina atualizar(Long id, VacinaDTO dto) {
        Vacina vacina = vacinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada"));

        vacina.setNome(dto.getNome());
        vacina.setDescricao(dto.getDescricao());
        vacina.setFabricante(dto.getFabricante());
        vacina.setQuantidadeDisponivel(dto.getQuantidadeDisponivel());
        vacina.setDiasParaReaplicacao(dto.getDiasParaReaplicacao());

        return vacinaRepository.save(vacina);
    }

    // decrementar quantidade de vacinas
    public void decrementarQuantidade(Long id, int quantidade) {
        Vacina vacina = buscarPorId(id);
        if (vacina.getQuantidadeDisponivel() < quantidade) {
            throw new IllegalArgumentException("Quantidade insuficiente.");
        }
        vacina.setQuantidadeDisponivel(vacina.getQuantidadeDisponivel() - quantidade);
        vacinaRepository.save(vacina);
    }

    // Deletar vacina
    public void deletarVacina(Long id) {
        Vacina vacina = buscarPorId(id);
        vacinaRepository.delete(vacina);
    }

}
