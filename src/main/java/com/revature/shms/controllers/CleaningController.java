package com.revature.shms.controllers;

import com.revature.shms.models.Cleaning;
import com.revature.shms.services.CleaningService;
import com.revature.shms.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/employee")
public class CleaningController {
	@Autowired
	private CleaningService service;
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/cleaning/list")
	public Page<Cleaning> getCleaning(@RequestParam("id") int employee,@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy){
		return service.employeeCleaningToDo(employeeService.getEmployeeRepository().getById(employee), PageRequest.of(pageNumber,  pageSize, Sort.by(sortBy).descending()));
	}
}
