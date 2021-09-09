package com.tandembackend.game;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class PlayerId implements Serializable {
    private String room;
    private TablePosition position;
}
