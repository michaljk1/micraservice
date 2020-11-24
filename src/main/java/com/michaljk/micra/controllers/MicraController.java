package com.michaljk.micra.controllers;

import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.services.CarService;
import com.michaljk.micra.services.UserService;
import com.michaljk.micra.services.api.car.NewCarRequest;
import com.michaljk.micra.services.api.settlement.SettlementRequest;
import com.michaljk.micra.services.api.settlement.SettlementResponse;
import com.michaljk.micra.services.api.settlement.SettlementUser;
import com.michaljk.micra.services.api.trip.TripRequest;
import com.michaljk.micra.services.api.trip.TripUserRequest;
import com.michaljk.micra.services.SettlementService;
import com.michaljk.micra.services.TripService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableSwagger2
@RequestMapping(value = "micra", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class MicraController {

    private final TripService tripService;
    private final SettlementService settlementService;
    private final CarService carService;
    private final UserService userService;

    @PutMapping("settlement")
    public SettlementResponse getSettlement(@RequestBody SettlementRequest settlementRequest){
        return settlementService.getSettlement(settlementRequest.getMonth(), settlementRequest.getYear());
    }

    @PostMapping("trip")
    public ResponseEntity<String> addTrip(@RequestBody TripRequest tripRequest){
        List<TripUser> tripUsers = tripRequest.getTripUsers().stream().map(TripUserRequest::toTripUser).collect(Collectors.toList());
        tripService.addTrip(tripUsers, tripRequest.isUpdateBalance());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("car")
    public ResponseEntity<String> addCar(@RequestBody NewCarRequest carRequest) throws Exception {
        carService.addCar(carRequest);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("user")
    public ResponseEntity<String> addUser(@RequestParam String name) {
        userService.addUser(name);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
