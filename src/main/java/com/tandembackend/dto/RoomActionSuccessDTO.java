package com.tandembackend.dto;

import com.tandembackend.lobby.Room;
import lombok.Getter;
import lombok.Setter;
import org.owasp.encoder.Encode;

@Getter
@Setter
public class RoomActionSuccessDTO extends RoomSuccessDTO{
    private String roomName;
    public RoomActionSuccessDTO(Room room, String action) {
        super(action);
        this.roomName = Encode.forUriComponent(room.getName());
    }
}
