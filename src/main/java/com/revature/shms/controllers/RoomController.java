package com.revature.shms.controllers;

import com.revature.shms.models.Room;
import com.revature.shms.services.RoomService;
import com.revature.shms.services.UserService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {
	@Autowired
	RoomService roomService;
	@Autowired
	UserService userService;
	@GetMapping()
	public ResponseEntity<?> getAllRooms(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy,@RequestParam(value = "filter",defaultValue = "") String filter){
		switch (filter){
			case "Occupied":
				return ResponseEntity.ok(roomService.findAllByIsOccupied(true,PageRequest.of(pageNumber,  pageSize, Sort.by(sortBy).descending())));
			case "NotOccupied":
				return ResponseEntity.ok(roomService.findAllByIsOccupied(false,PageRequest.of(pageNumber,  pageSize, Sort.by(sortBy).descending())));
			default:
				return ResponseEntity.ok(roomService.findAllRooms(PageRequest.of(pageNumber,  pageSize, Sort.by(sortBy).descending())));
		}
	}
	@PostMapping
	public ResponseEntity<?> saveRoom(@RequestBody Room room){
		return ResponseEntity.ok(roomService.addRoom(room));
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getRoom(@PathVariable int id) throws NotFound {
		return  ResponseEntity.ok(roomService.findByRoomNumber(id));
	}
	@PutMapping("/{id}/changeOccupation")
	public ResponseEntity<?> editRoom(@PathVariable int id,@RequestParam("value") boolean value) throws NotFound {
		return  ResponseEntity.ok(roomService.setOccupationStatus(id,value));
	}
	@PutMapping("/{id}/addUser")
	public ResponseEntity<?> editRoom(@PathVariable int id,@RequestParam("userID") int uid) throws NotFound {
		Room room = roomService.findByRoomNumber(id);
		room.setCurrentUser(userService.findUserByUserID(uid));
		return  ResponseEntity.ok(roomService.addRoom(room));
	}







}
