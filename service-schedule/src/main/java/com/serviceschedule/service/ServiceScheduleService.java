package com.serviceschedule.service;

import com.serviceschedule.exception.PrestadorDuplicadoException;
import com.serviceschedule.exception.PrestadorNaoEncontradoException;
import com.serviceschedule.model.ServiceScheduleModel;
import com.serviceschedule.repository.ServiceScheduleRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.SneakyThrows;
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
        String nomePrestador = serviceScheduleModel.getNome();

        if (nomePrestadorJaCadastrado(nomePrestador)) {
            throw new PrestadorDuplicadoException("Já existe um prestador com este nome cadastrado");
        }

        return serviceScheduleRepository.save(serviceScheduleModel);
    }

    public boolean nomePrestadorJaCadastrado(String nome) {
        return serviceScheduleRepository.existsByNome(nome);
    }

    public void deletePrestador(Long id) {
            if (!serviceScheduleRepository.existsById(id)) {
                throw new PrestadorNaoEncontradoException("Prestador não encontrado com o ID: " + id);
            }
            serviceScheduleRepository.deleteById(id);
        }
}
