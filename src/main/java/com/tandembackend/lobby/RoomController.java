package com.tandembackend.lobby;

import com.tandembackend.dto.RoomActionSuccessDTO;
import com.tandembackend.dto.RoomSuccessDTO;
import com.tandembackend.exception.RoomNotFoundException;
import com.tandembackend.exception.RoomnameTakenException;
import com.tandembackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
public class RoomController {
    private RoomService roomService;
    private UserService userService;

    @Autowired
    public RoomController(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @GetMapping(path="/lobby")
    public @ResponseBody Iterable<String> getAllRooms() {
        return roomService.getAllRoomNames();
    }

    @PostMapping(path="/lobby/create")
    public @ResponseBody ResponseEntity<RoomSuccessDTO>
    createRoom(@RequestParam("room") String roomName, Principal principal) throws RoomnameTakenException {
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
