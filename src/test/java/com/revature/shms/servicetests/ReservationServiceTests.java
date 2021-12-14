package com.revature.shms.servicetests;

import com.revature.shms.enums.ReservationStatus;
import com.revature.shms.models.Reservation;
import com.revature.shms.models.User;
import com.revature.shms.repositories.ReservationRepository;
import com.revature.shms.services.ReservationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
	@InjectMocks
	ReservationService reservationService;
	@Mock
	ReservationRepository reservationRepository;

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
		int reservationID = 0;
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);
		User user = new User();
		reservation.setUserReserve(user);
		reservation.setUserReserve(user);
		List<Reservation> reservations1 = new ArrayList<>();
		reservations1.add(new Reservation());
		reservations1.add(new Reservation());
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

	// -- Getter/Setters
	@Test
	public void gettersSetters() {
		ReservationService reservationService = new ReservationService();
		ReservationRepository reservationRepository = null;
		reservationService.setReservationRepository(reservationRepository);
		assertEquals(reservationRepository, reservationService.getReservationRepository());
	}
}
