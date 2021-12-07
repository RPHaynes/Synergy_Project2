package com.revature.shms.controllers;

import com.revature.shms.models.Cleaning;
import com.revature.shms.services.AmenityWrapperService;
import com.revature.shms.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController()
@RequestMapping(value = "/amenities")
public class AmenityWrapperController {
	@Autowired
	private AmenityWrapperService service;

	@GetMapping("/generate")
	public ResponseEntity<?> generate(){
		service.GenerateAllAmenityWrappers();
		return ResponseEntity.ok().build();
	}
}
