package com.tandembackend.game;

import com.tandembackend.lobby.Room;
import com.tandembackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@IdClass(PlayerId.class)
public class Player implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomname")
    private Room room;

    @Id
    @Enumerated(EnumType.STRING)
    private TablePosition position;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    public Player(Room room, TablePosition position, User user) {
        this.room = room;
        this.position = position;
        this.user = user;
    }
}
