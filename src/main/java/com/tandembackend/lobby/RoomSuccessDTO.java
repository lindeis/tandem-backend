package com.tandembackend.lobby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSuccessDTO {
    private String roomName;
    private String action;

    public RoomSuccessDTO(Room room, String action) {
        roomName = room.getName();
        this.action = action;
    }
}