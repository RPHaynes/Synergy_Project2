package com.revature.shms.models;

import com.revature.shms.enums.EmployeeType;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
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

	/*  Probably can just work with employeeType
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "EMPLOYEE_ROLES",
			joinColumns = {
				@JoinColumn(name = "EMPLOYEE_ID")
			}
	)
	private Set<Role> roles;*/


}
