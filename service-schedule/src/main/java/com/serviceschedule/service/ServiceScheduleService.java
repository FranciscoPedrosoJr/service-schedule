package com.serviceschedule.service;

import com.serviceschedule.model.ServiceScheduleModel;
import com.serviceschedule.repository.ServiceScheduleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceScheduleService {

    private final ServiceScheduleRepository serviceScheduleRepository;

    @Autowired
    public ServiceScheduleService(ServiceScheduleRepository serviceScheduleRepository){
        this.serviceScheduleRepository = serviceScheduleRepository;
    }


    public List<ServiceScheduleModel> getAllPrestadores() {
        return serviceScheduleRepository.findAll();
    }

    public Optional<ServiceScheduleModel> getPrestadorById(Long id) {
        return serviceScheduleRepository.findById(id);
    }

    public ServiceScheduleModel savePrestador(ServiceScheduleModel serviceScheduleModel) {
        return serviceScheduleRepository.save(serviceScheduleModel);
    }

    public void deletePrestador(Long id) {
        serviceScheduleRepository.deleteById(id);
    }
}
