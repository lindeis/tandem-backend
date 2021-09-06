package com.tandembackend.lobby;

import com.tandembackend.exception.ForbiddenRoomClosingException;
import com.tandembackend.exception.RoomNotFoundException;
import com.tandembackend.exception.RoomnameTakenException;
import com.tandembackend.exception.UserAlreadyOwnerException;
import com.tandembackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping(path="/lobby", params="create")
    public @ResponseBody ResponseEntity<RoomSuccessDTO>
    createRoom(@RequestParam("create") String roomName, Principal principal) throws UsernameNotFoundException, UserAlreadyOwnerException, RoomnameTakenException {
        return ResponseEntity.ok(new RoomSuccessDTO(
                roomService.createRoom(roomName, userService.getUserFromPrincipal(principal)),
                "created"
        ));
    }

    @PostMapping(path="/lobby", params="close")
    public @ResponseBody ResponseEntity<RoomSuccessDTO>
    closeRoom(@RequestParam("close") String roomName, Principal principal) throws RoomNotFoundException, ForbiddenRoomClosingException {
        return ResponseEntity.ok(new RoomSuccessDTO(
                roomService.closeRoom(roomName, userService.getUserFromPrincipal(principal)),
                "left" // has the same functionality as "leave" currently
        ));
    }

    @PostMapping(path="/lobby", params="join")
    public @ResponseBody ResponseEntity<RoomSuccessDTO>
    joinRoom(@RequestParam("join") String roomName, Principal principal) throws RoomNotFoundException {
        return ResponseEntity.ok(new RoomSuccessDTO(
                roomService.joinRoom(roomName, userService.getUserFromPrincipal(principal)),
                "joined"
        ));
    }

    @PostMapping(path="/lobby", params="leave")
    public @ResponseBody ResponseEntity<RoomSuccessDTO>
    leaveRoom(@RequestParam("leave") String roomName, Principal principal) throws RoomNotFoundException {
        return ResponseEntity.ok(new RoomSuccessDTO(
                roomService.leaveRoom(roomName, userService.getUserFromPrincipal(principal)),
                "left"
        ));
    }
}
