package com.revature.shms.services;

import com.revature.shms.enums.Amenities;
import com.revature.shms.models.AmenityWrapper;
import com.revature.shms.repositories.AmenityWrapperRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AmenityWrapperService {

	@Autowired
	private AmenityWrapperRepository amenityWrapperRepository;

	/**
	 * Sets an amenities prices to the given amount.
	 * @param amenity amenity to change price.
	 * @param price  price to be set.
	 * @return the amenityWrapper with the new price for the given amenity.
	 */
	public AmenityWrapper setAmenityPrice(Amenities amenity, double price){
		return amenityWrapperRepository.save(new AmenityWrapper(amenity, price));
	}

	/**
	 * Generates all AmenityWrappers with 0 price
	 */
	public void GenerateAllAmenityWrappers(){
		Arrays.stream(Amenities.values()).forEach(amenities -> amenityWrapperRepository.save(new AmenityWrapper(amenities,0)));
	}

	// -- Gets
	/**
	 * Gets AmenityWrapper based on amenities enum
	 * @param amenity amenity to look for.
	 * @return AmenityWrapper the amenity wrapper for the given amenity.
	 */
	public AmenityWrapper getAmenityWrapper(Amenities amenity){
		return amenityWrapperRepository.getById(amenity);
	}

	/**
	 * Gets price of the given amenity from database.
	 * @param amenity amenity to look for.
	 * @return double price of the given ammenity.
	 */
	public Double getAmenityPrice(Amenities amenity) {
		return amenityWrapperRepository.getById(amenity).getPriceWeight();
	}

	/**
	 * Gets total price from list of amenity wrappers.
	 * @param wrappers List<AmenityWrapper> list of AmenityWrappers to get prices of.
	 * @return double total price for the lis of amenities.
	 */
	public Double getTotal(List<AmenityWrapper> wrappers){
		return wrappers.stream().mapToDouble(AmenityWrapper::getPriceWeight).sum();
	}

	// -- Finds
	/**
	 * Gets all amenityWrappers in a Page.
	 * @param pageable the page information.
	 * @return Page<AmenityWrapper> of All amenity wrappers.
	 */
	public Page<AmenityWrapper> findAllAmenities(Pageable pageable){
		return amenityWrapperRepository.findAll(pageable);
	}

}
