package com.tandembackend.lobby;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {
    @Query("SELECT r.name FROM Room r")
    Iterable<String> getAllRoomNames();

    Optional<Room> findRoomByName (String roomName);
}
