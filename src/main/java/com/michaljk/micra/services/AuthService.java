package com.michaljk.micra.services;

import com.michaljk.micra.models.User;
import com.michaljk.micra.services.dto.auth.WSAuthenticationRequest;
import com.michaljk.micra.services.dto.auth.WSAuthenticationResponse;
import com.michaljk.micra.services.dto.user.WSRegistrationRequest;
import com.michaljk.micra.services.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public WSAuthenticationResponse authenticateUser(WSAuthenticationRequest authRequest) throws Exception {
        final User user;
        try {
            user = userService.getUserByName(authRequest.getUsername());
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getName(), authRequest.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials");
        } catch (Exception exc) {
            throw new EntityNotFoundException("Entity not found");
        }
        final String jwtToken = "Bearer " + JwtUtil.generateToken(user);
        return new WSAuthenticationResponse(jwtToken);
    }

    public void registerUser(WSRegistrationRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
//        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        userService.saveUser(user);
    }

}


