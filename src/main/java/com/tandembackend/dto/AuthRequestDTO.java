package com.tandembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    @Size(min = 3, max = 255, message = "The username must be between 3 and 255 characters.")
    @Pattern(regexp = "[a-zA-Z0-9_.]+", message = "The username may only contain alphanumerical characters, underscores and periods.")
    private String username;
    @Size(min = 5, max = 255, message = "The password must be between 5 and 255 characters long.")
    @Pattern(regexp = "[a-zA-Z0-9 !\"#$%&'()*+,-./:;<=>?@^_`{|}~]+",
    message = "The password may only contain alphanumerical characters, spaces and the following special characters: !\"#$%&'()*+,-./:;<=>?@^_`{|}~")
    private String password;
}
