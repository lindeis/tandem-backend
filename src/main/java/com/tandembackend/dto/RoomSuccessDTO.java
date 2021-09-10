package com.tandembackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomSuccessDTO extends MessageDTO{
    public RoomSuccessDTO(String action) {
        this.message = action;
    }
}