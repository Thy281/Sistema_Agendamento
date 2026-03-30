package com.BaseAgendamentos.AgendamentoClassic.service;

import com.BaseAgendamentos.AgendamentoClassic.infrastructure.entity.AgendamentoEntity;
import com.BaseAgendamentos.AgendamentoClassic.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\(\\d{2}\\)\\s?9?\\d{4}-?\\d{4}$");

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoEntity salvarAgendamento(AgendamentoEntity agendamento) {
        validarTelefone(agendamento.getTelefone());

        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusHours(1);

        boolean jaExisteConflito = agendamentoRepository.existsByServicoAndDataHoraAgendamentoBetween(
                agendamento.getServico(),
                horaAgendamento,
                horaFim
        );

        if (!jaExisteConflito) {
            return agendamentoRepository.save(agendamento);
        } else {
            throw new RuntimeException("Já existe um agendamento para este serviço no horário solicitado.");
        }
    }

    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente) {
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    public List<AgendamentoEntity> buscarAgendamentosDia(LocalDate data) {
        LocalDateTime primeiraHoraDoDia = data.atStartOfDay();
        LocalDateTime horaFinalDia = data.atTime(23, 59, 59);

        List<AgendamentoEntity> agendamentoHoras = agendamentoRepository.findByDataHoraAgendamentoBetween(
                primeiraHoraDoDia,
                horaFinalDia
        );

        if (agendamentoHoras.isEmpty()) {
            throw new RuntimeException("Não existem agendamentos para o dia solicitado.");
        } else {
            return agendamentoHoras;
        }
    }

    public AgendamentoEntity alterarAgendamento(AgendamentoEntity agendamento, String cliente, LocalDateTime dataHoraAgendamento) {
        AgendamentoEntity agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

        if (Objects.isNull(agenda)) {
            throw new RuntimeException("Agendamento não encontrado para o cliente e horário especificados.");
        }

        String novoServico = escolherValor(agendamento.getServico(), agenda.getServico());
        String novoProfissional = escolherValor(agendamento.getProfissional(), agenda.getProfissional());
        String novoCliente = escolherValor(agendamento.getCliente(), agenda.getCliente());
        String novoTelefone = escolherValor(agendamento.getTelefone(), agenda.getTelefone());
        LocalDateTime novaDataHora = Objects.nonNull(agendamento.getDataHoraAgendamento())
                ? agendamento.getDataHoraAgendamento()
                : agenda.getDataHoraAgendamento();

        validarTelefone(novoTelefone);

        LocalDateTime horaFim = novaDataHora.plusHours(1);
        boolean conflito = agendamentoRepository.existsByServicoAndDataHoraAgendamentoBetweenAndIdNot(
                novoServico,
                novaDataHora,
                horaFim,
                agenda.getId()
        );

        if (conflito) {
            throw new RuntimeException("Já existe um agendamento para este serviço no horário solicitado.");
        }

        agenda.setServico(novoServico);
        agenda.setProfissional(novoProfissional);
        agenda.setCliente(novoCliente);
        agenda.setTelefone(novoTelefone);
        agenda.setDataHoraAgendamento(novaDataHora);

        return agendamentoRepository.save(agenda);
    }

    private void validarTelefone(String telefone) {
        if (Objects.isNull(telefone) || !TELEFONE_PATTERN.matcher(telefone).matches()) {
            throw new RuntimeException("Telefone inválido. Use o formato (XX) 9XXXX-XXXX ou (XX) XXXX-XXXX");
        }
    }

    private String escolherValor(String novoValor, String valorAtual) {
        return Objects.nonNull(novoValor) ? novoValor : valorAtual;
    }
}
