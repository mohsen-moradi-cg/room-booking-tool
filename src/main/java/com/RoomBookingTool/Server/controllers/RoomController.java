package com.RoomBookingTool.Server.controllers;

import com.RoomBookingTool.Server.models.Room;
import com.RoomBookingTool.Server.repositories.RoomJpaRepository;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomJpaRepository roomJpaRepository;

    // Returns all the rooms  "/api/rooms"
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        try {
            List<Room> rooms = roomJpaRepository.findAll();
            return new ResponseEntity( rooms, HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong.", HttpStatus.NOT_FOUND );
        }
    }

    // Find and returns a room using roomId "/api/rooms/{roomId}"
    @GetMapping
    @RequestMapping("{roomId}")
    public ResponseEntity<Optional<Room>> getRoomByRoomId(@PathVariable Long roomId) {
        try {
            Optional<Room> room = roomJpaRepository.findById(roomId);
            return new ResponseEntity( room, HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong.", HttpStatus.NOT_FOUND );
        }
    }

    // needs work
//    @RequestMapping(method = {RequestMethod.POST}, value = "/room")
//    public ResponseEntity<List<Room>> getRoomsByQuery(@RequestBody final Room room) {
//        try {
//            List<Room> rooms = roomJpaRepository.findBySeatsGreaterThanEqual(room.getSeats());
//            Gson gson = new Gson();
//            String jsonRoom = gson.toJson( room.getFacilities() );
//            Room[] filteredRooms = rooms.stream().filter(
//                    r -> {
//                        for(String f : r.getFacilities()) {
//                            System.out.println(jsonRoom + " ----- " + f);
//                            if(jsonRoom.contains(f)) {
//                                return true;
//                            }
//                            return false;
//                        }
//                        return false;
//                    }
//            ).toArray(Room[]::new);
//            List<Room> filteredRoomsList = Arrays.asList(filteredRooms);
//            return new ResponseEntity( filteredRoomsList, HttpStatus.OK );
//        }catch (final Exception e){
//            return new ResponseEntity( "Something went wrong, please try again later.", HttpStatus.BAD_REQUEST );
//        }
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(@RequestBody final Room room){
            room.setCreatedAt(LocalDateTime.now());
            return roomJpaRepository.saveAndFlush(room);
    }

   //  Create a room "/api/rooms"
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<Room> createRoom(@RequestBody final Room room){
//        try {
//            room.setCreatedAt(LocalDateTime.now());
//            Room newRoom = roomJpaRepository.saveAndFlush(room);
//            return new ResponseEntity( newRoom, HttpStatus.CREATED );
//        }catch (final Exception e){
//            return new ResponseEntity( e.getMessage(), HttpStatus.NOT_FOUND );
//        }
//    }

    // Delete a room by roomId
    @RequestMapping( value = "{roomId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRoomById(@PathVariable Long roomId) {
        try {
            roomJpaRepository.deleteById(roomId);
            return new ResponseEntity( "Room with this '" + roomId + "' been removed.", HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong.", HttpStatus.NOT_FOUND );
        }
    }

    // update a room by roomId
    @RequestMapping( value = "{roomId}", method = RequestMethod.PUT)
    public ResponseEntity<Room> updateRoom(@PathVariable Long roomId, @RequestBody Room room) {
        try {
            Room existingRoom = roomJpaRepository.getOne(roomId);
            BeanUtils.copyProperties(room, existingRoom, "roomId");
            Room updatedRoom = roomJpaRepository.saveAndFlush(existingRoom);
            return new ResponseEntity( "Room with this '" + updatedRoom.getRoomName() + "' been updated.", HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong.", HttpStatus.NOT_FOUND );
        }
    }

}
