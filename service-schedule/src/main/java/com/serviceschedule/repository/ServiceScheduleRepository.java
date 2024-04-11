package com.serviceschedule.repository;

import com.serviceschedule.model.ServiceScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import com.serviceschedule.model.ServiceScheduleModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceScheduleRepository extends JpaRepository<ServiceScheduleModel, Long> {
    Optional<ServiceScheduleModel> findByNome(String nome);

    boolean existsByNome(String nome);
}
