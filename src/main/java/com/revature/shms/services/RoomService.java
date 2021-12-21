package com.revature.shms.services;

import com.revature.shms.enums.*;
import com.revature.shms.models.Room;
import com.revature.shms.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	/**
	 * Saves the given room to the database.
	 * @param room the room to be saved.
	 * @return Room now saved to the database.
	 */
	public Room addRoom(Room room){
		return roomRepository.save(room);
	}

	/**
	 * Saves the given list of rooms to the database.
	 * @param rooms a list of rooms to be saved.
	 * @return List<Room> now saved to the database.
	 */
	public List<Room> addRooms(List<Room> rooms){
		return roomRepository.saveAll(rooms);
	}

	// --- isAvailable ---
	/**
	 * Returns a boolean if the room is clean with no work being done and is not occupied.
	 * @param room the room to be worked on.
	 * @return boolean of if the room is available or not.
	 */


	// --- isOccupied ---
	/**
	 * Sets the room Occupation status of the given room to the given status.
	 * @param roomNumber the room to be worked on by room number.
	 * @param isOccupied is the room occupied or not.
	 * @return the room with the new occupation status.
	 * @throws NotFound is thrown if the room with the given room number does not exist.
	 */
	public Room setOccupationStatus(int roomNumber, boolean isOccupied) throws NotFound {
		Room room = findByRoomNumber(roomNumber);
		room.setOccupied(isOccupied);
		return addRoom(room);
	}

	/**
	 * Set the room Occupation status to true, aka. Occupied.
	 * @param roomNumber the room to be worked on by room number.
	 * @return the room with the occupation status set to true.
	 * @throws NotFound is thrown if the room with the given room number does not exist.
	 */
	public Room Occupied(int roomNumber) throws NotFound {
		return setOccupationStatus(roomNumber, true);
	}

	/**
	 * Set the room Occupation status to false, aka. not Occupied.
	 * @param roomNumber the room to be worked on by room number.
	 * @return the room with the occupation status set to false.
	 * @throws NotFound is thrown if the room with the given room number does not exist.
	 */
	public Room notOccupied(int roomNumber) throws NotFound {
		return setOccupationStatus(roomNumber, false);
	}
















	// --- Finds ---
	/**
	 *
	 * @param pageable the page information.
	 * @return List<Room> Ordered by Room Number in descending order.
	 */
	public Page<Room> findAllRooms(Pageable pageable){
		return roomRepository.findAllByOrderByRoomNumberDesc(pageable);
	}

	/**
	 * A Room with the given Room Number.
	 * @param roomNumber the Room Number to be matched.
	 * @return Room with the given Room Number.
	 * @throws NotFound is thrown if the room number does not exist.
	 */
	public Room findByRoomNumber(int roomNumber) throws NotFound {
		return roomRepository.findByRoomNumber(roomNumber).orElseThrow(NotFound::new);
	}
	/**
	 * Gets all Rooms with the given Amenity.
	 * @param amenity the Amenity to be matched.
	 * @param pageable the page information.
	 * @return List<Room> of all rooms that have the given Amenity.
	 */
	public Page<Room> findAllByAmenity(Amenities amenity, Pageable pageable){
		return roomRepository.findAllByAmenitiesList_Amenity(amenity,pageable);
	}
	/**
	 * Gets all Rooms with the given Occupation status.
	 * @param isOccupied is the room Occupied or not
	 * @param pageable the page information.
	 * @return List<Room> of all rooms that are the given occupation status.
	 */
	public Page<Room> findAllByIsOccupied(boolean isOccupied,Pageable pageable){
		return roomRepository.findAllByIsOccupied(isOccupied, pageable);
	}
}
