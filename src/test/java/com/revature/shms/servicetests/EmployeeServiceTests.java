package com.revature.shms.servicetests;

import com.revature.shms.enums.EmployeeType;
import com.revature.shms.models.Employee;
import com.revature.shms.repositories.EmployeeRepository;
import com.revature.shms.repositories.RoomRepository;
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

	// -- Updates
	@Test
	public void updatePasswordTest(){
		int employeeID = 1;
		String username = "jlecl";
		String oldPassword = "Password";
		String newPassword = "new";

		when(employeeRepository.findByUsername(any())).thenReturn(java.util.Optional.empty());
		assertFalse(employeeService.updatePassword(username, oldPassword, newPassword)); // no info

		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(employeeService.updatePassword(employeeID, oldPassword, newPassword)); // no info

		Employee employee = new Employee();
		employee.setEmployeeID(employeeID);
		employee.setUsername(username);
		employee.setPassword(oldPassword);
		when(employeeRepository.findByUsername(any())).thenReturn(java.util.Optional.of(employee));
		assertFalse(employeeService.updatePassword(username, newPassword, oldPassword)); // wrong password
		assertTrue(employeeService.updatePassword(username, oldPassword, newPassword)); // right password

		employee.setPassword(oldPassword);
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.of(employee));
		assertFalse(employeeService.updatePassword(employeeID, newPassword, oldPassword)); // wrong password
		assertTrue(employeeService.updatePassword(employeeID, oldPassword, newPassword)); // right password
	}

	@Test
	public void updateFirstNameTest(){
		int userID = 1;
		String firstName = "Jennica";

		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(employeeService.updateFirstName(userID, firstName));

		Employee employee = new Employee();
		employee.setEmployeeID(userID);
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.of(employee));
		assertTrue(employeeService.updateFirstName(userID, firstName));
	}

	@Test
	public void updateLastNameTestTrue(){
		int userID = 1;
		String lastName = "LeClerc";

		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(employeeService.updateLastName(userID, lastName));

		Employee employee = new Employee();
		employee.setEmployeeID(userID);
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.of(employee));
		assertTrue(employeeService.updateLastName(userID, lastName));
	}

	// -- Finds
	@Test
	public void findAllEmployeesTest(){
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee());
		employeeList.add(new Employee());
		Page<Employee> employeePage = new PageImpl<>(employeeList);
		when(employeeRepository.findAllByOrderByEmployeeType(any())).thenReturn(employeePage);
		assertEquals(employeeList, employeeService.findAllEmployees(null).getContent());
	}

	@Test
	public void findAllEmployeesByTypeTest(){
		EmployeeType employeeType = EmployeeType.RECEPTIONIST;
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee());
		employeeList.add(new Employee());
		Page<Employee> employeePage = new PageImpl<>(employeeList);
		when(employeeRepository.findByEmployeeType(any(), any())).thenReturn(employeePage);
		assertEquals(employeeList, employeeService.findAllEmployeesByType(employeeType,null).getContent());
	}

	@Test
	public void findEmployeeByIDTest() throws NotFound {
		Employee employee = new Employee();
		when(employeeRepository.findByEmployeeID(anyInt())).thenReturn(java.util.Optional.of(employee));
		assertEquals(employee, employeeService.findEmployeeByID(0));
	}

	@Test
	public void findEmployeeByUserNameTest() throws NotFound {
		String username = "username";
		Employee employee = new Employee();
		when(employeeRepository.findByUsername(any())).thenReturn(java.util.Optional.of(employee));
		assertEquals(employee, employeeService.findEmployeeByUserName(username));
	}

	// -- Getter/Setter
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
