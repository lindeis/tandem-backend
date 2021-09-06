package com.tandembackend.user;

import com.tandembackend.lobby.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByUsername(String username);
    List<User> findByCurrentRoom(Room room);
}
