package com.serviceschedule.controller;

import com.serviceschedule.model.Role;
import com.serviceschedule.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> cadastrarRole(@RequestBody Role role) {
        final Role novaRole = roleService.cadastrarRole(role);
        return new ResponseEntity<>(novaRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Role>> listarTodasRoles() {
        final List<Role> roles = roleService.listarTodasRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
