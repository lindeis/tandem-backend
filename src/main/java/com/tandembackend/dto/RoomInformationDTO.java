package com.tandembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomInformationDTO {
    private String roomName;
    private int playerCount;
    private int spectatorCount;
}
