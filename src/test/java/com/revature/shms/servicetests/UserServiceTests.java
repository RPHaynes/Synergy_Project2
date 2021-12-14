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

	// -- Update
	@Test
	public void updatePasswordTest(){
		int userID = 1;
		String username = "jlecl";
		String oldPassword = "Password";
		String newPassword = "new";

		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.empty());
		assertFalse(userService.updatePassword(username, oldPassword, newPassword)); // no info

		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(userService.updatePassword(userID, oldPassword, newPassword)); // no info

		User user = new User();
		user.setUserID(userID);
		user.setUsername(username);
		user.setPassword(oldPassword);
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
		assertFalse(userService.updatePassword(username, newPassword, oldPassword)); // wrong password
		assertTrue(userService.updatePassword(username, oldPassword, newPassword)); // right password

		user.setPassword(oldPassword);
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.of(user));
		assertFalse(userService.updatePassword(userID, newPassword, oldPassword)); // wrong password
		assertTrue(userService.updatePassword(userID, oldPassword, newPassword)); // right password
	}

	@Test
	public void updateFirstNameTest(){
		int userID = 1;
		String firstName = "Jennica";

		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(userService.updateFirstName(userID, firstName));

		User user = new User();
		user.setUserID(userID);
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.of(user));
		assertTrue(userService.updateFirstName(userID, firstName));
	}

	@Test
	public void updateLastNameTest(){
		int userID = 1;
		String lastName = "LeClerc";

		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(userService.updateLastName(userID, lastName));

		User user = new User();
		user.setUserID(userID);
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.of(user));
		assertTrue(userService.updateLastName(userID, lastName));
	}

	@Test
	public void updateEmailTest(){
		int userID = 1;
		String email = "email@email.com";

		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.empty());
		assertFalse(userService.updateEmail(userID, email));

		User user = new User();
		user.setUserID(userID);
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.of(user));
		assertTrue(userService.updateEmail(userID, email));
	}

	// -- Finds
	@Test
	public void findAllUsersTest(){
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
	public void findUserByUserNameTest() throws NotFound {
		User user = new User();
		user.setUsername("Ryan");
		user.setPassword("123123");
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
		assertEquals(user, userService.findUserByUsername("Ryan"));
	}

	@Test
	public void findUserByUserId() throws NotFound {
		User user = new User();
		user.setUserID(123);
		when(userRepository.findByUserID(anyInt())).thenReturn(java.util.Optional.of(user));
		assertEquals(user, userService.findUserByUserID(123));
	}

	// -- Getter/Setter
	@Test
	public void gettersSetters(){
		UserService userService = new UserService();
		UserRepository userRepository = null;
		userService.setUserRepository(userRepository);
		assertNull(userService.getUserRepository());
	}
}
