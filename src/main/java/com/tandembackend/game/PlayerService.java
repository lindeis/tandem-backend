package com.tandembackend.game;

import com.tandembackend.exception.PositionTakenException;
import com.tandembackend.exception.RoomNotFoundException;
import com.tandembackend.lobby.Room;
import com.tandembackend.lobby.RoomService;
import com.tandembackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    private RoomService roomService;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, @Lazy RoomService roomService) {
        this.playerRepository = playerRepository;
        this.roomService = roomService;
    }

    public Player takePosition(Room room, TablePosition position, User user) throws PositionTakenException, RoomNotFoundException {
        // Check if requested position is free to take
        Optional<Player> requestedPosition = playerRepository.findPlayerByRoomAndPosition(room, position);
        if (!requestedPosition.isEmpty()) {
            // If already sitting at the requested position, don't do anything
            if (requestedPosition.get().getUser() == user) {
                return requestedPosition.get();
            }
            throw new PositionTakenException("This seat is already taken.");
        }

        // Stand up from previous position, if there was one
        leavePosition(user);

        // Change rooms if necessary
        roomService.joinRoom(room, user);

        // Take position
        Player player = new Player(room, position, user);
        return playerRepository.save(player);
    }

    public Optional<Player> leavePosition(User user) {
        Optional<Player> previousPosition = playerRepository.findPlayerByUser(user);
        if (!previousPosition.isEmpty()) {
            playerRepository.delete(previousPosition.get());
        }
        return previousPosition;
    }
}
