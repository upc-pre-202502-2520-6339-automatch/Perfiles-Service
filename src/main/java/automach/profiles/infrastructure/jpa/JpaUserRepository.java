package automach.profiles.infrastructure.jpa;

import automach.profiles.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {}
