package com.revature.shms.models;

import com.revature.shms.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
	@Id
	@Column(name = "roomNumber")
	@GeneratedValue(strategy = GenerationType.AUTO)
	int roomNumber;

	int currentOccupants;

	@OneToOne(cascade = CascadeType.ALL)
	User CurrentUser;

	@Column(nullable = false)
	CleaningStatus status;
	@Column(nullable = false)
	boolean isOccupied;
	@Column(nullable = false)
	WorkStatus workStatus;

	@OneToMany(cascade = CascadeType.ALL)
	List<AmenityWrapper> amenitiesList;

	/*
	boolean isAvailable(){
		return !(!isClean || isOccupied || needsService);
		//		 !true = false|	true		|	true		|returns !(true) = false
		//		 !false =true |	true		|	true		|returns !(true) = false
		//		 !true = false|	false		|	true		|returns !(true) = false
		//		 !true = false|	true		|	false		|returns !(true) = false
		//		 !true = false|	false		|	false		|returns !(false) = true
	}
	 */
}
