package com.BaseAgendamentos.AgendamentoClassic.infrastructure.repository;

import com.BaseAgendamentos.AgendamentoClassic.infrastructure.entity.AgendamentoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {
    boolean existsByServicoAndDataHoraAgendamentoBetween(String servico, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);

    boolean existsByServicoAndDataHoraAgendamentoBetweenAndIdNot(
            String servico,
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim,
            Long id
    );

    @Transactional
    long deleteByDataHoraAgendamentoAndClienteIgnoreCase(LocalDateTime dataHoraAgendamento, String cliente);

    List<AgendamentoEntity> findByDataHoraAgendamentoBetween(LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal);

    AgendamentoEntity findByDataHoraAgendamentoAndCliente(LocalDateTime dataHoraAgendamento, String cliente);
}
