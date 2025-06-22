package com.vacinacao.agvacinacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vacinacao.agvacinacao.dto.VacinaDTO;
import com.vacinacao.agvacinacao.model.Vacina;
import com.vacinacao.agvacinacao.service.VacinaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vacinas")
public class VacinaController {

    @Autowired
    private VacinaService vacinaService;

    @PostMapping
    public VacinaDTO criarVacina(@RequestBody VacinaDTO vacinaDTO) {
        Vacina vacina = vacinaService.criarVacina(vacinaDTO);
        return new VacinaDTO(vacina);
    }

    @GetMapping
    public List<VacinaDTO> listarVacinas() {
        List<Vacina> vacinas = vacinaService.listarTodas();
        return vacinas.stream().map(VacinaDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VacinaDTO buscarVacina(@PathVariable Long id) {
        Vacina vacina = vacinaService.buscarPorId(id);
        return new VacinaDTO(vacina);
    }

    @PutMapping("/{id}/incrementar")
    public void incrementar(@PathVariable Long id, @RequestParam int quantidade) {
        vacinaService.incrementarQuantidade(id, quantidade);
    }

    @PutMapping("/{id}/decrementar")
    public void decrementar(@PathVariable Long id, @RequestParam int quantidade) {
        vacinaService.decrementarQuantidade(id, quantidade);
    }

    @DeleteMapping("/{id}")
    public void deletarVacina(@PathVariable Long id) {
        vacinaService.deletarVacina(id);
    }
}
