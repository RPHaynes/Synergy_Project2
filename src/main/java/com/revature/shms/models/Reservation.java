package com.revature.shms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.revature.shms.enums.CleaningStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.revature.shms.enums.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    String startDate;
    @Column
    String endDate;

    @Column
    String accommodations;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private User userReserve;
}
