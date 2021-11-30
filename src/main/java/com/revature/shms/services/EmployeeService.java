package com.revature.shms.services;


import com.revature.shms.enums.EmployeeType;
import com.revature.shms.models.Cleaning;
import com.revature.shms.models.Employee;
import com.revature.shms.models.Room;
import com.revature.shms.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private CleaningService cleaningService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private UserService userService;




	public List<Cleaning> employeeCleaningToDo(Employee employee){
		return cleaningService.GetAllCleaningsByEmployee(employee);
	}
	public Room scheduleCleaningRoom(Employee employee, Employee employeeTarget, Room room, int priority){
		if (employeeTarget.getEmployeeType().equals(EmployeeType.RECEPTIONIST)) return null;
		cleaningService.schedule(new Cleaning(0,room,employeeTarget,Instant.now().toEpochMilli(),priority));
		return roomService.scheduleCleaning(room);
	}
	public Room startCleanRoom(Employee employee,Room room){
		if (employee.getEmployeeType().equals(EmployeeType.RECEPTIONIST)) return null;
		 cleaningService.remove(cleaningService.getByRoom(room));
		 return roomService.startCleaning(room);
	}
	public Room finishCleaningRoom(Employee employee, Room room){
		if (employee.getEmployeeType().equals(EmployeeType.RECEPTIONIST)) return null;
		return roomService.finishCleaning(room);
	}


}