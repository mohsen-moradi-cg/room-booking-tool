package com.RoomBookingTool.Server.controllers;

import com.RoomBookingTool.Server.models.Booking;
import com.RoomBookingTool.Server.models.Room;
import com.RoomBookingTool.Server.repositories.BookingJpaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/room/book")
public class BookingController {
    @Autowired
    private BookingJpaRepository bookingJpaRepository;

    @GetMapping
    public ResponseEntity<List<Booking>> getListOfBookings() {
        try {
            List<Booking> bookings = bookingJpaRepository.findAll();
            return new ResponseEntity( bookings, HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong, please try again later.", HttpStatus.BAD_REQUEST );
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Booking> createBooking(@RequestBody final Booking booking){
        try {
            booking.setCreatedAt(LocalDateTime.now());
            Booking newBooking = bookingJpaRepository.saveAndFlush(booking);
            return new ResponseEntity( newBooking, HttpStatus.CREATED );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong, please try again later.", HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping
    @RequestMapping("{bookingId}")
    public ResponseEntity<Optional<Booking>> getBookingById(@PathVariable Long bookingId) {
        try {
            Optional<Booking> booking = bookingJpaRepository.findById(bookingId);
            return new ResponseEntity( booking, HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong, please try again later.", HttpStatus.BAD_REQUEST );
        }
    }

    @RequestMapping(value = "{bookingId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBookingByBookingId(@PathVariable long bookingId) {
        try {
            bookingJpaRepository.deleteById(bookingId);
            return new ResponseEntity( "Booking with this '" + bookingId + "' been deleted.", HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong, please try again later.", HttpStatus.BAD_REQUEST );
        }

    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteByStartAndEndTimeAndSpeaker(@RequestBody final Booking booking) {
        try {
            LocalDateTime startTime = booking.getStartTime();
            LocalDateTime endTime = booking.getEndTime();
            String speaker = booking.getSpeaker();
            Booking lookupBooking =  bookingJpaRepository.findByStartTimeAndEndTimeAndSpeaker(startTime,endTime,speaker);
            Long bookingId = lookupBooking.getBookingId();
            bookingJpaRepository.deleteById(bookingId);
            return new ResponseEntity( "Booking with this '" + lookupBooking.getBookingName() + "' been removed from database.", HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong or booking does not exist.", HttpStatus.NOT_FOUND );
        }
    }


    @RequestMapping(value = "{bookingId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBookingByBookingId(
            @RequestBody final Booking booking,
            @PathVariable long bookingId
    ) {
        try {
            Booking lookupBooking = bookingJpaRepository.getOne(bookingId);
            BeanUtils.copyProperties(booking, lookupBooking, "bookingId");
            bookingJpaRepository.saveAndFlush(lookupBooking);
            return new ResponseEntity( "Booking with this '" + booking.getBookingName() + "' been updated.", HttpStatus.OK );
        }catch (final Exception e){
            return new ResponseEntity( "Something went wrong, please try again later.", HttpStatus.BAD_REQUEST );
        }
    }
}
