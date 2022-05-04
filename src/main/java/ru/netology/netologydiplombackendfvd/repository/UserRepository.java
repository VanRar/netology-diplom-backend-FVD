package ru.netology.netologydiplombackendfvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.netologydiplombackendfvd.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
