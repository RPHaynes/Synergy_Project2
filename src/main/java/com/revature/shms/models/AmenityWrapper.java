package com.revature.shms.models;

import com.revature.shms.enums.Amenities;
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
public class AmenityWrapper {
	@Id
	int id;
	@Enumerated
	@Column(unique = true)
	Amenities amenity;
	@Column
	double priceWeight;

	public AmenityWrapper(Amenities amenity, double priceWeight) {
		this.amenity = amenity;
		this.priceWeight = priceWeight;
	}
}
