package com.serviceschedule.service;

import com.serviceschedule.model.Role;
import com.serviceschedule.model.Usuario;
import com.serviceschedule.repository.RoleRepository;
import com.serviceschedule.repository.UsuarioRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(String nome, String email, String password, String roleName) {
        final Role role = roleRepository.findBynomeRole(roleName)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        final Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRole(role);
        usuario.setCriadoEm(LocalDateTime.now());

        return usuarioRepository.save(usuario);

    }
}
