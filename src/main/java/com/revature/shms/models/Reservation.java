package com.revature.shms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.revature.shms.enums.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Reservation model
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int reservationID;

    @Column
    ReservationStatus status;

    @Column
    Date startDate;
    @Column
    Date endDate;

    @Column
    String accommodations;


    @ManyToOne(cascade = CascadeType.MERGE)
	@JsonBackReference
    User userReserve;
}
