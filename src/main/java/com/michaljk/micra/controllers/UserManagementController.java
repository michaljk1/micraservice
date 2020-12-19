package com.michaljk.micra.controllers;

import com.michaljk.micra.services.AuthService;
import com.michaljk.micra.services.dto.auth.WSAuthenticationRequest;
import com.michaljk.micra.services.dto.auth.WSAuthenticationResponse;
import com.michaljk.micra.services.dto.user.WSRegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping(value = "micra", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserManagementController {

    private final AuthService authService;

    @PostMapping("signUp")
    public ResponseEntity<String> addUser(@RequestBody WSRegistrationRequest registrationRequest) {
        authService.registerUser(registrationRequest);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("signIn")
    public ResponseEntity<WSAuthenticationResponse> authenticateUser(@RequestBody WSAuthenticationRequest authRequest) throws Exception {
        return ResponseEntity.ok(authService.authenticateUser(authRequest));
    }

}
