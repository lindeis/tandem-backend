package com.tandembackend.dto;

import com.tandembackend.game.TablePosition;
import com.tandembackend.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class TableInformationDTO {
    private Map<String, String> players; // Key: Table position. Value: Username.

    public TableInformationDTO(Map<TablePosition, User> players) {
        this.players = new HashMap<String, String>();
        for (Map.Entry<TablePosition, User> entry : players.entrySet()) {
            this.players.put(entry.getKey().toString(), entry.getValue().getUsername());
        }
    }
}
