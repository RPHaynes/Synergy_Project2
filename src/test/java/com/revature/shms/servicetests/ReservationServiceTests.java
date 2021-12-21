package com.revature.shms.servicetests;

import com.revature.shms.enums.ReservationStatus;
import com.revature.shms.models.Reservation;
import com.revature.shms.models.User;
import com.revature.shms.repositories.ReservationRepository;
import com.revature.shms.services.ReservationService;
import com.revature.shms.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
	@InjectMocks
	ReservationService reservationService;

	@Mock
	ReservationRepository reservationRepository;
	@Mock
	UserRepository userRepository;

	// -- Create/Delete
	@Test
	public void createReservationTest() {
		Reservation reservation = new Reservation();
		reservation.setStatus(ReservationStatus.APPROVED);
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.createReservation(reservation));
	}

	@Test
	public void deleteReservationByUserIDTest() {
		reservationService.deleteReservationByUserID(1231245);
		verify(reservationRepository, times(1)).deleteByUserReserve_UserID(anyInt());
	}

	@Test
	public void deleteReservationByReservationIDTest() {
		reservationService.deleteReservationByReservationID(1231245);
		verify(reservationRepository, times(1)).deleteByReservationID(anyInt());
	}

	@Test
	public void setReservationTest() throws ParseException {
		Reservation reservation = new Reservation();
		Reservation customReservation = new Reservation();
		User user = new User();
		customReservation.setUserReserve(user);
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1990-12-12");
		customReservation.setStartDate(date);
		customReservation.setEndDate(date);
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.setReservation(customReservation));
	}

	@Test
	public void setAccommodationsTest() throws NotFound {
		Reservation reservation = new Reservation();
		int reservationID = 1;
		reservation.setReservationID(1);
		when(reservationRepository.findByReservationID(anyInt())).thenReturn(java.util.Optional.of(reservation));
		when(reservationRepository.save(any())).thenReturn(reservation);
		String accommodations = "Things I want";
		assertEquals(accommodations, reservationService.setAccommodations(reservationID, accommodations).getAccommodations());
	}

	// -- Change
	@Test
	public void changeStatusOfReservationTest() throws NotFound {
		User user = new User();
		Reservation reservation = new Reservation();
		Reservation update = new Reservation();
		update.setUserReserve(user);
		update.setStatus(ReservationStatus.REJECTED);
		reservation.setReservationID(1);
		reservation.setStatus(ReservationStatus.APPROVED);
		update.setStatus(ReservationStatus.CANCELLED);
		when(reservationRepository.findByReservationID(anyInt())).thenReturn(Optional.of(reservation));
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.changeStatusOfReservation(1, ReservationStatus.APPROVED));
	}

	@Test
	public void changeDateOfReservationTest() throws NotFound, ParseException {
		int reservationID = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);
		Reservation update = new Reservation();
		update.setReservationID(reservationID);
		reservation.setStatus(ReservationStatus.APPROVED);
		update.setStatus(ReservationStatus.CANCELLED);
		when(reservationRepository.findByReservationID(anyInt())).thenReturn(java.util.Optional.of(reservation));
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.changeDateOfReservation(reservationID,"1990/12/12", "1990/12/12"));
	}

	// -- Finds
	@Test
	public void findAllTest() {
		List<Reservation> test = new ArrayList<>();
		Reservation reservation = new Reservation();
		reservation.setReservationID(1);
		test.add(reservation);
		when(reservationRepository.findAll()).thenReturn(test);
		assertEquals(test, reservationService.findAll());
	}

	@Test
	public void findReservationByUserID() throws NotFound {
		int reservationID = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);
		User user = new User();
		reservation.setUserReserve(user);
		reservation.setUserReserve(user);
		List<Reservation> reservations1 = new ArrayList<>();
		reservations1.add(reservation);
		reservations1.add(reservation);
		Page<Reservation> reservations = new PageImpl<>(reservations1);
		when(reservationRepository.findAllByUserReserve_UserID(anyInt(), any())).thenReturn(reservations);
		assertEquals(reservationID, reservationService.findReservationByUserID( reservationID, PageRequest.of(0, 10, Sort.by(String.valueOf(reservationID)))).getContent().get(1).getReservationID());
	}

	@Test
	public void findReservationByReservationIDTest() throws NotFound {
		int reservationID = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);
		when(reservationRepository.findByReservationID(anyInt())).thenReturn(java.util.Optional.of(reservation));
		assertEquals(reservationID, reservationService.findReservationByReservationID(reservationID).getReservationID());
	}

	// -- Gets
	@Test
	public void getAllReservationsOfUserTest() {
		int reservationID = 1;
		int userID = 1;
		String username = "jlecl";
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);

		Reservation reservationWrong = new Reservation();
		reservationWrong.setReservationID(reservationID + 1);

		User user = new User();
		user.setUserID(userID);
		user.setUsername(username);

		User wrongUser = new User();
		wrongUser.setUserID(userID + 1);
		wrongUser.setUsername("wrong");

		reservation.setUserReserve(user);

		reservationWrong.setUserReserve(wrongUser);

		List<Reservation> reservations1 = new ArrayList<>();
		reservations1.add(reservation);
		reservations1.add(reservation);

		List<Reservation> reservations1Wrong = new ArrayList<>();
		reservations1Wrong.add(reservationWrong);
		reservations1Wrong.add(reservationWrong);

		Page<Reservation> reservations = new PageImpl<>(reservations1);
		Page<Reservation> reservationsWrong = new PageImpl<>(reservations1Wrong);

		when(reservationRepository.findAllByUserReserve_UserID(anyInt(), any())).thenReturn(reservationsWrong);
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(wrongUser));
		assertNotEquals(reservationID, reservationService.getAllReservationsOfUser( username, PageRequest.of(0, 10, Sort.by(String.valueOf(reservationID)))).getContent().get(1).getReservationID());

		when(reservationRepository.findAllByUserReserve_UserID(anyInt(), any())).thenReturn(reservations);
		when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
		assertEquals(reservationID, reservationService.getAllReservationsOfUser( username, PageRequest.of(0, 10, Sort.by(String.valueOf(reservationID)))).getContent().get(1).getReservationID());
	}

	@Test
	public void getAllPendingStatusTest() {
		Reservation reservation = new Reservation();
		reservation.setStatus(ReservationStatus.PENDING);

		List<Reservation> reservations1 = new ArrayList<>();
		reservations1.add(reservation);
		reservations1.add(reservation);

		Page<Reservation> reservations = new PageImpl<>(reservations1);

		when(reservationRepository.findAllByStatus(any(),any())).thenReturn(reservations);
		assertEquals(reservations, reservationService.getAllPendingStatus(PageRequest.of(0, 10, Sort.by(String.valueOf(reservations)))));
	}

	@Test
	public void getAllApprovedAndStartDateTest() throws ParseException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		Date todayPlusThree = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(now.plusDays(3)));

		Reservation reservation = new Reservation();
		reservation.setStatus(ReservationStatus.APPROVED);
		reservation.setStartDate(todayPlusThree);

		List<Reservation> reservations1 = new ArrayList<>();
		reservations1.add(reservation);
		reservations1.add(reservation);

		Page<Reservation> reservations = new PageImpl<>(reservations1);

		when(reservationRepository.findAllByStatusAndStartDateBeforeAndStartDateAfter(any(),any(),any(), any())).thenReturn(reservations);
		assertEquals(reservations, reservationService.getAllApprovedAndStartDate(PageRequest.of(0, 10, Sort.by(String.valueOf(reservations)))));
	}

	@Test
	public void getAllApprovedAndStartandEndTest() throws ParseException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		Date todayPlusThree = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(now.plusDays(3)));
		Date todayMinusThree = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(now.minusDays(3)));

		Reservation reservation = new Reservation();
		reservation.setStatus(ReservationStatus.APPROVED);
		reservation.setStartDate(todayMinusThree);
		reservation.setEndDate(todayPlusThree);

		List<Reservation> reservations1 = new ArrayList<>();
		reservations1.add(reservation);
		reservations1.add(reservation);

		Page<Reservation> reservations = new PageImpl<>(reservations1);

		when(reservationRepository.findAllByStatusAndStartDateBeforeAndEndDateAfter(any(),any(),any(), any())).thenReturn(reservations);
		assertEquals(reservations, reservationService.getAllApprovedAndStartandEnd(PageRequest.of(0, 10, Sort.by(String.valueOf(reservations)))));
	}

	// -- Getter/Setters
	@Test
	public void gettersSetters() {
		ReservationService reservationService = new ReservationService();
		ReservationRepository reservationRepository = null;
		reservationService.setReservationRepository(reservationRepository);
		assertNull(reservationService.getReservationRepository());

		UserRepository userRepository = null;
		reservationService.setUserRepository(userRepository);
		assertNull(reservationService.getUserRepository());
	}
}
