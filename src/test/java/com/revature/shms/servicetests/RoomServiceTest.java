package com.revature.shms.servicetests;

import com.revature.shms.enums.Amenities;
import com.revature.shms.models.AmenityWrapper;
import com.revature.shms.models.Room;
import com.revature.shms.repositories.RoomRepository;
import com.revature.shms.services.RoomService;
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


	// --- isOccupied ---
	@Test
	public void setOccupationStatusTest() throws NotFound {
		int roomNumber = 1;
		boolean isOccupied = true;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		room.setOccupied(isOccupied);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		when(roomRepository.save(any())).thenReturn(room);
		assertFalse(roomService.setOccupationStatus(roomNumber, false).isOccupied());
	}

	@Test
	public void OccupiedTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		when(roomRepository.save(any())).thenReturn(room);
		assertTrue(roomService.Occupied(roomNumber).isOccupied());
	}
	@Test
	public void notOccupiedTest() throws NotFound {
		int roomNumber = 1;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(anyInt())).thenReturn(java.util.Optional.of(room));
		when(roomRepository.save(any())).thenReturn(room);
		assertFalse(roomService.notOccupied(roomNumber).isOccupied());
	}

	@Test
	public void findAllRoomsTest(){
		List<Room> roomList = new ArrayList<>();
		roomList.add(new Room());
		roomList.add(new Room());
		Page<Room> roomPage = new PageImpl<>(roomList);
		when(roomRepository.findAllByOrderByRoomNumberDesc(any())).thenReturn(roomPage);
		assertEquals(roomList, roomService.findAllRooms(null ).getContent());
	}
	@Test
	public void findByRoomNumberTest() throws NotFound {
		int roomNumber = 102;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(roomNumber)).thenReturn(java.util.Optional.of(room));
		assertEquals(room, roomService.findByRoomNumber(roomNumber));
	}
	@Test
	public void findAllByAmenityTest(){
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
	public void findAllByIsOccupiedTest(){
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
	public void getterAndSetterTest(){
		RoomRepository roomRepository = null;
		RoomService roomService = new RoomService();
		roomService.setRoomRepository(roomRepository);
		assertNull(roomService.getRoomRepository());

		roomService.setRoomRepository(null);
		assertNull(roomService.getRoomRepository());
	}
}
