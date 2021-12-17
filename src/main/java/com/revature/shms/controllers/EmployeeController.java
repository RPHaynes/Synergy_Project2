package com.revature.shms.controllers;
import com.revature.shms.models.Employee;
import com.revature.shms.models.Reservation;
import com.revature.shms.repositories.EmployeeRepository;
import com.revature.shms.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public Page<Employee> returnAllEmployee(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy){
        return employeeRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
    }

    @PostMapping
    public ResponseEntity<?> createNewEmployee(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @GetMapping("/{employeeID}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int employeeID){
        return ResponseEntity.ok( employeeRepository.findByEmployeeID(employeeID));
    }

    @PutMapping("/{employeeID}")
    public ResponseEntity<?> updatePassword(@PathVariable int employeeID,
                                            @RequestBody HashMap<String, String> password)
    {
        return ResponseEntity.ok(employeeService.updatePassword(employeeID, password.get("old"),
                password.get("new")));
    }

    @PutMapping("/firstName/{employeeID}")
    public ResponseEntity<?> updateFirstName(@PathVariable int employeeID,
                                             @RequestBody String firstName){
        return ResponseEntity.ok(employeeService.updateFirstName(employeeID, firstName.substring(1,firstName.length()-1)));
    }

    @PutMapping("/lastName/{employeeID}")
    public ResponseEntity<?> updateLastName(@PathVariable int employeeID,
                                            @RequestBody String lastName){
        return ResponseEntity.ok(employeeService.updateLastName(employeeID, lastName.substring(1,lastName.length()-1)));
    }
}
