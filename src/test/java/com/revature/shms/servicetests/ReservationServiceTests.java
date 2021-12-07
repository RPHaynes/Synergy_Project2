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

import java.util.ArrayList;
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

	@Test
	public void findReservationByUserID() throws NotFound {
		int reservationID = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);
		when(reservationRepository.findByUserReserve_UserID(anyInt())).thenReturn(java.util.Optional.of(reservation));
		assertEquals(reservationID, reservationService.findReservationByUserID(reservationID).getReservationID());
	}

	@Test
	public void findReservationByReservationIDTest() throws NotFound {
		int reservationID = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);
		when(reservationRepository.findByReservationID(anyInt())).thenReturn(java.util.Optional.of(reservation));
		assertEquals(reservationID, reservationService.findReservationByReservationID(reservationID).getReservationID());
	}

	@Test
	public void findAllReservationsTest() {
		List<Reservation> test = new ArrayList<>();
		Reservation reservation = new Reservation();
		reservation.setReservationID(1);
		test.add(reservation);
		when(reservationRepository.findAll()).thenReturn(test);
		assertEquals(test, reservationService.findAll());
	}

	@Test
	public void getSpecificReservation() throws NotFound {
		int reservationID = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);
		when(reservationRepository.findByReservationID(anyInt())).thenReturn(java.util.Optional.of(reservation));
		assertEquals(reservationID, reservationService.findReservationByReservationID(reservationID).getReservationID());
	}

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
	public void setAccommodationsTest() throws NotFound {
		Reservation reservation = new Reservation();
		int reservationID = 1;
		reservation.setReservationID(1);
		when(reservationRepository.findByReservationID(anyInt())).thenReturn(java.util.Optional.of(reservation));
		when(reservationRepository.save(any())).thenReturn(reservation);
		String accommodations = "Things I want";
		assertEquals(accommodations, reservationService.setAccommodations(reservationID, accommodations).getAccommodations());
	}

	@Test
	public void changeDateOfReservationTest() throws NotFound {
		int reservationID = 1;
		Reservation reservation = new Reservation();
		reservation.setReservationID(reservationID);
		Reservation update = new Reservation();
		update.setReservationID(reservationID);
		reservation.setStatus(ReservationStatus.APPROVED);
		update.setStatus(ReservationStatus.CANCELLED);
		when(reservationRepository.findByReservationID(anyInt())).thenReturn(java.util.Optional.of(reservation));
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.changeDateOfReservation(reservationID,"1", "1"));
	}

	@Test
	public void reserveTest(){
		Reservation reservation = new Reservation();
		Reservation customReservation = new Reservation();
		User user = new User();
		customReservation.setUserReserve(user);
		customReservation.setStartDate("1");
		customReservation.setEndDate("1");
		when(reservationRepository.save(any())).thenReturn(reservation);
		assertEquals(reservation, reservationService.setReservation(customReservation));
	}

	@Test
	public void gettersSetters() {
		ReservationService reservationService = new ReservationService();

		ReservationRepository reservationRepository = null;

		reservationService.setReservationRepository(reservationRepository);
		assertEquals(reservationRepository, reservationService.getReservationRepository());
	}
}
