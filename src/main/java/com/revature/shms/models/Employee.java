package com.revature.shms.models;

import com.revature.shms.enums.EmployeeType;
import com.revature.shms.enums.Roles;
import com.revature.shms.services.MyUserDetailsService;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee extends secUserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int employeeID;

	String firstName;
	String lastName;

	@Column(nullable = false, unique = true)
	String username;
	@Column(nullable = false)
	String password;

	@Enumerated
	@Column(nullable = false)
	EmployeeType employeeType;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(getRole().getValue()));
	}

	@Override
	public Integer getID() {
		return employeeID;
	}

	@Override
	public Roles getRole() {
		return (employeeType == EmployeeType.MANAGER)? Roles.MANAGER:Roles.EMPLOYEE;
	}
}
