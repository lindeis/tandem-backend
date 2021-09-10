package com.tandembackend.dto;

public class PlayerSuccessDTO extends MessageDTO{
    public PlayerSuccessDTO(String action) {
        this.message = action;
    }
}
