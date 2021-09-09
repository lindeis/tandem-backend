package com.tandembackend.game;

import com.tandembackend.exception.InvalidTablePositionException;
import com.tandembackend.exception.PositionTakenException;
import com.tandembackend.exception.RoomNotFoundException;
import com.tandembackend.lobby.RoomService;
import com.tandembackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class PlayerController {
    private PlayerService playerService;
    private UserService userService;
    private RoomService roomService;

    @Autowired
    public PlayerController(PlayerService playerService, UserService userService, RoomService roomService) {
        this.playerService = playerService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @PostMapping(path="/room", params="sit")
    public @ResponseBody ResponseEntity<PlayerSuccessDTO>
    takePosition(@RequestParam("name") String roomName, @RequestParam("sit") String position,  Principal principal) throws RoomNotFoundException, PositionTakenException, InvalidTablePositionException {
        return ResponseEntity.ok(new PlayerSuccessDTO(playerService.takePosition(
                roomService.getRoomByName(roomName),
                TablePosition.fromString(position),
                userService.getUserFromPrincipal(principal)),
                "sit"
        ));
    }

    @PostMapping(path="/room", params="stand")
    public @ResponseBody ResponseEntity<PlayerSuccessDTO>
    leavePosition(Principal principal) throws RoomNotFoundException {
        Optional<Player> leftPosition = playerService.leavePosition(userService.getUserFromPrincipal(principal));
        if (leftPosition.isEmpty()) {
            return ResponseEntity.ok(new PlayerSuccessDTO("no action"));
        } else {
            return ResponseEntity.ok(new PlayerSuccessDTO((leftPosition.get()), "left"));
        }
    }
}
