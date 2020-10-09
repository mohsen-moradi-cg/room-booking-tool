package com.RoomBookingTool.Server.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;
    private String roomName;
    private int seats;
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "roomBookings",
            joinColumns = @JoinColumn(name = "roomId")
    )
    private List<Booking> bookings;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "roomFacilities",
            joinColumns = @JoinColumn(name = "roomId")
    )
    private List<Facility> facility;


    public Room() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Facility> getFacility() {
        return facility;
    }

    public void setFacility(List<Facility> facility) {
        this.facility = facility;
    }
}
