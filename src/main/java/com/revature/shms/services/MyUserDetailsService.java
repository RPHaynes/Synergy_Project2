package com.revature.shms.services;
import com.revature.shms.auth.AuthenticationRequest;
import com.revature.shms.enums.Roles;

import com.revature.shms.models.secUserDetails;
import com.revature.shms.repositories.EmployeeRepository;
import com.revature.shms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
	@Autowired
	EmployeeRepository employeeRepository;

    public secUserDetails loadUserByUsername(AuthenticationRequest authenticationRequest) throws UsernameNotFoundException {
		secUserDetails user;
		if (authenticationRequest.getRole().equals(Roles.USER))
			user = (secUserDetails) userRepository.findByUsername(authenticationRequest.getUsername()).orElse(null);
		else
			user = (secUserDetails) employeeRepository.findByUsername(authenticationRequest.getUsername()).orElse(null);
        if (user == null) throw new UsernameNotFoundException("Invalid");

        return user;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		return loadUserByUsername(new AuthenticationRequest(username.split(",")[0],null,Roles.valueOf(username.split(",")[1])));
	}
}
