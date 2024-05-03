package com.manhdd.serverbase.repositories;

import com.manhdd.serverbase.controllers.enums.RoleEnum;
import com.manhdd.serverbase.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(RoleEnum role);
}
