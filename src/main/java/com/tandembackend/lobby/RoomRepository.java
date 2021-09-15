package com.tandembackend.lobby;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {
    @Query("SELECT r FROM Room r")
    Iterable<Room> getAllRooms();

    Optional<Room> findRoomByName (String roomName);
}
