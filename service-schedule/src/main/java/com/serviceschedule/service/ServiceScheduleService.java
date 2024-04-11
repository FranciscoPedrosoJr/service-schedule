package com.serviceschedule.service;

import com.serviceschedule.exception.HorarioDuplicadoException;
import com.serviceschedule.exception.PrestadorDuplicadoException;
import com.serviceschedule.exception.PrestadorNaoEncontradoException;
import com.serviceschedule.model.Horario;
import com.serviceschedule.model.ServiceScheduleModel;
import com.serviceschedule.repository.ServiceScheduleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public List<Horario> getHorariosDisponiveisByPrestadorId(Long idPrestador) {
        Optional<ServiceScheduleModel> optionalPrestador = getPrestadorById(idPrestador);

        if(optionalPrestador.isPresent())
        {
            ServiceScheduleModel prestador = optionalPrestador.get();

            List<Horario> horariosDisponiveis = prestador.getHorarios().stream()
                    .filter(Horario::isDisponivel)
                    .collect(Collectors.toList());

            return horariosDisponiveis;
        } else {
            throw new PrestadorNaoEncontradoException("Prestador não encontrado com o ID: " + idPrestador);
        }
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


    public void associarHorariosAoPrestador(Long idPrestador, List<Horario> horarios) {
        ServiceScheduleModel prestador = serviceScheduleRepository.findById(idPrestador)
                .orElseThrow(() -> new PrestadorNaoEncontradoException("Prestador não encontrado com o ID: " + idPrestador));

        List<Horario> horariosExistente = prestador.getHorarios();

        for (Horario novoHorario : horarios) {
            boolean duplicado = horariosExistente.stream()
                    .anyMatch(horario -> horario.getDiaSemana().equals(novoHorario.getDiaSemana())
                            && horario.getHora().equals(novoHorario.getHora()));

            if (duplicado) {
                throw new HorarioDuplicadoException("Horário duplicado para o prestador: " + prestador.getId());
            }

            novoHorario.setPrestador(prestador);
            novoHorario.setDisponivel(true);

            horariosExistente.add(novoHorario);
        }

        serviceScheduleRepository.save(prestador);

    }
}
