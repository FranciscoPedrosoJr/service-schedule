package com.serviceschedule.service;


import com.serviceschedule.exception.HorarioDisponivelException;
import com.serviceschedule.exception.HorarioDuplicadoException;
import com.serviceschedule.exception.HorarioNaoDisponivelException;
import com.serviceschedule.exception.HorarioNaoEncontradoException;
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
    public ServiceScheduleService(ServiceScheduleRepository serviceScheduleRepository) {

        this.serviceScheduleRepository = serviceScheduleRepository;
    }


    public List<ServiceScheduleModel> getAllPrestadores() {
        return serviceScheduleRepository.findAll();
    }

    public Optional<ServiceScheduleModel> getPrestadorById(Long id) {
        return serviceScheduleRepository.findById(id);
    }

    public List<Horario> getHorariosDisponiveisByPrestadorId(Long idPrestador) {

        final Optional<ServiceScheduleModel> optionalPrestador = getPrestadorById(idPrestador);

        if (optionalPrestador.isPresent()) {
            final ServiceScheduleModel prestador = optionalPrestador.get();

            final List<Horario> horariosDisponiveis = prestador.getHorarios().stream()
                    .filter(Horario::isDisponivel)
                    .collect(Collectors.toList());

            return horariosDisponiveis;
        } else {
            throw new PrestadorNaoEncontradoException(idPrestador);
        }
    }

    public ServiceScheduleModel savePrestador(ServiceScheduleModel serviceScheduleModel) {

        final String nomePrestador = serviceScheduleModel.getNome();

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
            throw new PrestadorNaoEncontradoException(id);

        }
        serviceScheduleRepository.deleteById(id);
    }


    public void associarHorariosAoPrestador(Long idPrestador, List<Horario> horarios) {
        final ServiceScheduleModel prestador = serviceScheduleRepository.findById(idPrestador)

                .orElseThrow(() -> new PrestadorNaoEncontradoException(idPrestador));

        final List<Horario> horariosExistente = prestador.getHorarios();

        for (Horario novoHorario : horarios) {
            final boolean duplicado = horariosExistente.stream()
                    .anyMatch(horario -> horario.getDiaSemana().equals(novoHorario.getDiaSemana())
                            && horario.getHora().equals(novoHorario.getHora()));

            if (duplicado) {
                throw new HorarioDuplicadoException(prestador.getId());
            }

            novoHorario.setPrestador(prestador);
            novoHorario.setDisponivel(true);

            horariosExistente.add(novoHorario);
        }

        serviceScheduleRepository.save(prestador);

    }

    public void alterarDisponibilidadeHorario(Long idPrestador, Long idHorario) {

        final Optional<ServiceScheduleModel> optionalPrestador = serviceScheduleRepository.findById(idPrestador);

        if (optionalPrestador.isPresent()) {
            final ServiceScheduleModel prestador = optionalPrestador.get();

            final Optional<Horario> optionalHorario = prestador.getHorarios().stream()
                    .filter(horario -> horario.getId().equals(idHorario))
                    .findFirst();

            if (optionalHorario.isPresent()) {
                final Horario horario = optionalHorario.get();

                if (horario.isDisponivel()) {
                    horario.setDisponivel(false);
                    serviceScheduleRepository.save(prestador);
                } else {
                    throw new HorarioNaoDisponivelException();
                }
            } else {
                throw new HorarioNaoEncontradoException(idPrestador);
            }
        } else {
            throw new PrestadorNaoEncontradoException(idPrestador);
        }
    }

    public void alterarDisponibilidadeHorarioParaTrue(Long idPrestador, Long idHorario) {

        final Optional<ServiceScheduleModel> optionalPrestador = serviceScheduleRepository.findById(idPrestador);

        if (optionalPrestador.isPresent()) {
            final ServiceScheduleModel prestador = optionalPrestador.get();

            final Optional<Horario> optionalHorario = prestador.getHorarios().stream()
                    .filter(horario -> horario.getId().equals(idHorario))
                    .findFirst();

            if (optionalHorario.isPresent()) {
                final Horario horario = optionalHorario.get();

                if (horario.isDisponivel() == false) {
                    horario.setDisponivel(true);
                    serviceScheduleRepository.save(prestador);
                } else {
                    throw new HorarioDisponivelException();
                }
            } else {
                throw new HorarioNaoEncontradoException(idPrestador);
            }
        } else {
            throw new PrestadorNaoEncontradoException(idPrestador);
        }
    }
}