package com.revature.shms.servicetests;
import com.revature.shms.enums.EmployeeType;
import com.revature.shms.models.Employee;
import com.revature.shms.models.User;
import com.revature.shms.repositories.EmployeeRepository;
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
	@Mock	EmployeeRepository employeeRepository;
	@Mock	UserRepository userRepository;
	@InjectMocks MyUserDetailsService myUserDetailsService;

	@Test
	public void loadUserByUserNameTest(){
		User user = new User();
		user.setUsername("123");
		Employee employee = new Employee();
		employee.setUsername("123");
		employee.setEmployeeType(EmployeeType.RECEPTIONIST);
		when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));
		when( employeeRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(employee));
		assertEquals(user.getUsername(), myUserDetailsService.loadUserByUsername("123,EMPLOYEE").getUsername());
		assertEquals(user.getUsername(), myUserDetailsService.loadUserByUsername("123,USER").getUsername());
		boolean caught = false;
		try {
			myUserDetailsService.loadUserByUsername("12344,USER");
		} catch (UsernameNotFoundException e){
			assertTrue(e.getMessage().contains("Invalid"));
			caught = true;
		}
		assertTrue(caught);
	}
}
