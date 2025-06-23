package com.vacinacao.agvacinacao.config;

import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.TipoUsuario;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.repository.PacienteRepository;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class StartupRunner {

    @Bean
    public CommandLineRunner criarAdminComPaciente(
        UsuarioRepository usuarioRepository,
        PacienteRepository pacienteRepository,
        PasswordEncoder passwordEncoder
    ) {
        return args -> {
            String email = "admin@admin.com";

            if (usuarioRepository.findByEmail(email).isEmpty()) {
                // Cria o usuário ADMIN
                Usuario admin = new Usuario();
                admin.setNome("Admin Padrão");
                admin.setEmail(email);
                admin.setSenha(passwordEncoder.encode("123456"));
                admin.setTipo(TipoUsuario.ADMIN);
                usuarioRepository.save(admin);

                // Cria o paciente vinculado ao usuário
                Paciente paciente = new Paciente();
                paciente.setNome("João Silva");
                paciente.setCpf("12345678900");
                paciente.setDataNascimento(LocalDate.of(1990, 5, 15));
                paciente.setRua("Rua das Flores");
                paciente.setNumero("123");
                paciente.setBairro("Centro");
                paciente.setCidade("São Paulo");
                paciente.setEstado("SP");
                paciente.setUsuario(admin); // vincula o usuário ao paciente

                pacienteRepository.save(paciente);

                System.out.println("✅ Usuário ADMIN + Paciente criado com sucesso.");
            } else {
                System.out.println("ℹ️ Usuário ADMIN já existe.");
            }
        };
    }
}
