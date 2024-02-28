package com.serviceschedule.repository;

import com.serviceschedule.model.ServiceScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceScheduleRepository extends JpaRepository<ServiceScheduleModel, Long> {
}
