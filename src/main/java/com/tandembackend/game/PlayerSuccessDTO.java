package com.tandembackend.game;

import com.tandembackend.lobby.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSuccessDTO {
    private String roomName;
    private String position;
    private String action;

    public PlayerSuccessDTO(Player player, String action) {
        roomName = player.getRoom().getName();
        position = player.getPosition().toString();
        this.action = action;
    }

    public PlayerSuccessDTO(String action) {
        this.action = action;
    }
}
