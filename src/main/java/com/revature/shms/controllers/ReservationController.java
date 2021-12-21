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

import java.text.ParseException;

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

    @GetMapping("/all")
    public Page<Reservation> returnAllReservations(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy){
        return reservationRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> setStatusOfReservation(@PathVariable("id") int reservationID, @RequestParam("status") ReservationStatus status) throws NotFound {
           return ResponseEntity.ok(reservationService.changeStatusOfReservation(reservationID, status));
    }

    @PostMapping
    public ResponseEntity<?> createNewReservation(@RequestBody Reservation reservation) throws NotFound {
        return  ResponseEntity.ok(reservationService.setReservation(reservation));
    }

    @PostMapping("/update/dates/{id}")
    public ResponseEntity<?> setDateReservation(@PathVariable("id") int reservationID, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws NotFound, ParseException {
        return ResponseEntity.ok(reservationService.changeDateOfReservation(reservationID, startDate, endDate));
    }

    @GetMapping("/username")
    public Page<Reservation> getReservationsByUsername(@RequestParam("username") String username, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy) {
        return reservationService.getAllReservationsOfUser(username, PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
    }

    @GetMapping("/status/pending")
    public Page<Reservation> getAllPending(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy){
        return reservationService.getAllPendingStatus(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
    }

    @GetMapping("/start")
    public Page<Reservation> startDateLessToday(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy) throws ParseException {
        return reservationService.getAllApprovedAndStartDate(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
    }

    @GetMapping("/startend")
    public Page<Reservation> startDateAndEndDate(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy) throws ParseException {
        return reservationService.getAllApprovedAndStartandEnd(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
    }

}
