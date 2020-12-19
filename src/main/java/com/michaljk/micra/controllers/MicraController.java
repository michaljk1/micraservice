package com.michaljk.micra.controllers;

import com.michaljk.micra.models.Period;
import com.michaljk.micra.models.TripUser;
import com.michaljk.micra.services.BalanceService;
import com.michaljk.micra.services.CarService;
import com.michaljk.micra.services.SettlementService;
import com.michaljk.micra.services.TripService;
import com.michaljk.micra.services.UserService;
import com.michaljk.micra.services.dto.car.WSCarRequest;
import com.michaljk.micra.services.dto.car.WSCarResponse;
import com.michaljk.micra.services.dto.settlement.ws.WSSettlementRequest;
import com.michaljk.micra.services.dto.settlement.ws.WSSettlementResponse;
import com.michaljk.micra.services.dto.trip.TripRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public WSSettlementResponse getSettlement(@RequestBody WSSettlementRequest WSSettlementRequest) throws Exception {
        Period settlementPeriod = balanceService.getPeriod(WSSettlementRequest.getMonth(), WSSettlementRequest.getYear());
        return settlementService.getSettlement(settlementPeriod);
    }

    @PostMapping("trip")
    public ResponseEntity<String> addTrip(@RequestBody TripRequest tripRequest) {
        List<TripUser> tripUsers = userService.mapToTripUsers(tripRequest.getTripUsers());
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


    @PostMapping("fuel")
    public ResponseEntity<String> updateFuelUsage(@RequestBody Float fuelUsage) {
        carService.updateFuelUsage(fuelUsage);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
