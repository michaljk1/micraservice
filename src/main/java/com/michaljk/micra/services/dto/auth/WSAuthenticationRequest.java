package com.michaljk.micra.services.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WSAuthenticationRequest {

    private String username;
    private String password;

}
