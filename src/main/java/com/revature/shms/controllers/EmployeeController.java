package com.revature.shms.controllers;
import com.revature.shms.models.Employee;
import com.revature.shms.repositories.EmployeeRepository;
import com.revature.shms.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<?> createNewEmployee(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @RequestMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String employeeId){
        return ResponseEntity.ok( employeeRepository.findByEmployeeID(Integer.parseInt(employeeId)));
    }

}
