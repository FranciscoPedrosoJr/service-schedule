package com.serviceschedule.repository;

import com.serviceschedule.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findBynomeRole(String nome);
}
