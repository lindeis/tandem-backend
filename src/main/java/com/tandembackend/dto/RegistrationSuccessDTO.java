package com.tandembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.owasp.encoder.Encode;

@Getter
@Setter
public class RegistrationSuccessDTO {
    private String username;

    public RegistrationSuccessDTO(String username) {
        this.username = Encode.forHtml(username);
    }
}
