package com.serviceschedule.service;

import com.serviceschedule.exception.GlobalExceptionHandler;
import com.serviceschedule.model.Role;
import com.serviceschedule.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;



    public boolean nomeRoleJaCadastrado(String nome) {
        return roleRepository.findBynomeRole(nome).isPresent();
    }

    public Role cadastrarRole(Role role) {
        final String nomeRole = role.getNomeRole();

        if (nomeRoleJaCadastrado(nomeRole)) {
            try {
                throw new GlobalExceptionHandler();
            } catch (GlobalExceptionHandler e) {
                throw new RuntimeException(e);
            }
        }
        return roleRepository.save(role);
    }

    public List<Role> listarTodasRoles() {
        return roleRepository.findAll();
    }
}
