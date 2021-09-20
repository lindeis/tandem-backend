package com.tandembackend.game;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class PlayerId implements Serializable {
    private String room;
    private TablePosition position;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        final PlayerId other = (PlayerId) o;
        if (this.room.equals(other.room) && this.position == other.position) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, position);
    }
}
