package com.tandembackend.dto;

import com.tandembackend.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInformationDTO {
    private String username;
    public UserInformationDTO(User user) {
        username = user.getUsername();
    }
}
