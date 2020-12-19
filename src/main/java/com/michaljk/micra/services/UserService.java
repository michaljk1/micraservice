package com.michaljk.micra.services;

import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.UserRepository;
import com.michaljk.micra.services.dto.trip.TripUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow();
    }

    public List<TripUser> mapToTripUsers(List<TripUserRequest> requestUsers) {
        return requestUsers.stream().map(this::getMappedTripUser).collect(Collectors.toList());
    }

    private TripUser getMappedTripUser(TripUserRequest tripUserRequest) {
        User user = getUserByName(tripUserRequest.getName());
        TripUser tripUser = new TripUser();
        tripUser.setUser(user);
        tripUser.setUserId(user.getId());
        tripUser.setKilometers(tripUserRequest.getKilometers());
        return tripUser;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}


