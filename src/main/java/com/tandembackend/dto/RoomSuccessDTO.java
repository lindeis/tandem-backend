package com.tandembackend.dto;

import com.tandembackend.lobby.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomSuccessDTO extends MessageDTO{
    private String roomName;

    public RoomSuccessDTO(Room room, String action) {
        roomName = room.getName();
        this.message = action;
    }
}