package com.tandembackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreationRequestDTO {
    @Size(min = 3, max = 255, message = "The room name has to be between 3 and 255 characters long.")
    @Pattern(regexp = "[a-zA-Z0-9 _.]+", message = "The room name may only contain alphanumerical characters, spaces, underscores and periods.")
    private String name;
}
