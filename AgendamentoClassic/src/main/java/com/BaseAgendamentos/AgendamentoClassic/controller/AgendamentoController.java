package com.BaseAgendamentos.AgendamentoClassic.controller;

import com.BaseAgendamentos.AgendamentoClassic.infrastructure.entity.AgendamentoEntity;
import com.BaseAgendamentos.AgendamentoClassic.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping ("/agendamento")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoEntity> salvarAgendamento(@RequestBody AgendamentoEntity agendamento) {
            AgendamentoEntity agendamentoSalvo = agendamentoService.salvarAgendamento(agendamento);
            return ResponseEntity.ok(agendamentoSalvo);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(
            @RequestParam String cliente,
            @RequestParam LocalDateTime dataHoraAgendamento) {
        agendamentoService.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoEntity>> buscarAgendamentoDia(@RequestParam LocalDate data) {
        List<AgendamentoEntity> agendamentos = agendamentoService.buscarAgendamentosDia(data);
        return ResponseEntity.ok(agendamentos);
    }

    @PutMapping
    public ResponseEntity<AgendamentoEntity> alterarAgendamento(
            @RequestBody AgendamentoEntity agendamento,
            @RequestParam String cliente,
            @RequestParam LocalDateTime dataHoraAgendamento) {
        AgendamentoEntity agendamentoAlterado = agendamentoService.alterarAgendamento(agendamento, cliente, dataHoraAgendamento);
        return ResponseEntity.ok(agendamentoAlterado);
    }
}
