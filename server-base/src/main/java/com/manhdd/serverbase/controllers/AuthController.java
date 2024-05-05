package com.manhdd.serverbase.controllers;

import com.manhdd.serverbase.controllers.enums.RoleEnum;
import com.manhdd.serverbase.controllers.requests.LoginRequest;
import com.manhdd.serverbase.controllers.requests.SignUpRequest;
import com.manhdd.serverbase.controllers.responses.AuthResponse;
import com.manhdd.serverbase.entities.Role;
import com.manhdd.serverbase.entities.User;
import com.manhdd.serverbase.helpers.JwtHelper;
import com.manhdd.serverbase.repositories.RoleRepository;
import com.manhdd.serverbase.repositories.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/log-in")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest request) {
        try {
            User user = userRepository.findByUsername(request.username);
            if (user == null) {
                throw new BadCredentialsException("Invalid username or password");
            }
//            String userSalt = user.getSalt();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username, request.password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = JwtHelper.generateToken(user);
            AuthResponse response = new AuthResponse(user.getUsername(), user.getUsername() , accessToken, user.getRoleClaims());
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        try {
            if (userRepository.existsByUsername(request.username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }
//            String secureSalt = generateSalt();
            User user = new User();
            user.setUsername(request.username);
            user.setPassword(passwordEncoder.encode(request.password));
//            user.setSalt(secureSalt);
            Role role = roleRepository.findByRole(RoleEnum.USER);
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);

            return ResponseEntity.ok("SUCCESSFULLY");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

//    @GetMapping("/log-out")
//    public ResponseEntity<?> logOut(Authentication authentication) {
//        try {
//            return ResponseEntity.ok(authentication);
////            SecurityContextHolder.clearContext();
////            return ResponseEntity.ok("SUCCESSFULLY");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        int saltSize = 16;
        byte[] salt = new byte[saltSize];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
