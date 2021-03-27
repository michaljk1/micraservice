package com.michaljk.micra.services;

import com.michaljk.micra.exceptions.AppExceptions;
import com.michaljk.micra.exceptions.ApplicationException;
import com.michaljk.micra.models.trip.TripUser;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.UserRepository;
import com.michaljk.micra.services.dto.trip.TripUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByName(String name) throws ApplicationException {
        return userRepository.findByName(name).orElseThrow(() -> new ApplicationException(AppExceptions.USER_NOT_FOUND));
    }

    public List<TripUser> mapToTripUsers(List<TripUserRequest> requestUsers, String parkingUser) throws ApplicationException {
        List<TripUser> tripUsers = new ArrayList<>();
        for (TripUserRequest tripUserRequest : requestUsers){
            tripUsers.add(getMappedTripUser(tripUserRequest, parkingUser));
        }
        return tripUsers;
    }

    private TripUser getMappedTripUser(TripUserRequest tripUserRequest, String parkingUser) throws ApplicationException {
        String name = tripUserRequest.getName();
        User user = getUserByName(name);
        return TripUser.builder()
                .user(user)
                .userId(user.getId())
                .kilometers(tripUserRequest.getKilometers())
                .parkingUser(name.equals(parkingUser))
                .build();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}


