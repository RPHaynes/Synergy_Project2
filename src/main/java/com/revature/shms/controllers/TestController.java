package com.revature.shms.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/u")
    public ResponseEntity<?> testURLUser(){
        return ResponseEntity.ok("You are a user");
    }

    @RequestMapping("/m")
    public ResponseEntity<?> testURLManager(){
        return ResponseEntity.ok("You are a manager");
    }

    @RequestMapping("/e")
    public ResponseEntity<?> testURLEmployee(){
        return ResponseEntity.ok("You are an employee");
    }

}
