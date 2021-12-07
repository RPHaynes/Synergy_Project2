package com.revature.shms.models;

import com.revature.shms.enums.Amenities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AmenityWrapper {
	@Id
	@Enumerated
	@Column(unique = true,nullable = false)
	Amenities amenity;
	@Column(nullable = false)
	double priceWeight;
}
