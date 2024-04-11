package com.serviceschedule.repository;

import com.serviceschedule.model.Horario;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository {
    List<Horario> findByPrestadorIdAndDisponivelIsTrue(Long prestadorId);
}
