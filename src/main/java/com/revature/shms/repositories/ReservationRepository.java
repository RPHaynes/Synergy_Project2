package com.revature.shms.repositories;

import com.revature.shms.enums.ReservationStatus;
import com.revature.shms.models.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer > {
	Page<Reservation>  findAllByUserReserve_UserID(int userID, Pageable pageable);
	Optional<Reservation> findByReservationID(int reservationID);

	void deleteByUserReserve_UserID(int userID);
	void deleteByReservationID(int reservationID);
	Page<Reservation> findAllByStatusAndStartDateBefore(ReservationStatus status, Date today, Pageable pageable);
	Page<Reservation> findAllByStatusAndStartDateBeforeAndEndDateAfter(ReservationStatus status, Date today, Date date, Pageable pageable);
	Page<Reservation> findAllByStatus(ReservationStatus status, Pageable pageable);
}
