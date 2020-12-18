package com.michaljk.micra.services.jwt;

import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByName(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("USER NOT FOUND");
    }
}
