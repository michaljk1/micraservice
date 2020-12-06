package com.michaljk.micra.controllers;

import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.services.BalanceService;
import com.michaljk.micra.services.CarService;
import com.michaljk.micra.services.UserService;
import com.michaljk.micra.services.api.car.WSCarRequest;
import com.michaljk.micra.services.api.car.WSCarResponse;
import com.michaljk.micra.services.api.settlement.SettlementRequest;
import com.michaljk.micra.services.api.settlement.SettlementResponse;
import com.michaljk.micra.services.api.trip.TripRequest;
import com.michaljk.micra.services.SettlementService;
import com.michaljk.micra.services.TripService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping(value = "micra", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class MicraController {

    private final TripService tripService;
    private final SettlementService settlementService;
    private final CarService carService;
    private final UserService userService;
    private final BalanceService balanceService;

    @PutMapping("settlement")
    public SettlementResponse getSettlement(@RequestBody SettlementRequest settlementRequest) {
        Period settlementPeriod = balanceService.getPeriod(settlementRequest.getMonth(), settlementRequest.getYear());
        return settlementService.getSettlement(settlementPeriod);
    }

    @PostMapping("trip")
    public ResponseEntity<String> addTrip(@RequestBody TripRequest tripRequest) {
        List<TripUser> tripUsers = userService.mapToTripUsersByName(tripRequest.getTripUsers());
        tripService.addTrip(tripUsers, tripRequest.isUpdateBalance());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("car")
    public ResponseEntity<String> addCar(@RequestBody WSCarRequest carRequest) throws Exception {
        carService.addCar(carRequest);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("getCar")
    public WSCarResponse getCar() {
        return new WSCarResponse(carService.getCar());
    }

    @PostMapping("user")
    public ResponseEntity<String> addUser(@RequestParam String name) {
        userService.addUser(name);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("fuel")
    public ResponseEntity<String> updateFuelUsage(@RequestBody Float fuelUsage) {
        carService.updateFuelUsage(fuelUsage);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


}