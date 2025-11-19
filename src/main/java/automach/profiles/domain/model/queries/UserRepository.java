package automach.profiles.domain.model.queries;

import automach.profiles.domain.model.aggregates.User;
import automach.profiles.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Port de repositorio para el agregado User.
 * La implementación concreta vive en infrastructure (UserRepositoryJpaAdapter).
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(UserId id);

    List<User> findAll();

    void deleteById(UserId id);

    Optional<User> findByIamUserId(Long iamUserId);

}
