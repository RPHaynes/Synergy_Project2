package com.revature.shms.servicetests;

import com.revature.shms.enums.EmployeeType;
import com.revature.shms.models.Cleaning;
import com.revature.shms.models.Employee;
import com.revature.shms.models.User;
import com.revature.shms.repositories.EmployeeRepository;
import com.revature.shms.repositories.RoomRepository;
import com.revature.shms.services.CleaningService;
import com.revature.shms.services.EmployeeService;
import com.revature.shms.services.RoomService;
import com.revature.shms.services.UserService;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

	@Mock EmployeeRepository employeeRepository;
	@Mock CleaningService cleaningService;
	@InjectMocks EmployeeService employeeService;

	@Test
	public void createEmployeeTest(){
		Employee employee = new Employee();
		when(employeeRepository.save(any())).thenReturn(employee);
		assertEquals(employee, employeeService.createEmployee(employee));
	}

	@Test
	public void loginEmployeeTests() {
		Employee employee = new Employee();
		employee.setUsername("RPH");
		employee.setPassword("RPH123");
		when(employeeRepository.findByUsername(any())).thenReturn(java.util.Optional.of(employee));
		assertEquals(employee, employeeService.loginEmployee("RPH","RPH123"));
		try {
			Exception exception = assertThrows(org.springframework.security.access.AccessDeniedException.class, (Executable) employeeService.loginEmployee("RPH","123"));
			assertTrue(exception.getMessage().contains("Incorrect username/password"));
		} catch (Exception ignored){}
	}

	@Test
	public void getAllEmployeesTest(){
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee());
		employeeList.add(new Employee());
		Page<Employee> employeePage = new PageImpl<>(employeeList);
		when(employeeRepository.findAllByOrderByEmployeeType(any())).thenReturn(employeePage);
		assertEquals(employeeList, employeeService.findAllEmployees(null).getContent());
	}

	@Test
	public void getAllEmployeesByTypeTest(){
		EmployeeType employeeType = EmployeeType.RECEPTIONIST;
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee());
		employeeList.add(new Employee());
		Page<Employee> employeePage = new PageImpl<>(employeeList);
		when(employeeRepository.findByEmployeeType(any(), any())).thenReturn(employeePage);
		assertEquals(employeeList, employeeService.findAllEmployeesByType(employeeType,null).getContent());
	}

	@Test
	public void getEmployeeByIDTest() throws NotFound {
		Employee employee = new Employee();
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.of(employee));
		assertEquals(employee, employeeService.findEmployeeByID(0));
	}

	@Test
	public void getEmployeeByUserNameTest() throws NotFound {
		String username = "username";
		Employee employee = new Employee();
		when(employeeRepository.findByUsername(any())).thenReturn(java.util.Optional.of(employee));
		assertEquals(employee, employeeService.findEmployeeByUserName(username));
	}



	@Test
	public void updatePasswordTestTrue(){
		String username = "jlecl";
		String oldPassword = "Password";
		String newPassword = "new";
		Employee employee = new Employee();
		employee.setUsername(username);
		employee.setPassword(oldPassword);
		when(employeeRepository.findByUsername(any())).thenReturn(java.util.Optional.of(employee));
		assertTrue(employeeService.updatePassword(username, oldPassword, newPassword));
	}

	@Test
	public void updatePasswordTestWrongPassword(){
		String username = "jlecl";
		String rightPassword = "Password";
		String wrongPassword = "new";
		Employee employee = new Employee();
		employee.setUsername(username);
		employee.setPassword(rightPassword);
		when(employeeRepository.findByUsername(any())).thenReturn(java.util.Optional.of(employee));
		assertFalse(employeeService.updatePassword(username, wrongPassword, rightPassword));
	}

	@Test
	public void updatePasswordTestFalse(){
		String username = "jlecl";
		String oldPassword = "Password";
		String newPassword = "new";
		when(employeeRepository.findByUsername(any())).thenReturn(java.util.Optional.empty());
		assertFalse(employeeService.updatePassword(username, oldPassword, newPassword));
	}

	@Test
	public void updateFirstNameTestTrue(){
		int userID = 1;
		String firstName = "Jennica";
		Employee employee = new Employee();
		employee.setEmployeeID(userID);
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.of(employee));
		assertTrue(employeeService.updateFirstName(userID, firstName));
	}

	@Test
	public void updateFirstNameTestFalse(){
		int userID = 1;
		String firstName = "Jennica";
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(employeeService.updateFirstName(userID, firstName));
	}

	@Test
	public void updateLastNameTestTrue(){
		int userID = 1;
		String lastName = "LeClerc";
		Employee employee = new Employee();
		employee.setEmployeeID(userID);
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.of(employee));
		assertTrue(employeeService.updateLastName(userID, lastName));
	}

	@Test
	public void updateLastNameTestFalse(){
		int userID = 1;
		String lastName = "LeClerc";
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(employeeService.updateLastName(userID, lastName));
	}

	@Test
	public void settersGettersTest(){
		EmployeeService employeeService = new EmployeeService();

		EmployeeRepository employeeRepository = null;
		RoomRepository roomRepository = null;
		RoomService roomService = new RoomService();
		UserService userService = new UserService();

		employeeService.setRoomService(roomService);
		employeeService.setUserService(userService);
		employeeService.setEmployeeRepository(employeeRepository);
		employeeService.setRoomRepository(roomRepository);

		assertEquals(roomService,employeeService.getRoomService());
		assertEquals(userService,employeeService.getUserService());
		assertNull(employeeService.getEmployeeRepository());
		assertNull(employeeService.getRoomRepository());
	}
}
