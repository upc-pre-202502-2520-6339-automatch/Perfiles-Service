package automach.profiles.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    // Si luego quieres búsquedas específicas:
    // Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIamUserId(Long iamUserId);

}
