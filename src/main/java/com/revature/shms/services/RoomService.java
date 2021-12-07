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
import org.springframework.security.core.parameters.P;
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
	 * @return boolean of if the room is avaliable or not.
	 */
	public boolean isAvailable(Room room){
		return room.getStatus().equals(CleaningStatus.CLEAN) && room.getWorkStatus().equals(WorkStatus.NO_ISSUES)
				&& !room.isOccupied();
	}

	public Page<Room> findAllAvailable(Pageable pageable){
		return roomRepository.findAllByStatusAndIsOccupiedAndWorkStatusOrderByRoomNumberDesc(
				CleaningStatus.CLEAN, false, WorkStatus.NO_ISSUES,pageable);
	}

	// --- isOccupied ---
	/**
	 * Sets the room Occupation status of the given room to the given status.
	 * @param roomNumber the room to be worked on by room number.
	 * @param isOccupied is the room occupied or not.
	 * @return the room with the new occupation status.
	 */
	public Room setOccupationStatus(int roomNumber, boolean isOccupied) throws NotFound {
		Room room = findByRoomNumber(roomNumber);
		room.setOccupied(isOccupied);
		return room;
	}

	/**
	 * Gets all Rooms with the given Occupation status.
	 * @param isOccupied is the room Occupied or not
	 * @return List<Room> of all rooms that are the given occupation status.
	 */
	public Page<Room> findAllByIsOccupied(boolean isOccupied,Pageable pageable){
		return roomRepository.findAllByIsOccupied(isOccupied, pageable);
	}

	/**
	 * Set the room Occupation status to true, aka. Occupied.
	 * @param roomNumber the room to be worked on by room number.
	 * @return the room with the occupation status set to true.
	 */
	public Room Occupied(int roomNumber) throws NotFound {
		return setOccupationStatus(roomNumber, true);
	}

	/**
	 * Set the room Occupation status to false, aka. not Occupied.
	 * @param roomNumber the room to be worked on by room number.
	 * @return the room with the occupation status set to false.
	 */
	public Room notOccupied(int roomNumber) throws NotFound {
		return setOccupationStatus(roomNumber, false);
	}

	// --- isClean portion ---
	/**
	 * Sets the room cleaning status of the given room to the given status.
	 * @param roomNumber the room to be worked on by room number.
	 * @param cleaningStatus the status of the cleaning.
	 * @return Room with the current cleaning status.
	 */
	public Room setRoomStatus(int roomNumber, CleaningStatus cleaningStatus) throws NotFound {
		Room room = findByRoomNumber(roomNumber);
		room.setStatus(cleaningStatus);
		return room;
	}

	/**
	 * Sets the room cleaning status to being Scheduled.
	 * @param roomNumber the room to be worked on by room number.
	 * @return Room with a scheduled cleaning status.
	 */
	public Room scheduleCleaning(int roomNumber) throws NotFound {
		return setRoomStatus(roomNumber, CleaningStatus.SCHEDULED);
	}

	/**
	 * Sets the room cleaning status to Not Scheduled.
	 * @param roomNumber the room to be worked on by room number.
	 * @return Room with no scheduled cleaning.
	 */
	public Room notScheduleCleaning(int roomNumber) throws NotFound {
		return setRoomStatus(roomNumber, CleaningStatus.NOT_SCHEDULED);
	}

	/**
	 * Sets the room cleaning status to In Progress.
	 * @param roomNumber the room to be worked on by room number.
	 * @return Room with an in progress cleaning status.
	 */
	public Room startCleaning(int roomNumber) throws NotFound {
		return setRoomStatus(roomNumber, CleaningStatus.IN_PROGRESS);
	}

	/**
	 * Sets the room cleaning status to Clean.
	 * @param roomNumber the room to be worked on by r
	 * @return Room with a clean cleaning Status.
	 */
	public Room finishCleaning(int roomNumber) throws NotFound {
		return setRoomStatus(roomNumber, CleaningStatus.CLEAN);
	}

	/**
	 * Gets all Rooms with the given Cleaning Status.
	 * @param status the Cleaning Status to be matched.
	 * @return List<Room> of all rooms with the given Cleaning Status.
	 */
	public Page<Room> findAllByStatus(CleaningStatus status, Pageable pageable){
		return roomRepository.findAllByStatus(status,pageable);
	}

	/**
	 * Gets all Rooms without the given Cleaning status.
	 * @param status the Cleaning Status to NOT be matched.
	 * @return List<Room> of all rooms without the given Cleaning Status.
	 */
	public Page<Room> findAllByNotStatus(CleaningStatus status, Pageable pageable){
		return roomRepository.findAllByStatusNot(status,pageable);
	}

	// --- WorkStatus ---
	/**
	 * Set the room with the given work status.
	 * @param room the room to be worked on.
	 * @param workStatus the status of the work on the room.
	 * @return the room with the selected work status.
	 */
	public Room setWorkStatus(Room room, WorkStatus workStatus){
		room.setWorkStatus(workStatus);
		return room;
	}

	/**
	 * Gets all Rooms with the given Needs Service status.
	 * @param workStatus the Work Status to be matched.
	 * @return List<Room> of all rooms that are the given Needs Service status.
	 */
	public Page<Room> findAllByWorkStatus(WorkStatus workStatus,Pageable pageable){
		return roomRepository.findAllByWorkStatus(workStatus,pageable);
	}

	/**
	 * Gets all Rooms without the given Work Status.
	 * @param workStatus the Work Status to NOT be matched.
	 * @return List<Room> of all rooms without the given Work Status.
	 */
	public Page<Room> findAllByNotWorkStatus(WorkStatus workStatus,Pageable pageable){
		return roomRepository.findAllByWorkStatusNot(workStatus,pageable);
	}

	/**
	 * Sets the Work Status of the given room to In Progress.
	 * @param room the room to be worked on.
	 * @return the room with an In Progress work status.
	 */
	public Room startWorking(Room room){
		return setWorkStatus(room, WorkStatus.IN_PROGRESS);
	}

	/**
	 * Sets the Work Status of the given room to Scheduled.
	 * @param room the room to be worked on.
	 * @return the room with a Scheduled work status.
	 */
	public Room scheduleWorking(Room room){
		return setWorkStatus(room, WorkStatus.SCHEDULED);
	}

	/**
	 * Sets the Work Status of the given room to Not Scheduled.
	 * @param room the room to be worked on.
	 * @return the room with a Not Scheduled work status.
	 */
	public Room notScheduleWorking(Room room){
		return setWorkStatus(room, WorkStatus.NOT_SCHEDULED);
	}

	/**
	 * Sets the Work Status of the given room to No Issues.
	 * @param room the room to be worked on.
	 * @return the room with a No Issues work status.
	 */
	public Room finishWorking(Room room){
		return setWorkStatus(room, WorkStatus.NO_ISSUES);
	}

	// --- Finds ---
	/**
	 * @return List<Room> Ordered by Room Number in descending order.
	 */
	public Page<Room> findAllRooms(Pageable pageable){
		return roomRepository.findAllByOrderByRoomNumberDesc(pageable);
	}

	/**
	 * Gets all Rooms with the given Amenity.
	 * @param amenity the Amenity to be matched.
	 * @return List<Room> of all rooms that have the given Amenity.
	 */
	public Page<Room> findAllByAmenity(Amenities amenity, Pageable pageable){
		return roomRepository.findAllByAmenitiesList_Amenity(amenity,pageable);
	}

	/**
	 * A Room with the given Room Number.
	 * @param roomNumber the Room Number to be matched.
	 * @return Room with the given Room Number.
	 * @throws NotFound if the room number does not exist.
	 */
	public Room findByRoomNumber(int roomNumber) throws NotFound {
		return roomRepository.findByRoomNumber(roomNumber).orElseThrow(NotFound::new);
	}
}
