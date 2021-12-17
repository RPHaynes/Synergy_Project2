package com.revature.shms.controllers;

import com.revature.shms.models.User;
import com.revature.shms.repositories.UserRepository;
import com.revature.shms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/{userID}")
    public ResponseEntity<?> findUserById(@PathVariable int userID) {
        return ResponseEntity.ok(userRepository.findByUserID(userID));
    }

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createNewUser(user));
    }

    @PutMapping("/{userID}")
    public ResponseEntity<?> updatePassword(@PathVariable int userID,
                                            @RequestBody HashMap<String, String> password) {
        return ResponseEntity.ok(userService.updatePassword(userID, password.get("old"),
                password.get("new")));
    }

    @PutMapping("/firstName/{userID}")
    public ResponseEntity<?> updateFirstName(@PathVariable int userID,
                                        @RequestBody String firstName){
        return ResponseEntity.ok(userService.updateFirstName(userID, firstName.substring(1,firstName.length()-1)));
    }

    @PutMapping("/lastName/{userID}")
    public ResponseEntity<?> updateLastName(@PathVariable int userID,
                                        @RequestBody String lastName){
        return ResponseEntity.ok(userService.updateLastName(userID, lastName.substring(1,lastName.length()-1)));
    }

    @PutMapping("/email/{userID}")
    public ResponseEntity<?> updateEmail(@PathVariable int userID,
                                            @RequestBody String email){
        return ResponseEntity.ok(userService.updateEmail(userID, email.substring(1,email.length()-1)));
    }
}
