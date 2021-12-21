package com.revature.shms.controllers;

import com.revature.shms.auth.AuthenticationRequest;
import com.revature.shms.auth.AuthenticationResponse;
import com.revature.shms.models.secUserDetails;
import com.revature.shms.services.MyUserDetailsService;
import com.revature.shms.services.UserService;
import com.revature.shms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
		final secUserDetails userDetails = userDetailsService.loadUserByUsername(authRequest);
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
