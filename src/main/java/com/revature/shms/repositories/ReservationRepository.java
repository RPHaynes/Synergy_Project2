package com.revature.shms.repositories;

import com.revature.shms.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer > {
	/**
	 * gets Reservation by userId
	 */
	Optional<Reservation>  findByUserReserve_UserID(int id);

	/**
	 * Gets Reservation by ReservationId.
	 */
	Optional<Reservation> findByReservationID(int id);

	/**
	 * This deletes a reservation.
	 */
	void deleteByUserReserve_UserID(int id);

	/**
	 * This denies a reservation by the employee
	 */
//	Reservation denyReservationByEmployee_EmployeeId(int employeeId);

	/**
	 * This approves a reservation by the employee
	 */
//	Reservation approveReservationByEmployee_EmployeeId(int employeeId);
}
