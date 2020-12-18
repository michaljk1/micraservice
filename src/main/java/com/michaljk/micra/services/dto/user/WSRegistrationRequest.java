package com.michaljk.micra.services.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WSRegistrationRequest {

    private String name;
    private String email;
    private String password;

}
