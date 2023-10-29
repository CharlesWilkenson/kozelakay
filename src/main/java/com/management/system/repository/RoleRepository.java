package com.management.system.repository;

import com.management.system.entities.Authority;
import com.management.system.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(Authority name);
}
