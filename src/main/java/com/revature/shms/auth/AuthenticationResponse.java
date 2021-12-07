package com.revature.shms.auth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    //needs Json Web Token
    private final String jwt;

}
