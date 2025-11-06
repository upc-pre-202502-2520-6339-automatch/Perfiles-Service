package automach.profiles.domain.repository;


import automach.profiles.domain.model.aggregates.User;
import automach.profiles.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    List<User> findAll();
    void deleteById(UserId id);
}