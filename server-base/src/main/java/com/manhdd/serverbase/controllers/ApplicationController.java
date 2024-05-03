package com.manhdd.serverbase.controllers;

import com.manhdd.serverbase.controllers.enums.RoleEnum;
import com.manhdd.serverbase.controllers.responses.AuthResponse;
import com.manhdd.serverbase.entities.Role;
import com.manhdd.serverbase.repositories.RoleRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app")
public class ApplicationController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/rake-role")
    @RolesAllowed("SUPER_ADMIN")
    public ResponseEntity<?> rakeRole() {
        for (RoleEnum eRole : RoleEnum.values()) {
            String roleName = eRole.name();
            Role role = new Role(roleName, "");
            roleRepository.save(role);
        }
        return ResponseEntity.ok("OK");
    }

}
