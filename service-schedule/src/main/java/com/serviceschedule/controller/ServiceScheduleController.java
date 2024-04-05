package com.serviceschedule.controller;


import com.serviceschedule.exception.PrestadorNaoEncontradoException;
import com.serviceschedule.model.ServiceScheduleModel;
import com.serviceschedule.service.ServiceScheduleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prestadores")
public class ServiceScheduleController {
    private final ServiceScheduleService serviceScheduleService;

    @Autowired
    public ServiceScheduleController(ServiceScheduleService serviceScheduleService) {
        this.serviceScheduleService = serviceScheduleService;
    }

    @GetMapping
    public List<ServiceScheduleModel> getAllPrestadores() {
        return serviceScheduleService.getAllPrestadores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceScheduleModel> getPrestadorById(@PathVariable Long id) {
        Optional<ServiceScheduleModel> prestadorServico = serviceScheduleService.getPrestadorById(id);
        return prestadorServico.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ServiceScheduleModel> savePrestador(@RequestBody ServiceScheduleModel prestadorServico) {
        ServiceScheduleModel savedPrestador = serviceScheduleService.savePrestador(prestadorServico);
        return new ResponseEntity<>(savedPrestador, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestador(@PathVariable Long id) {
        try {
            serviceScheduleService.deletePrestador(id);
            return ResponseEntity.ok().build();
        } catch (PrestadorNaoEncontradoException ex) {
            return ResponseEntity.notFound().build();
        }

    }

}
