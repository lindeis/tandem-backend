package com.tandembackend.dto;

import com.tandembackend.user.User;
import lombok.Getter;
import lombok.Setter;
import org.owasp.encoder.Encode;

@Getter
@Setter
public class UserInformationDTO {
    private String username;
    public UserInformationDTO(User user) {
        username = Encode.forJavaScript(user.getUsername());
    }
}
