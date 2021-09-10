package com.tandembackend.dto;

import com.tandembackend.game.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerActionSuccessDTO extends PlayerSuccessDTO{
    private String roomName;
    private String position;

    public PlayerActionSuccessDTO(Player player, String action) {
        super(action);
        roomName = player.getRoom().getName();
        position = player.getPosition().toString();
    }

}
