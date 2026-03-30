package com.BaseAgendamentos.AgendamentoClassic.controller;

import com.BaseAgendamentos.AgendamentoClassic.infrastructure.entity.AgendamentoEntity;
import com.BaseAgendamentos.AgendamentoClassic.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
