package com.vacinacao.agvacinacao.service;

import com.vacinacao.agvacinacao.dto.DoencaDTO;
import com.vacinacao.agvacinacao.model.Doenca;
import com.vacinacao.agvacinacao.repository.DoencaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoencaService {

    @Autowired
    private DoencaRepository doencaRepository;

    public DoencaDTO criarDoenca(DoencaDTO dto) {
        Doenca doenca = new Doenca(
                dto.getNome(),
                dto.getData(),
                dto.getEndereco(),
                dto.getCasos(),
                dto.getSintomas(),
                dto.getMedidasPreventivas());

        return new DoencaDTO(doencaRepository.save(doenca));
    }

    public DoencaDTO atualizarDoenca(Long id, DoencaDTO dto) {
        Doenca doenca = doencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doença não encontrada com id: " + id));

        doenca.setNome(dto.getNome());
        doenca.setData(dto.getData());
        doenca.setEndereco(dto.getEndereco());
        doenca.setCasos(dto.getCasos());
        doenca.setSintomas(dto.getSintomas());
        doenca.setMedidasPreventivas(dto.getMedidasPreventivas());

        return new DoencaDTO(doencaRepository.save(doenca));
    }

    public List<DoencaDTO> listarDoencas() {
        return doencaRepository.findAll().stream().map(DoencaDTO::new).collect(Collectors.toList());
    }

    public void excluirDoenca(Long id) {
        doencaRepository.deleteById(id);
    }
}
