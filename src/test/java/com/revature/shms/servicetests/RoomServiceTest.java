package com.revature.shms.servicetests;

import com.revature.shms.enums.Amenities;
import com.revature.shms.enums.CleaningStatus;
import com.revature.shms.enums.WorkStatus;
import com.revature.shms.models.AmenityWrapper;
import com.revature.shms.models.Room;
import com.revature.shms.repositories.RoomRepository;
import com.revature.shms.services.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
	@Mock RoomRepository roomRepository;
	@InjectMocks RoomService roomService;

	@Test
	public void addRoomTest(){
		Room room = new Room();
		when(roomRepository.save(any())).thenReturn(room);
		assertEquals(room, roomService.addRoom(room));
	}

	@Test
	public void addRoomsTest(){
		List<Room> rooms = new ArrayList<>();
		rooms.add(new Room());
		rooms.add(new Room());
		rooms.add(new Room());
		rooms.add(new Room());
		rooms.add(new Room());
		rooms.add(new Room());
		when(roomRepository.saveAll(any())).thenReturn(rooms);
		assertEquals(rooms, roomService.addRooms(rooms));
	}

	@Test
	public void isAvailableTestTrue(){
		Room room = new Room();
		room.setStatus(CleaningStatus.CLEAN);
		room.setWorkStatus(WorkStatus.NO_ISSUES);
		room.setOccupied(false);
		assertTrue(roomService.isAvailable(room));
	}

	@Test
	public void isAvailableTestWrongCleaning(){
		Room room = new Room();
		room.setStatus(CleaningStatus.NOT_SCHEDULED);
		room.setWorkStatus(WorkStatus.NO_ISSUES);
		room.setOccupied(false);
		assertFalse(roomService.isAvailable(room));
	}

	@Test
	public void isAvailableTestWrongWorking(){
		Room room = new Room();
		room.setStatus(CleaningStatus.CLEAN);
		room.setWorkStatus(WorkStatus.SCHEDULED);
		room.setOccupied(false);
		assertFalse(roomService.isAvailable(room));
	}

	@Test
	public void isAvailableTestWrongOccupied(){
		Room room = new Room();
		room.setStatus(CleaningStatus.CLEAN);
		room.setWorkStatus(WorkStatus.NO_ISSUES);
		room.setOccupied(true);
		assertFalse(roomService.isAvailable(room));
	}

	@Test
	public void findAllAvailableTest(){
		Room room = new Room();
		room.setStatus(CleaningStatus.CLEAN);
		room.setOccupied(false);
		room.setWorkStatus(WorkStatus.NO_ISSUES);
		List<Room> roomList = new ArrayList<>();
		roomList.add(room);
		roomList.add(room);
		Page<Room> roomPage = new PageImpl<>(roomList);
		when(roomRepository.findAllByStatusAndIsOccupiedAndWorkStatusOrderByRoomNumberDesc(any(), anyBoolean(), any(),any())).thenReturn(roomPage);
		assertEquals(roomList, roomService.findAllAvailable(null).getContent());
	}

	@Test
	public void setOccupationStatusTest() throws NotFound {
		int roomNumber = 1;
		boolean isOccupied = true;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		room.setOccupied(isOccupied);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		assertEquals(room, roomService.setOccupationStatus(roomNumber, isOccupied));
	}

	@Test
	public void getAllByIsOccupiedTest(){
		boolean testing = true;
		for(int i = 0; i < 2; i++){
			Room room = new Room();
			room.setOccupied(testing);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			Page<Room> roomPage = new PageImpl<>(roomList);
			when(roomRepository.findAllByIsOccupied(anyBoolean(),any())).thenReturn(roomPage);
			assertEquals(roomList, roomService.findAllByIsOccupied(testing,null).getContent());
			testing = false;
		}
	}

	@Test
	public void OccupiedTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		assertTrue(roomService.Occupied(roomNumber).isOccupied());
	}

	@Test
	public void notOccupiedTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		assertFalse(roomService.notOccupied(roomNumber).isOccupied());
	}

	@Test
	public void setRoomStatusTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		for (CleaningStatus cleaningStatus:CleaningStatus.values()) {
			assertEquals(cleaningStatus, roomService.setRoomStatus(roomNumber, cleaningStatus).getStatus());
		}
	}

	@Test
	public void scheduleCleaningTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		assertEquals(CleaningStatus.SCHEDULED, roomService.scheduleCleaning(roomNumber).getStatus());
	}

	@Test
	public void notScheduleCleaningTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		assertEquals(CleaningStatus.NOT_SCHEDULED, roomService.notScheduleCleaning(roomNumber).getStatus());
	}

	@Test
	public void startCleaningTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		assertEquals(CleaningStatus.IN_PROGRESS, roomService.startCleaning(roomNumber).getStatus());
	}

	@Test
	public void finishCleaningTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		assertEquals(CleaningStatus.CLEAN, roomService.finishCleaning(roomNumber).getStatus());
	}

	@Test
	public void getAllByStatusTest(){
		for(CleaningStatus cleaningStatus:CleaningStatus.values()) {
			Room room = new Room();
			room.setStatus(cleaningStatus);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			Page<Room> roomPage = new PageImpl<>(roomList);
			when(roomRepository.findAllByStatus(any(CleaningStatus.class),any())).thenReturn(roomPage);
			assertEquals(cleaningStatus, roomService.findAllByStatus(cleaningStatus, null).getContent().get(0).getStatus());
		}
	}

	@Test
	public void findAllByNotStatusTest(){
		for(CleaningStatus cleaningStatus:CleaningStatus.values()) {
			Room room = new Room();
			room.setStatus(cleaningStatus);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			Page<Room> roomPage = new PageImpl<>(roomList);
			when(roomRepository.findAllByStatusNot(any(CleaningStatus.class),any())).thenReturn(roomPage);
			assertEquals(cleaningStatus, roomService.findAllByNotStatus(cleaningStatus, null).getContent().get(0).getStatus());
		}
	}

	@Test
	public void setWorkStatusTest(){
		for(WorkStatus workStatus: WorkStatus.values()){
			assertEquals(workStatus, roomService.setWorkStatus(new Room(), workStatus).getWorkStatus());
		}
	}

	@Test
	public void getAllByWorkStatusTest(){
		for(WorkStatus workStatus: WorkStatus.values()){
			Room room = new Room();
			room.setWorkStatus(workStatus);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			Page<Room> roomPage = new PageImpl<>(roomList);
			when(roomRepository.findAllByWorkStatus(any(),any())).thenReturn(roomPage);
			assertEquals(roomList, roomService.findAllByWorkStatus(workStatus, null).getContent());
		}
	}

	@Test
	public void findAllByNotWorkStatusTest(){
		for(WorkStatus workStatus: WorkStatus.values()){
			Room room = new Room();
			room.setWorkStatus(workStatus);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			Page<Room> roomPage = new PageImpl<>(roomList);
			when(roomRepository.findAllByWorkStatusNot(any(),any())).thenReturn(roomPage);
			assertEquals(roomList, roomService.findAllByNotWorkStatus(workStatus, null).getContent());
		}
	}

	@Test
	public void startWorkingTest(){
		assertEquals(WorkStatus.IN_PROGRESS, roomService.startWorking(new Room()).getWorkStatus());
	}

	@Test
	public void finishWorkingTest(){
		assertEquals(WorkStatus.NO_ISSUES, roomService.finishWorking(new Room()).getWorkStatus());
	}

	@Test
	public void scheduleWorkingTest(){
		assertEquals(WorkStatus.SCHEDULED, roomService.scheduleWorking(new Room()).getWorkStatus());
	}

	@Test
	public void notScheduleWorkingTest(){
		assertEquals(WorkStatus.NOT_SCHEDULED, roomService.notScheduleWorking(new Room()).getWorkStatus());
	}

	@Test
	public void getAllRoomsTest(){
		List<Room> roomList = new ArrayList<>();
		roomList.add(new Room());
		roomList.add(new Room());
		Page<Room> roomPage = new PageImpl<>(roomList);
		when(roomRepository.findAllByOrderByRoomNumberDesc(any())).thenReturn(roomPage);
		assertEquals(roomList, roomService.findAllRooms(null ).getContent());
	}

	@Test
	public void getAllByAmenityTest(){
		for(Amenities amenities:Amenities.values()) {
			AmenityWrapper amenityWrapper = new AmenityWrapper();
			amenityWrapper.setAmenity(amenities);
			List<AmenityWrapper> amenityWrapperList = new ArrayList<>();
			amenityWrapperList.add(amenityWrapper);

			Room room = new Room();
			room.setAmenitiesList(amenityWrapperList);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			Page<Room> roomPage = new PageImpl<>(roomList);
			when(roomRepository.findAllByAmenitiesList_Amenity(amenities, null)).thenReturn(roomPage);
			assertEquals(roomList, roomService.findAllByAmenity(amenities, null).getContent());
		}
	}

	@Test
	public void getByRoomNumberTest() throws NotFound {
		int roomNumber = 102;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(roomNumber)).thenReturn(java.util.Optional.of(room));
		assertEquals(room, roomService.findByRoomNumber(roomNumber));
	}

	@Test
	public void getterAndSetterTest(){
		RoomRepository roomRepository = null;
		RoomService roomService = new RoomService();
		roomService.setRoomRepository(roomRepository);
		assertNull(roomService.getRoomRepository());

		roomService.setRoomRepository(null);
		assertNull(roomService.getRoomRepository());
	}
}
