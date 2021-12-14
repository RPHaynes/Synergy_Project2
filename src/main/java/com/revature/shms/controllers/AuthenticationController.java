package com.revature.shms.controllers;
import com.revature.shms.auth.AuthenticationRequest;
import com.revature.shms.auth.AuthenticationResponse;
import com.revature.shms.models.*;
import com.revature.shms.services.MyUserDetailsService;
import com.revature.shms.services.UserService;
import com.revature.shms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

	@CrossOrigin
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authRequest) throws Exception{
		System.out.println("Created " + 1);
        try{
			System.out.println("Created " + 2);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername()+","+authRequest.getRole(), authRequest.getPassword()));
			System.out.println(2);
        }catch(BadCredentialsException e){
			System.out.println("exception");
			e.printStackTrace();
            throw new Exception("Incorrect username/password", e);
        }
		System.out.println(4);
        final secUserDetails userDetails  = userDetailsService.loadUserByUsername(authRequest);
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
