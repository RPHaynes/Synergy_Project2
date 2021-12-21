package com.revature.shms.controllers;

import com.revature.shms.enums.Amenities;
import com.revature.shms.services.AmenityWrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/amenities")
public class AmenityWrapperController {

	@Autowired
	private AmenityWrapperService service;

	@GetMapping("/generate")
	public ResponseEntity<?> generate(){
		service.GenerateAllAmenityWrappers();
		return ResponseEntity.ok(service.findAllAmenities(Pageable.unpaged()));
	}
	@PutMapping("/{ID}")
	public ResponseEntity<?> update(@PathVariable int ID,@RequestParam("price") float price){
		return ResponseEntity.ok(service.setAmenityPrice(Amenities.values()[ID],price));
	}
	@GetMapping("/{ID}")
	public ResponseEntity<?> get(@PathVariable int ID){
		return ResponseEntity.ok(service.getAmenityWrapper(ID));
	}
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy){
		return ResponseEntity.ok(service.findAllAmenities(PageRequest.of(pageNumber,  pageSize, Sort.by(sortBy).descending())));
	}



}
