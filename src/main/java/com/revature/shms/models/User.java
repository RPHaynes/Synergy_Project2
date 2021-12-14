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
public class User extends secUserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int userID;

	String firstName;
	String lastName;
	String email;

	@Column(nullable = false, unique = true)
	String username;
	@Column(nullable = false)
	String password;

	@Override
	public Integer getID() {
		return userID;
	}

	@OneToMany(mappedBy = "userReserve", cascade = CascadeType.MERGE)
	@JsonManagedReference
	List<Reservation> reservations;
}
