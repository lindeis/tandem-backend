package com.tandembackend.lobby;

import com.tandembackend.exception.RoomNotFoundException;
import com.tandembackend.exception.RoomnameTakenException;
import com.tandembackend.exception.UserAlreadyOwnerException;
import com.tandembackend.game.PlayerService;
import com.tandembackend.user.User;
import com.tandembackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Iterable<String> getAllRoomNames() {
        return roomRepository.getAllRoomNames();
    }

    public Room createRoom(String roomName, User owner) throws UserAlreadyOwnerException, RoomnameTakenException {
        if (owner.isOwnsRoom()) {
            throw new UserAlreadyOwnerException("Close room " + owner.getCurrentRoom().getName() + " before trying to open a new one.");
        }
        if (!roomRepository.findRoomByName(roomName).isEmpty()) {
            throw new RoomnameTakenException("The room name " + roomName + " is already taken.");
        }
        if (owner.getCurrentRoom() != null) {
            leaveRoom(owner, owner.getCurrentRoom());
        }
        Room room = new Room(roomName);
        room = roomRepository.save(room);
        owner.setCurrentRoom(room);
        owner.setOwnsRoom(true);
        userRepository.save(owner);
        return room;
    }

    public Room joinRoom(String roomName, User user) throws RoomNotFoundException {
        Room room = getRoomByName(roomName);
        if (user.getCurrentRoom() == room) {
            return room;
        }
        if (user.getCurrentRoom() != null) {
            leaveRoom(user, user.getCurrentRoom());
        }
        user.setCurrentRoom(room);
        userRepository.save(user);
        return room;
    }

    public Room leaveRoom(String roomName, User user) throws RoomNotFoundException {
        Room room = getRoomByName(roomName);
        if (user.getCurrentRoom() == room) {
            leaveRoom(user, room);
        }
        return room;
    }

    /*  Makes the user leave the room.
        If the user was the last person in the room, the room gets deleted.
        If the user was the owner, a new owner is selected randomly.
        If the user is sitting at a table, the position is freed.
    */
    private void leaveRoom(User user, Room room) {
        if (user.getCurrentRoom() != room) {
            throw new IllegalArgumentException("User " + user.getUsername() + " trying to leave a room " + room.getName() + (
                    user.getCurrentRoom() == null ? ", but they are not in any room currently." : ", but they are in room " + user.getCurrentRoom().getName()));
        }
        playerService.leavePosition(user);
        boolean userOwnsRoom = user.isOwnsRoom();
        user.setOwnsRoom(false);
        user.setCurrentRoom(null);
        userRepository.save(user);
        if (userOwnsRoom) {
            List<User> usersInRoom = userRepository.findByCurrentRoom(room);
            if (usersInRoom.isEmpty()) {
                roomRepository.delete(room);
            } else {
                User newOwner = usersInRoom.get(0);
                newOwner.setOwnsRoom(true);
                userRepository.save(newOwner);
            }
        }
    }

    public Room getRoomByName(String roomName) throws RoomNotFoundException {
        Optional<Room> optionalRoom = roomRepository.findRoomByName(roomName);
        if (optionalRoom.isEmpty()) {
            throw new RoomNotFoundException("Couldn't find a room named " + roomName + ".");
        }
        return optionalRoom.get();
    }
}
