package com.revature.shms.controllers;

import com.revature.shms.models.Cleaning;
import com.revature.shms.services.CleaningService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/cleaning")
public class CleaningController {

	@Autowired
	private CleaningService cleaningService;

	@GetMapping("/")
	public Page<Cleaning> getCleaning(@RequestParam("id") int employeeID, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy) throws NotFound {
		return cleaningService.findAllCleaningsByEmployee(employeeID, PageRequest.of(pageNumber,  pageSize, Sort.by(sortBy).descending()));
  }
	@PostMapping("/")
	public ResponseEntity<?> saveCleaning(@RequestParam("ID") int employeeID, @RequestParam("roomNumber") int roomID, @RequestParam("priority") int priority) throws NotFound {
		return ResponseEntity.ok(cleaningService.scheduleCleaningRoom(employeeID,roomID,priority));
	}
	@PostMapping("/update")
	public ResponseEntity<?> saveCleaning(@RequestBody Cleaning cleaning) throws NotFound {
		return ResponseEntity.ok(cleaningService.createCleaning(cleaning));
	}
}
