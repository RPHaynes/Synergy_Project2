package com.revature.shms.servicetests;

import com.revature.shms.models.User;
import com.revature.shms.repositories.UserRepository;
import com.revature.shms.services.MyUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsServiceTests {
	@Mock UserRepository userRepository;
	@InjectMocks MyUserDetailsService myUserDetailsService;

	@Test
	public void loadUserByUserNameTest(){
		User user = new User();
		user.setUsername("123");
		when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));
		assertEquals(user.getUsername(), myUserDetailsService.loadUserByUsername("123").getUsername());
		boolean caught = false;
		try {
			myUserDetailsService.loadUserByUsername("12344");
		} catch (UsernameNotFoundException e){
			assertTrue(e.getMessage().contains("Invalid"));
			caught = true;
		}
		assertTrue(caught);
	}
}
