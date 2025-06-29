package com.vacinacao.agvacinacao.service;

import com.vacinacao.agvacinacao.dto.PacienteDTO;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.TipoUsuario;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.repository.PacienteRepository;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public PacienteDTO buscarPorUsuarioId(Long usuarioId) {
        Paciente paciente = pacienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
        return new PacienteDTO(paciente);
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
    public Paciente atualizar(Long id, PacienteDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));

        paciente.setNome(dto.getNome());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setCpf(dto.getCpf());
        paciente.setRua(dto.getRua());
        paciente.setNumero(dto.getNumero());
        paciente.setBairro(dto.getBairro());
        paciente.setCidade(dto.getCidade());
        paciente.setEstado(dto.getEstado());

        return pacienteRepository.save(paciente);
    }

    // Deletar paciente
    public void deletar(Long id) {
        pacienteRepository.deleteById(id);
    }
}
