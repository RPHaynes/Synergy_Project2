package com.revature.shms.servicetests;

import com.revature.shms.enums.CleaningStatus;
import com.revature.shms.enums.EmployeeType;
import com.revature.shms.models.Cleaning;
import com.revature.shms.models.Employee;
import com.revature.shms.models.Room;
import com.revature.shms.repositories.CleaningRepository;
import com.revature.shms.services.CleaningService;
import com.revature.shms.services.EmployeeService;
import com.revature.shms.services.RoomService;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CleaningServiceTest {
	@Mock RoomService roomService;
	@Mock EmployeeService employeeService;
	@Mock CleaningRepository cleaningRepository;
	@InjectMocks CleaningService cleaningService;

	// -- Create/Delete
	@Test
	public void createCleaningTest(){
		Cleaning cleaning = new Cleaning();
		when(cleaningRepository.save(any())).thenReturn(cleaning);
		assertEquals(cleaning, cleaningService.createCleaning(cleaning));
	}

	@Test
	public void removeCleaningTest(){
		Cleaning cleaning = new Cleaning();
		cleaningService.removeCleaning(cleaning);
		verify(cleaningRepository, times(1)).delete(any());
	}

	@Test
	public void scheduleCleaningRoomTest() throws NotFound {
		Employee employee = new Employee();
		Room room = new Room();
		employee.setEmployeeType(EmployeeType.MAINTENANCE);
		room.setStatus(CleaningStatus.NOT_SCHEDULED);
		Cleaning c = new Cleaning();
		when(roomService.scheduledCleaning(anyInt())).thenReturn(room);
		when(cleaningRepository.save(any())).thenReturn(c);
		assertEquals(c, cleaningService.scheduleCleaningRoom( employee.getEmployeeID(), room.getRoomNumber(), 0));
	}

	// -- Cleaning Start/Stop
	@Test
	public void startCleanRoomTest() throws NotFound {
		int employeeID = 1;
		Employee employee = new Employee();
		employee.setEmployeeID(employeeID);
		employee.setEmployeeType(EmployeeType.RECEPTIONIST);

		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);

		when(employeeService.findEmployeeByID(anyInt())).thenReturn(employee);
		assertNull(cleaningService.startCleanRoom(employeeID, roomNumber));

		employee.setEmployeeType(EmployeeType.MAINTENANCE);
		room.setStatus(CleaningStatus.NOT_SCHEDULED);
		when(roomService.startCleaning(anyInt())).thenReturn(room);
		assertEquals(room, cleaningService.startCleanRoom(employeeID, roomNumber));
	}

	@Test
	public void finishCleaningRoomTest() throws NotFound {
		int employeeID = 1;
		Employee employee = new Employee();
		employee.setEmployeeType(EmployeeType.RECEPTIONIST);

		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);

		when(roomService.findByRoomNumber(anyInt())).thenReturn(room);
		when(employeeService.findEmployeeByID(anyInt())).thenReturn(employee);
		assertNull(cleaningService.finishCleaningRoom(employeeID, roomNumber));

		employee.setEmployeeType(EmployeeType.MAINTENANCE);
		room.setStatus(CleaningStatus.NOT_SCHEDULED);
		when(roomService.finishCleaning(anyInt())).thenReturn(room);
		when(cleaningRepository.findByRoom(any())).thenReturn(java.util.Optional.of(new Cleaning()));
		assertEquals(room, cleaningService.finishCleaningRoom(employeeID, roomNumber));
	}

	// -- Finds
	@Test
	public void findAllCleaningsTest(){
		List<Cleaning> cleanings = new ArrayList<>();
		cleanings.add(new Cleaning());
		cleanings.add(new Cleaning());
		cleanings.add(new Cleaning());
		cleanings.add(new Cleaning());
		Page<Cleaning> cleaningPage = new PageImpl<>(cleanings);
		when(cleaningRepository.findAllByOrderByPriorityDescDateAddedAsc(any())).thenReturn(cleaningPage);
		assertEquals(cleanings, cleaningService.findAllCleanings(any()).getContent());
	}

	@Test
	public void findAllCleaningsByEmployeeTest() throws NotFound {
		int employeeID = 1;
		Employee employee = new Employee();
		employee.setEmployeeID(employeeID);

		List<Cleaning> cleaningList = new ArrayList<>();
		cleaningList.add(new Cleaning());
		Page<Cleaning> cleaningPage = new PageImpl<>(cleaningList);

		when(employeeService.findEmployeeByID(anyInt())).thenReturn(employee);
		when(cleaningRepository.findAllByEmployeeOrderByPriorityDescDateAddedAsc(any(),any())).thenReturn(cleaningPage);
		assertEquals(cleaningList, cleaningService.findAllCleaningsByEmployee(employeeID,null).getContent());
	}

	@Test
	public void findByRoomTest() throws NotFound {
		Cleaning cleaning = new Cleaning();
		when(cleaningRepository.findByRoom(any())).thenReturn(java.util.Optional.of(cleaning));
		assertEquals(cleaning, cleaningService.findByRoom(new Room()));
	}

	// -- Getter/Setter
	@Test
	public void gettersSetters(){
		CleaningRepository cleaningRepository = null;
		RoomService roomService = new RoomService();
		EmployeeService employeeService = new EmployeeService();

		CleaningService cleaningService = new CleaningService();
		cleaningService.setCleaningRepository(cleaningRepository);
		cleaningService.setRoomService(roomService);
		cleaningService.setEmployeeService(employeeService);

		assertNull(cleaningService.getCleaningRepository());
		assertEquals(roomService, cleaningService.getRoomService());
		assertEquals(employeeService, cleaningService.getEmployeeService());

		cleaningService.setRoomService(null);
		cleaningService.setCleaningRepository(null);
		cleaningService.setEmployeeService(null);

		assertNull(cleaningService.getCleaningRepository());
		assertNull(cleaningService.getRoomService());
		assertNull(cleaningService.getEmployeeService());
	}
}
