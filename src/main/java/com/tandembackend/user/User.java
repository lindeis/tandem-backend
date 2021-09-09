package com.tandembackend.user;

import com.tandembackend.lobby.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomname")
    private Room currentRoom;
    private boolean ownsRoom = false;

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
