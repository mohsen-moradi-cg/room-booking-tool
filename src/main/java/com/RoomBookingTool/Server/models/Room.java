package com.RoomBookingTool.Server.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;
    private String name;
    private int seats;
    private String[] facilities;
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "roomBookings",
            joinColumns = @JoinColumn(name = "roomId")
    )
    private List<Booking> bookings;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "facility",
            joinColumns = @JoinColumn(name = "roomId")
    )
    private List<Facility> facility;


    public Room() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String[] getFacilities() {
        return facilities;
    }

    public void setFacilities(String[] facilities) {
        this.facilities = facilities;
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
