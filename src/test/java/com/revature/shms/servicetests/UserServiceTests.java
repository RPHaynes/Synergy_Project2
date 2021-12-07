package com.revature.shms.servicetests;

import com.revature.shms.models.User;
import com.revature.shms.repositories.UserRepository;
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

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
	@Mock UserRepository userRepository;
	@InjectMocks UserService userService;

	@Test
	public void createNewUserTest(){
		User user = new User();
		when(userRepository.save(any())).thenReturn(user);
		assertEquals(user, userService.createNewUser(user));
	}
	
	@Test
	public void loginTest(){
		User user = new User();
		user.setUsername("Ryan");
		user.setPassword("123123");
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
		assertEquals(user, userService.login("Ryan","123123"));
		try {
			Exception e= assertThrows(AccessDeniedException.class, (Executable) userService.login("Ryan","1231234"));
			assertTrue(e.getMessage().contains("Incorrect username/password"));
		} catch (Exception ignored){}
	}
	
	@Test
	public void logoutTest(){
		assertEquals("redirect:logoutPage", userService.logout());
	}
	
	@Test
	public void getUserByUserNameTest() throws NotFound {
		User user = new User();
		user.setUsername("Ryan");
		user.setPassword("123123");
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
		assertEquals(user, userService.findUserByUsername("Ryan"));
	}
	
	@Test
	public void getAllUsersTest(){
		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		users.add(new User());
		users.add(new User());
		users.add(new User());
		Page<User> usersPage = new PageImpl<>(users);
		when(userRepository.findAllByOrderByUserIDDesc(any())).thenReturn(usersPage);
		assertEquals(users, userService.findAllUsers(null).getContent());
	}
	
	@Test
	public void getUserByUserId() throws NotFound {
		User user = new User();
		user.setUserID(123);
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.of(user));
		assertEquals(user, userService.findUserByUserID(123));
	}

	@Test
	public void updatePasswordTestTrue(){
		String username = "jlecl";
		String oldPassword = "Password";
		String newPassword = "new";
		User user = new User();
		user.setUsername(username);
		user.setPassword(oldPassword);
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
		assertTrue(userService.updatePassword(username, oldPassword, newPassword));
	}

	@Test
	public void updatePasswordTestWrongPassword(){
		String username = "jlecl";
		String rightPassword = "Password";
		String wrongPassword = "new";
		User user = new User();
		user.setUsername(username);
		user.setPassword(rightPassword);
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
		assertFalse(userService.updatePassword(username, wrongPassword, rightPassword));
	}

	@Test
	public void updatePasswordTestFalse(){
		String username = "jlecl";
		String oldPassword = "Password";
		String newPassword = "new";
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.empty());
		assertFalse(userService.updatePassword(username, oldPassword, newPassword));
	}

	@Test
	public void updateFirstNameTestTrue(){
		int userID = 1;
		String firstName = "Jennica";
		User user = new User();
		user.setUserID(userID);
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.of(user));
		assertTrue(userService.updateFirstName(userID, firstName));
	}

	@Test
	public void updateFirstNameTestFalse(){
		int userID = 1;
		String firstName = "Jennica";
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(userService.updateFirstName(userID, firstName));
	}

	@Test
	public void updateLastNameTestTrue(){
		int userID = 1;
		String lastName = "LeClerc";
		User user = new User();
		user.setUserID(userID);
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.of(user));
		assertTrue(userService.updateLastName(userID, lastName));
	}

	@Test
	public void updateLastNameTestFalse(){
		int userID = 1;
		String lastName = "LeClerc";
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(userService.updateLastName(userID, lastName));
	}

	@Test
	public void gettersSetters(){
		UserService userService = new UserService();
		UserRepository userRepository = null;
		userService.setUserRepository(userRepository);
		assertNull(userService.getUserRepository());
	}
}
