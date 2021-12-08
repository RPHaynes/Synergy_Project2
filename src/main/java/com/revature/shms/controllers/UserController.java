package com.revature.shms.controllers;
import com.revature.shms.models.User;
import com.revature.shms.repositories.UserRepository;
import com.revature.shms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable String userId) {
        return ResponseEntity.ok( userRepository.findByUserID(Integer.parseInt(userId)));
    }

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody User user) {
		return ResponseEntity.ok( userService.createNewUser(user));
    }

    /**
     * Logs out the user and redirect them to the logout page.
     * @return String that redirects the user to the logout page.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        return "redirect:logoutPage";
    }
}
