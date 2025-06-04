package com.vacinacao.agvacinacao.service;

import com.vacinacao.agvacinacao.dto.PacienteDTO;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.TipoUsuario;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.repository.PacienteRepository;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // Cadastrar novo paciente com criação de usuário
    public Paciente criarPaciente(PacienteDTO dto) {

        // Verifica se CPF já existe
        if (pacienteRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new RuntimeException("Paciente com CPF já cadastrado: " + dto.getCpf());
        }

        // Cria o usuário
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setTipo(TipoUsuario.PACIENTE);
        usuario = usuarioRepository.save(usuario);

        // Cria o paciente
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setRua(dto.getRua());
        paciente.setNumero(dto.getNumero());
        paciente.setBairro(dto.getBairro());
        paciente.setCidade(dto.getCidade());
        paciente.setEstado(dto.getEstado());
        paciente.setUsuario(usuario);

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
}
