package com.revature.shms.models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int userID;

	String firstName;
	String lastName;

	@Column(nullable = false, unique = true)
	String username;
	@Column(nullable = false)
	String password;


  	@OneToMany(mappedBy = "userReserve")
	@JsonManagedReference
	private List<Reservation> reservations;
}
