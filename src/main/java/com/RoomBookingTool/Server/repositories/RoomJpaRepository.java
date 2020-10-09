package com.RoomBookingTool.Server.repositories;

import com.RoomBookingTool.Server.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RoomJpaRepository extends JpaRepository<Room, Long> {
    @Query(
            value = "select * from rooms where find_in_set(:facilities, rooms.facilities) > 0",
            nativeQuery = true)
    List<Room> findByFacilities(@Param("facilities") String[] facilities);

    List<Room> findBySeatsGreaterThanEqual(int seats);
}
