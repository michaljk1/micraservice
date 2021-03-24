package com.michaljk.micra.services;

import com.michaljk.micra.exceptions.AppExceptions;
import com.michaljk.micra.exceptions.ApplicationException;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.models.User;
import com.michaljk.micra.repositories.UserRepository;
import com.michaljk.micra.services.dto.trip.TripUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByName(String name) throws ApplicationException {
        return userRepository.findByName(name).orElseThrow(() -> new ApplicationException(AppExceptions.USER_NOT_FOUND));
    }

    public List<TripUser> mapToTripUsers(List<TripUserRequest> requestUsers) throws ApplicationException {
        List<TripUser> tripUsers = new ArrayList<>();
        for (TripUserRequest tripUserRequest : requestUsers){
            tripUsers.add(getMappedTripUser(tripUserRequest));
        }
        return tripUsers;
    }

    private TripUser getMappedTripUser(TripUserRequest tripUserRequest) throws ApplicationException {
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


