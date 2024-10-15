package com.serviceschedule.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    private Long idRole;

    @Column(name = "nomeRole", nullable = false, length = 50)
    private String nomeRole;

    @Column(name = "criadoEm", nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "alteradoEm")
    private LocalDateTime alteradoEm;
}
