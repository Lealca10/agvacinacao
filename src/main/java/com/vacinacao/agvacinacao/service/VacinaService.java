package com.vacinacao.agvacinacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vacinacao.agvacinacao.dto.VacinaDTO;
import com.vacinacao.agvacinacao.model.Vacina;
import com.vacinacao.agvacinacao.repository.VacinaRepository;

import java.util.List;
import java.util.Optional;

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
    public Vacina atualizar(Long id, Vacina vacinaAtualizada) {
        Optional<Vacina> vacinaOptional = vacinaRepository.findById(id);
        if (vacinaOptional.isPresent()) {
            Vacina vacinaExistente = vacinaOptional.get();
            vacinaExistente.setNome(vacinaAtualizada.getNome());
            vacinaExistente.setDescricao(vacinaAtualizada.getDescricao());
            vacinaExistente.setFabricante(vacinaAtualizada.getFabricante());
            vacinaExistente.setQuantidadeDisponivel(vacinaAtualizada.getQuantidadeDisponivel());
            return vacinaRepository.save(vacinaExistente);
        } else {
            throw new RuntimeException("Vacina não encontrada com ID: " + id);
        }
    }
    
    // Incrementar quantidade de vacinas
    public void incrementarQuantidade(Long id, int quantidade) {
        Vacina vacina = buscarPorId(id);
        vacina.setQuantidadeDisponivel(vacina.getQuantidadeDisponivel() + quantidade);
        vacinaRepository.save(vacina);
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
