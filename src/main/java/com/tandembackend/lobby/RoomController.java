package com.tandembackend.lobby;

import com.tandembackend.dto.RoomActionSuccessDTO;
import com.tandembackend.dto.RoomInformationDTO;
import com.tandembackend.dto.RoomSuccessDTO;
import com.tandembackend.exception.RoomNameTooShortException;
import com.tandembackend.exception.RoomNotFoundException;
import com.tandembackend.exception.RoomnameTakenException;
import com.tandembackend.game.PlayerService;
import com.tandembackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RoomController {
    private RoomService roomService;
    private UserService userService;
    private PlayerService playerService;

    @Autowired
    public RoomController(RoomService roomService, UserService userService, PlayerService playerService) {
        this.roomService = roomService;
        this.userService = userService;
        this.playerService = playerService;
    }

    @GetMapping(path="/lobby")
    public @ResponseBody Iterable<RoomInformationDTO> getAllRooms() {
        Iterable<Room> rooms = roomService.getAllRooms();
        List<RoomInformationDTO> result = new ArrayList<>();
        for(Room room : rooms) {
            String roomName = room.getName();
            int userCount = userService.usersInRoom(room).size();
            int playerCount = playerService.getPlayersInRoom(room).size();
            result.add(new RoomInformationDTO(roomName, playerCount, userCount - playerCount));
        }
        return result;
    }

    @PostMapping(path="/lobby/create")
    public @ResponseBody ResponseEntity<RoomSuccessDTO>
    createRoom(@RequestParam("room") String roomName, Principal principal) throws RoomnameTakenException, RoomNameTooShortException {
        return ResponseEntity.ok(new RoomActionSuccessDTO(
                roomService.createRoom(roomName, userService.getUserFromPrincipal(principal)),
                "created"
        ));
    }

    @PostMapping(path="/lobby/join")
    public @ResponseBody ResponseEntity<RoomActionSuccessDTO>
    joinRoom(@RequestParam("room") String roomName, Principal principal) throws RoomNotFoundException {
        return ResponseEntity.ok(new RoomActionSuccessDTO(roomService.joinRoom(
                roomService.getRoomByName(roomName),
                userService.getUserFromPrincipal(principal)),
                "joined"
        ));
    }

    @PostMapping(path="/lobby/leave")
    public @ResponseBody ResponseEntity<RoomSuccessDTO>
    leaveRoom(@RequestParam("room") String roomName, Principal principal) throws RoomNotFoundException {
        Optional<Room> leftRoom = roomService.leaveRoom(userService.getUserFromPrincipal(principal), roomService.getRoomByName(roomName));
        if (leftRoom.isEmpty()) {
            return ResponseEntity.ok(new RoomSuccessDTO("no action"));
        } else {
            return ResponseEntity.ok(new RoomActionSuccessDTO(leftRoom.get(), "left"));
        }
    }
}
