package com.tandembackend.game;

import com.tandembackend.lobby.Room;
import com.tandembackend.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, PlayerId> {
    Optional<Player> findPlayerByUser(User user);
    Optional<Player> findPlayerByRoomAndPosition(Room room, TablePosition position);
    Iterable<Player> findPlayerByRoom(Room room);
}
