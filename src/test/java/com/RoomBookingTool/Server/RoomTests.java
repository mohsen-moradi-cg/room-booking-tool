package com.RoomBookingTool.Server;

import com.RoomBookingTool.Server.repositories.RoomJpaRepository;
import com.google.gson.Gson;
import com.RoomBookingTool.Server.models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    Room room = new Room();

    @Test
    public void testCreateRoom() throws Exception {
        room.setName("A");
        room.setSeats(6);
        createRoom(room);
        roomJpaRepository.deleteAll();
    }

    @Test
    public void testDeleteRoom() throws Exception {
        room.setName("A");
        room.setSeats(6);
        Room newRoom =  roomJpaRepository.saveAndFlush(room);
        deleteRoomByRoomId(newRoom.getRoomId());
        roomJpaRepository.deleteAll();
    }

    @Test
    public void testGetListOfRooms() throws Exception {
        room.setName("A");
        room.setSeats(6);
        roomJpaRepository.saveAndFlush(room);
        roomJpaRepository.saveAndFlush(room);
        roomJpaRepository.saveAndFlush(room);
        getListOfRooms();
        roomJpaRepository.deleteAll();
    }

    @Test
    public void testGetRoomByRoomId() throws Exception {
        room.setName("A");
        room.setSeats(6);
        Room newRoom =  roomJpaRepository.saveAndFlush(room);
        getRoomByRoomId(newRoom.getRoomId());
        roomJpaRepository.deleteAll();
    }

    public void createRoom( Room room ) throws Exception {
        Gson gson = new Gson();
        String jsonRoom = gson.toJson( room );
        mockMvc.perform(
                MockMvcRequestBuilders.post( "/api/rooms" )
                        .content( jsonRoom )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isCreated() ).andReturn();
    }

    public void deleteRoomByRoomId( Long roomId ) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete( "/api/rooms/"+roomId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() ).andReturn();
    }

    public void getListOfRooms() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get( "/api/rooms" )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andReturn();
    }

    public void getRoomByRoomId(Long roomId) throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get( "/api/rooms/"+roomId )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andReturn();
    }

}
