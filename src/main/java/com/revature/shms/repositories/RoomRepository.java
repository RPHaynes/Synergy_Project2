package com.revature.shms.repositories;

import com.revature.shms.enums.Amenities;
import com.revature.shms.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {
    Page<Room> findAllByOrderByRoomNumberDesc(Pageable pageable);
    Page<Room> findAllByIsOccupied(boolean isOccupied,Pageable pageable);
    Page<Room> findAllByAmenitiesList_Amenity(Amenities amenity,Pageable pageable);
    Page<Room> findAllByAmenitiesList_AmenityIn(Collection<Amenities> amenities,Pageable pageable);

    Optional<Room> findByRoomNumber(int roomNumber);
}
