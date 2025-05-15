package com.vacinacao.agvacinacao.controller;

import com.vacinacao.agvacinacao.dto.DoencaDTO;
import com.vacinacao.agvacinacao.service.DoencaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doencas")
public class DoencaController {

    @Autowired
    private DoencaService doencaService;

    @PostMapping
    public DoencaDTO criar(@RequestBody DoencaDTO dto) {
        return doencaService.criarDoenca(dto);
    }

    @PutMapping("/{id}")
    public DoencaDTO atualizar(@PathVariable Long id, @RequestBody DoencaDTO dto) {
        return doencaService.atualizarDoenca(id, dto);
    }

    @GetMapping
    public List<DoencaDTO> listar() {
        return doencaService.listarDoencas();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        doencaService.excluirDoenca(id);
    }
}
