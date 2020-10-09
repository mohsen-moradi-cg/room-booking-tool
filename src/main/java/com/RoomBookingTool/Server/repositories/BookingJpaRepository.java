package com.RoomBookingTool.Server.repositories;

import com.RoomBookingTool.Server.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingJpaRepository extends JpaRepository<Booking, Long> {
 Booking findByStartTimeAndEndTimeAndSpeaker(LocalDateTime startTime, LocalDateTime endTime, String speaker);
}
