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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class  ReservationService {
    @Autowired
	ReservationRepository reservationRepository;
    
    /**
     * Get a reservation with a userId
     * @param userID
     * @return Reservation
     * @throws NotFound
     */
    public Reservation findReservationByUserID(int userID) throws NotFound {
        return reservationRepository.findByUserReserve_UserID(userID).orElseThrow(NotFound::new);
    }
      
    /**
     * Get a reservation with a reservation Id
     * @param reservationId
     * @return Reservation
     * @throws NotFound
     */
    public Reservation findReservationByReservationID(int reservationId) throws NotFound {
        return reservationRepository.findByReservationID(reservationId).orElseThrow(NotFound::new);
    }

    /**
     * Get all reservations
     * @return a list of reservations
     */
    public List <Reservation> findAll(){
        return reservationRepository.findAll();
    }

    /**
     * This creates or saves a reservation
     * @param reservation
     * @return Reservation.
     */
    public Reservation createReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    /**
     * This deletes a reservation by a userId
     * @param userID
     */
    public void deleteReservationByUserID(int userID) {
		reservationRepository.deleteByUserReserve_UserID(userID);
    }

    /**
     * This toggles the reservation status for employees or users that cancel a reservation
     * @param reservationID
     * @param status
     * @return
     * @throws NotFound
     */
    public Reservation changeStatusOfReservation(int reservationID, ReservationStatus status) throws NotFound {
        Reservation reservation = findReservationByReservationID(reservationID);
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }

    /**
     * Sets the accommodations of the given reservation to the given accommodations string.
     * @param reservationID the given reservationID.
     * @param accommodations the accommodations that are being added or changed to.
     * @return a Reservation with the given accommodations.
     * @throws NotFound
     */
    public Reservation setAccommodations(int reservationID, String accommodations) throws NotFound {
        Reservation reservation = findReservationByReservationID(reservationID);
        reservation.setAccommodations(accommodations);
        return createReservation(reservation);
    }

    /**
     * This toggles the date
     * @param reservationID
     * @param startDate
     * @param endDate
     * @return
     * @throws NotFound
     */
    public Reservation changeDateOfReservation(int reservationID, String startDate, String endDate) throws NotFound {
        Reservation reservation = findReservationByReservationID(reservationID);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return reservationRepository.save(reservation);
    }

    /**
     *
     * @param reservation
     * @return
     */
    public Reservation setReservation(Reservation reservation){
        reservation.setStatus(ReservationStatus.PENDING);
        return reservationRepository.save(reservation);
    }

    /*public Reservation setReservation(User user, String startDate, String endDate) {
        //System.out.println(" start date is: " + startDate);
        Reservation reservation = new Reservation();
        reservation.setUserReserve(user);
        reservation.setStatus(ReservationStatus.PENDING.toString());
        //System.out.println(reservation.getUserReserve().getUsername());

        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        //System.out.println(reservation.getStartDate());
        //System.out.println(reservation.getEndDate());
        return createReservation(reservation);
    }*/
}
