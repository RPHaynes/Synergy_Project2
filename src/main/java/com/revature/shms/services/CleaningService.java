package com.revature.shms.services;

import com.revature.shms.enums.EmployeeType;
import com.revature.shms.models.Cleaning;
import com.revature.shms.models.Employee;
import com.revature.shms.models.Room;
import com.revature.shms.repositories.CleaningRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CleaningService {
	@Autowired
	private CleaningRepository cleaningRepository;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private RoomService roomService;

	/**
	 * Gets All Cleanings assigned to a specific employee.
	 * @param employeeID the employee to match.
	 * @return all Cleanings sorted that are assigned to employee.
	 */
	public Page<Cleaning> employeeCleaningToDo(int employeeID,Pageable pageable) throws NotFound {
		return findAllCleaningsByEmployee(employeeService.findEmployeeByID(employeeID),pageable);
	}

	/**
	 * Ryan wanted to still work on this
	 * @param employee ?
	 * @param employeeTarget ?
	 * @param room the room being worked on.
	 * @param priority how quickly should the room be cleaned.
	 * @return Room scheduled to be cleaned.
	 */
	public Room scheduleCleaningRoom(Employee employee, Employee employeeTarget, Room room, int priority) throws NotFound {
		if (employeeTarget.getEmployeeType().equals(EmployeeType.RECEPTIONIST)) return null;
		createCleaning(new Cleaning(0,room,employeeTarget, Instant.now().toEpochMilli(),priority));
		return roomService.scheduleCleaning(room.getRoomNumber());
	}

	/**
	 * If you aren't a Receptionist, then you delete the previous Cleaning ID for that room number and start another
	 * with the Cleaning status of In Progress.
	 * @param employeeID the employee doing the cleaning with the given ID.
	 * @param roomNumber the room to be worked on by room number.
	 * @return Room started being cleaned.
	 */
	public Room startCleanRoom(int employeeID, int roomNumber) throws NotFound {
		Employee employee = employeeService.findEmployeeByID(employeeID);
		if (employee.getEmployeeType().equals(EmployeeType.RECEPTIONIST)) return null;
		return roomService.startCleaning(roomNumber);
	}

	/**
	 * If you aren't a Receptionist, then you set the Cleaning status of the given room to Clean.
	 * @param employeeID the employee doing the cleaning with the given ID.
	 * @param roomNumber the room to be worked on by room number.
	 * @return Room now finished being cleaned.
	 */
	public Room finishCleaningRoom(int employeeID, int roomNumber) throws NotFound {
		Employee employee = employeeService.findEmployeeByID(employeeID);
		Room room = roomService.findByRoomNumber(roomNumber);
		if (employee.getEmployeeType().equals(EmployeeType.RECEPTIONIST)) return null;
		removeCleaning(findByRoom(room));
		return roomService.finishCleaning(roomNumber);
	}

	/**
	 * Gets All Cleanings by Priority then DateAdded.
	 * @return List<Cleaning> Sorted by Priority and DateAdded.
	 */
	public Page<Cleaning> findAllCleanings(Pageable pageable){
		return cleaningRepository.findAllByOrderByPriorityDescDateAddedAsc(pageable);
	}

	/**
	 * Gets All Cleanings assigned to a specific employee.
	 * @param employee the employee to match.
	 * @return all Cleanings sorted that are assigned to employee.
	 */
	public Page<Cleaning> findAllCleaningsByEmployee(Employee employee, Pageable pageable){
		return cleaningRepository.findAllByEmployeeOrderByPriorityDescDateAddedAsc(employee, pageable);
	}

	/**
	 * Gets Cleaning assigned to a specific room.
	 * @param room the room to match.
	 * @return Cleaning corresponding to the room.
	 */
	public Cleaning findByRoom(Room room) throws NotFound {
		return cleaningRepository.findByRoom(room).orElseThrow(NotFound::new);
	}

	/**
	 * Saves the cleaning information to the database and returns the original object
	 * @param cleaning the cleaning information to match.
	 * @return the cleaning information with it saved to the database.
	 */
	public Cleaning createCleaning(Cleaning cleaning){
		return cleaningRepository.save(cleaning);
	}

	/**
	 * Deletes the cleaning information from the database
	 * @param cleaning the cleaning information to match
	 */
	public void removeCleaning(Cleaning cleaning){
		cleaningRepository.delete(cleaning);
	}
}
