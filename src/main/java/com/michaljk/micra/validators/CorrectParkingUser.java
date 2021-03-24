package com.michaljk.micra.validators;

import com.auth0.jwt.interfaces.Payload;
import com.michaljk.micra.services.dto.trip.TripRequest;
import com.michaljk.micra.services.dto.trip.TripUserRequest;
import com.michaljk.micra.utils.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ParkingUserValidator.class)
@Documented
public @interface CorrectParkingUser {

    String message() default "ParkingUser";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

class ParkingUserValidator implements ConstraintValidator<CorrectParkingUser, TripRequest> {

    @Override
    public boolean isValid(TripRequest tripRequest, ConstraintValidatorContext context) {
        String parkingUserName = tripRequest.getParkingUser();
        if (StringUtils.isEmpty(parkingUserName)) {
           return true;
        } else {
            return tripRequest.getTripUsers().stream().map(TripUserRequest::getName).anyMatch(name -> name.equals(parkingUserName));
        }
    }
}