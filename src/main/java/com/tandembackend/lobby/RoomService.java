package com.tandembackend.lobby;

import com.tandembackend.exception.RoomNameTooShortException;
import com.tandembackend.exception.RoomNotFoundException;
import com.tandembackend.exception.RoomnameTakenException;
import com.tandembackend.game.PlayerService;
import com.tandembackend.user.User;
import com.tandembackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private PlayerService playerService;

    @Autowired
    public RoomService(RoomRepository roomRepository, UserRepository userRepository, @Lazy PlayerService playerService) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.playerService = playerService;
    }

    public Iterable<Room> getAllRooms() {
        return roomRepository.getAllRooms();
    }

    public Room createRoom(String roomName, User user) throws RoomnameTakenException, RoomNameTooShortException {
        // Check whether room name already exists
        if (!roomRepository.findRoomByName(roomName).isEmpty()) {
            throw new RoomnameTakenException("The room name " + roomName + " is already taken.");
        }

        // Check whether the user entered a valid room name
        checkIfRoomNameValid(roomName);

        // Create and save the new room
        Room room = new Room(roomName);
        room = roomRepository.save(room);

        // Join new room
        joinRoom(room, user);

        return room;
    }

    public Room joinRoom(Room room, User user) {
        // If already in the room, do nothing
        if (user.getCurrentRoom() == room) {
            return room;
        }

        // Leave previous room, if any
        if (user.getCurrentRoom() != null) {
            leaveRoom(user, user.getCurrentRoom());
        }

        // Join new room and save user
        user.setCurrentRoom(room);
        userRepository.save(user);

        return room;
    }

    public Optional<Room> leaveRoom(User user, Room room) {
        // If not in the room, do nothing
        if (user.getCurrentRoom() != room) {
            return Optional.empty();
        }

        // Stand up from table
        playerService.leavePosition(user);

        // Leave room and save user
        user.setCurrentRoom(null);
        userRepository.save(user);

        // Delete room if no more users in it
        deleteRoomIfEmpty(room);

        return Optional.of(room);
    }

    public Room getRoomByName(String roomName) throws RoomNotFoundException {
        Optional<Room> optionalRoom = roomRepository.findRoomByName(roomName);
        if (optionalRoom.isEmpty()) {
            throw new RoomNotFoundException("Couldn't find a room named " + roomName + ".");
        }
        return optionalRoom.get();
    }

    private void deleteRoomIfEmpty(Room room) {
        if (userRepository.findByCurrentRoom(room).isEmpty()) {
            roomRepository.delete(room);
        }
    }

    private void checkIfRoomNameValid(String roomName) throws RoomNameTooShortException {
        if (roomName.length() < 3) {
            throw new RoomNameTooShortException("Your room name has to be at least 3 characters long.");
        }
    }
}
