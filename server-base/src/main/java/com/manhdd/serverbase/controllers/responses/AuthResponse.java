package com.manhdd.serverbase.controllers.responses;

import com.manhdd.serverbase.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String username;
    private String name;
    private String accessToken;
    private String role;
}
