package com.vacinacao.agvacinacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vacinacao.agvacinacao.dto.PacienteDTO;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.repository.PacienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Listar todos os pacientes
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    // Buscar paciente por ID
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    // Buscar paciente por CPF
    public Optional<Paciente> buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf);
    }

    // Cadastrar novo paciente
    public Paciente cadastrar(Paciente paciente) {
        
        // Regra para validar se já existe um paciente com esse CPF
        Optional<Paciente> existente = pacienteRepository.findByCpf(paciente.getCpf());
        if (existente.isPresent()) {
            throw new RuntimeException("Paciente com CPF já cadastrado: " + paciente.getCpf());
        }
        return pacienteRepository.save(paciente);
    }

    // Atualizar paciente
    public Paciente atualizar(Long id, Paciente pacienteAtualizado) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente pacienteExistente = pacienteOptional.get();
            pacienteExistente.setNome(pacienteAtualizado.getNome());
            pacienteExistente.setDataNascimento(pacienteAtualizado.getDataNascimento());
            pacienteExistente.setCpf(pacienteAtualizado.getCpf());
            pacienteExistente.setRua(pacienteAtualizado.getRua());
            pacienteExistente.setNumero(pacienteAtualizado.getNumero());
            pacienteExistente.setBairro(pacienteAtualizado.getBairro());
            pacienteExistente.setCidade(pacienteAtualizado.getCidade());
            pacienteExistente.setEstado(pacienteAtualizado.getEstado());
            return pacienteRepository.save(pacienteExistente);
        } else {
            throw new RuntimeException("Paciente não encontrado com ID: " + id);
        }
    }

    // Deletar paciente
    public void deletar(Long id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente criarPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setCpf(pacienteDTO.getCpf());
        paciente.setDataNascimento(pacienteDTO.getDataNascimento());
        paciente.setRua(pacienteDTO.getRua());
        paciente.setNumero(pacienteDTO.getNumero());
        paciente.setBairro(pacienteDTO.getBairro());
        paciente.setCidade(pacienteDTO.getCidade());
        paciente.setEstado(pacienteDTO.getEstado());

        return pacienteRepository.save(paciente);
    }
}
