package com.michaljk.micra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class MicraApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicraApplication.class, args);
    }

}
