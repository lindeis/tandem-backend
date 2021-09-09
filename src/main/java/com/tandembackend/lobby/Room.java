package com.tandembackend.lobby;

import com.tandembackend.game.Player;
import com.tandembackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    private String name;

    @OneToMany(mappedBy = "currentRoom", fetch = FetchType.LAZY)
    private Set<User> users;    // Any user currently in the room, i.e. spectators included

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Player> players;    // The 4 users actually playing, i.e. spectators excluded

    public Room(String name) {
        this.name = name;
    }
}
