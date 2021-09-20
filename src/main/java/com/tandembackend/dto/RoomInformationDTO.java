package com.tandembackend.dto;

import lombok.Getter;
import lombok.Setter;
import org.owasp.encoder.Encode;

@Getter
@Setter
public class RoomInformationDTO {
    private String roomName;
    private int playerCount;
    private int spectatorCount;

    public RoomInformationDTO(String roomName, int playerCount, int spectatorCount) {
        this.roomName = Encode.forHtml(roomName);
        this.playerCount = playerCount;
        this.spectatorCount = spectatorCount;
    }
}
