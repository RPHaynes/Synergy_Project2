package com.revature.shms.services;

import com.revature.shms.enums.ReservationStatus;
import com.revature.shms.models.Reservation;
import com.revature.shms.models.User;
import com.revature.shms.repositories.ReservationRepository;
import com.revature.shms.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class  ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;

    // -- Create/Delete
    /**
     * This creates or saves a reservation
     *
     * @param reservation object that will be created.
     * @return Reservation object that was created .
     */
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    /**
     * This deletes a reservation by a userID.
     *
     * @param userID of user that will have their reservation deleted.
     */
    public void deleteReservationByUserID(int userID) {
        reservationRepository.deleteByUserReserve_UserID(userID);
    }

    /**
     * This deletes a reservation by a reservationID.
     *
     * @param reservationID of the reservation to be deleted.
     */
    public void deleteReservationByReservationID(int reservationID) {
        reservationRepository.deleteByReservationID(reservationID);
    }

    // -- Sets

    /**
     * Handles the creation of a reservation and sets the default status as pending
     *
     * @param reservation object that will be created
     * @return Reservation object that was created with a Pending status.
     */
    public Reservation setReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.PENDING);
        return reservationRepository.save(reservation);
    }

    /**
     * Sets the accommodations of the given reservation to the given accommodations string.
     *
     * @param reservationID  of reservation that will be changed.
     * @param accommodations the accommodations that are being added or changed to.
     * @return a Reservation with the given accommodations.
     * @throws NotFound exception if a reservation with the given reservationID was not found.
     */
    public Reservation setAccommodations(int reservationID, String accommodations) throws NotFound {
        Reservation reservation = findReservationByReservationID(reservationID);
        reservation.setAccommodations(accommodations);
        return createReservation(reservation);
    }

    // -- Change
    /**
     * This toggles the reservation status for employees or users that cancel a reservation
     * @param reservationID of reservation that will be changed.
     * @param status        that will be used to update
     * @return Reservation object that was updated
     * @throws NotFound exception if a reservation with the given reservationID was not found.
     */
    public Reservation changeStatusOfReservation(int reservationID, ReservationStatus status) throws NotFound {
        Reservation reservation = findReservationByReservationID(reservationID);
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }

    /**
     * This toggles the date
     * @param reservationID of reservation that will be changed.
     * @param startDate     start date that will be updated
     * @param endDate       end date that will be updated
     * @return Reservation object with updated fields
     * @throws NotFound exception if a reservation with the given reservationID was not found.
     */
    public Reservation changeDateOfReservation(int reservationID, String startDate, String endDate) throws NotFound, ParseException {
        Reservation reservation = findReservationByReservationID(reservationID);
        Date start = new SimpleDateFormat("yyyy/MM/dd").parse(startDate);
        Date end = new SimpleDateFormat("yyyy/MM/dd").parse(endDate);
        reservation.setStartDate(start);
        reservation.setEndDate(end);
        return reservationRepository.save(reservation);
    }

    // -- Finds

    /**
     * Get all reservations
     * @return a list of reservations
     */
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    /**
     * Get a reservation with a userId
     * @param userID of user that created a reservation.
     * @return Reservation object of the specific user.
     * @throws NotFound exception if reservation not found.
     */
    public Page<Reservation> findReservationByUserID(int userID, Pageable pageable) throws NotFound {
        return reservationRepository.findAllByUserReserve_UserID(userID, pageable);
    }

    /**
     * Get a reservation with a reservation Id
     * @param reservationId specific to a reservation
     * @return Reservation object of the reservation id.
     * @throws NotFound exception id reservation not found.
     */
    public Reservation findReservationByReservationID(int reservationId) throws NotFound {
        return reservationRepository.findByReservationID(reservationId).orElseThrow(NotFound::new);
    }

    /**
     * This method gets all reservations of a user
     * @param username of the user.
     * @param pageable of the request
     * @return A page of reservations of the user.
     */
    public Page<Reservation> getAllReservationsOfUser(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).get();
        return reservationRepository.findAllByUserReserve_UserID(user.getUserID(), pageable);
    }

    /**
     * This method gets all the reservations that are pending.
     * @param pageable of the request.
     * @return A page of the reservations.
     */
    public Page<Reservation> getAllPendingStatus(Pageable pageable) {
        return reservationRepository.findAllByStatus(ReservationStatus.PENDING, pageable);
    }

    /**
     *This method gets all reservations that are approved and that have a start date that is less than five days from today
     * @param pageable of the request
     * @return A page of the reservations
     * @throws ParseException if the date is in wrong format
     */
    public Page<Reservation> getAllApprovedAndStartDate(Pageable pageable) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        Date today = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(now));
        Date todayPlusFive = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(now.plusDays(5)));
        System.out.println(todayPlusFive+" plus");
        System.out.println(today+ " today");
        return reservationRepository.findAllByStatusAndStartDateBeforeAndStartDateAfter(ReservationStatus.APPROVED, todayPlusFive, today,  pageable);
    }

    /**
     *This method gets all reservations that are approved and that have a start date before today and an end date after today
     * @param pageable of the request
     * @return A page of the reservations
     * @throws ParseException if the date is in wrong format
     */
    public Page<Reservation> getAllApprovedAndStartandEnd(Pageable pageable) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        Date today = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(now));
        return reservationRepository.findAllByStatusAndStartDateBeforeAndEndDateAfter(ReservationStatus.APPROVED, today, today, pageable);
    }
}
