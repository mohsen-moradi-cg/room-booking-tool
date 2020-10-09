package com.RoomBookingTool.Server;

import com.RoomBookingTool.Server.models.Booking;
import com.RoomBookingTool.Server.repositories.BookingJpaRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingJpaRepository bookingJpaRepository;




    private Booking bookingObject() {
        Booking booking = new Booking();
        booking.setBookingName("Wash up");
        booking.setRoomId((long) 1);
        booking.setRoomName("A");
        booking.setSpeaker("Mo");
        booking.setParticipants(new String[]{"Mo"});
        return booking;
    }
    private Map bookingObjectWithMap() {
        Map map = new HashMap();
        map.put("speaker", "Mo");
        map.put("startTime", "2020-11-10T15:30:00");
        map.put("endTime", "2020-11-10T16:00:00");
        map.put("bookingName", "Wash up");
        map.put("roomId", 1);
        map.put("roomName", "A");
        map.put("participants", new String[]{"Mo"});
        return map;
    }

    @Test
    public void testCreateBooking() throws Exception {
        Map booking = bookingObjectWithMap();
        createBooking(booking);
        bookingJpaRepository.deleteAll();
    }

    @Test
    public void testDeleteRoom() throws Exception {
        Booking booking = bookingObject();
        Booking newBooking =  bookingJpaRepository.saveAndFlush(booking);
        deleteBookingByBookingId(newBooking.getBookingId());
        bookingJpaRepository.deleteAll();
    }

    @Test
    public void testGetListOfBookings() throws Exception {
        Booking booking = bookingObject();
        bookingJpaRepository.saveAndFlush(booking);
        bookingJpaRepository.saveAndFlush(booking);
        bookingJpaRepository.saveAndFlush(booking);
        getListOfBookings();
        bookingJpaRepository.deleteAll();
    }

    @Test
    public void testGetBookingByBookingId() throws Exception {
        Booking booking = bookingObject();
        Booking newBooking =  bookingJpaRepository.saveAndFlush(booking);
        getBookingByBookingId(newBooking.getBookingId());
        bookingJpaRepository.deleteAll();
    }

    @Test
    public void testUpdateBookingByBookingId() throws Exception {
        Booking booking = bookingObject();
        Booking newBooking =  bookingJpaRepository.saveAndFlush(booking);
        updateBookingByBookingId(newBooking.getBookingId(), booking);
        bookingJpaRepository.deleteAll();
    }

    @Test
    public void testDeleteByStartAndEndTimeAndSpeaker() throws Exception {
        Map booking = bookingObjectWithMap();
        createBooking(booking);
        deleteByStartAndEndTimeAndSpeaker(booking);
        bookingJpaRepository.deleteAll();
    }

    public void createBooking( Map booking ) throws Exception {
        Gson gson = new Gson();
        String jsonBooking = gson.toJson( booking );
        mockMvc.perform(
                MockMvcRequestBuilders.post( "/api/room/book" )
                        .content( jsonBooking )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isCreated() ).andReturn();
    }

    public void deleteBookingByBookingId( Long bookingId ) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete( "/api/room/book/"+bookingId )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() ).andReturn();
    }

    public void getListOfBookings() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get( "/api/room/book" )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andReturn();
    }

    public void getBookingByBookingId(Long bookingId) throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get( "/api/room/book/"+bookingId )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andReturn();
    }

    public void updateBookingByBookingId(Long bookingId, Booking booking) throws Exception {
        Gson gson = new Gson();
        String jsonBooking = gson.toJson( booking );
        mockMvc.perform( MockMvcRequestBuilders.put( "/api/room/book/"+bookingId )
                .content( jsonBooking )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( content().string("Booking with this '" + booking.getBookingName() + "' been updated.") )
                .andReturn();
    }

    public void deleteByStartAndEndTimeAndSpeaker(Map booking) throws Exception {
        Gson gson = new Gson();
        String jsonBooking = gson.toJson( booking );
        mockMvc.perform( MockMvcRequestBuilders.delete( "/api/room/book")
                .content( jsonBooking )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( content().string("Booking with this 'Wash up' been removed from database.") )
                .andReturn();
    }

}
