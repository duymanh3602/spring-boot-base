package com.manhdd.serverbase.entities;

import com.manhdd.serverbase.controllers.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"roleName", "role"})
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private RoleEnum role;
    private String roleName;
    private String description;

    public Role(String roleName, RoleEnum role, String description) {
        this.roleName = roleName;
        this.role = role;
        this.description = description;
    }
}
