package com.serviceschedule.service;

import com.serviceschedule.model.Role;
import com.serviceschedule.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public Role cadastrarRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> listarTodasRoles() {
        return roleRepository.findAll();
    }
}
