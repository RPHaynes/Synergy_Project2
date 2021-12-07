package com.revature.shms.controllers;

import com.revature.shms.enums.ReservationStatus;
import com.revature.shms.models.Reservation;
import com.revature.shms.repositories.ReservationRepository;
import com.revature.shms.repositories.UserRepository;
import com.revature.shms.services.ReservationService;
import com.revature.shms.services.UserService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationService reservationService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    /**
     * Employees can view all reservations here
     * @return
     */
    @GetMapping("/all")
    public Page<Reservation> returnAllReservations(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy){
        return reservationRepository.findAll(PageRequest.of(pageNumber,pageSize, Sort.by(sortBy)));
    }

    /**
     * An employee can set the status of a reservation to accepted or rejected.
     * @param status to change
     * @return
     * @throws NotFound
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<?> setStatusOfReservation( @PathVariable("id") int id, @RequestParam("status") ReservationStatus status) throws NotFound {
           return ResponseEntity.ok(reservationService.changeStatusOfReservation(id, status));
    }
  
    /**
     * A user can send a psot reuquest with the start and end date of a reservation.
     * @param reservation
     * @return
     * @throws NotFound
     */
    @PostMapping("/save")
    public ResponseEntity<?> createNewReservation(@RequestBody Reservation reservation) throws NotFound {
        return  ResponseEntity.ok( reservationService.setReservation(reservation));
    }

    /**
     * This changes the dates of the reservation
     * @param endDate startDate
     * @return
     * @throws NotFound
     */
    @PostMapping("/update/dates/{id}")
    public ResponseEntity<?> setDateReservation(@PathVariable("id") int id,  @RequestParam("startDate") String startDate, @RequestParam("startDate") String endDate ) throws NotFound {
        return ResponseEntity.ok(reservationService.changeDateOfReservation(id, startDate, endDate));
    }
}
