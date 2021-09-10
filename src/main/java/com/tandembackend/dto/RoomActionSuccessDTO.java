package com.tandembackend.dto;

import com.tandembackend.lobby.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomActionSuccessDTO extends RoomSuccessDTO{
    private String roomName;
    public RoomActionSuccessDTO(Room room, String action) {
        super(action);
        this.roomName = room.getName();
    }
}
