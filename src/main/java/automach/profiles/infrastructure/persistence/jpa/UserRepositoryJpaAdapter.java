package automach.profiles.infrastructure.persistence.jpa;

import automach.profiles.domain.model.aggregates.SellerProfile;
import automach.profiles.domain.model.aggregates.User;
import automach.profiles.domain.model.queries.UserRepository;
import automach.profiles.domain.model.valueobjects.UserId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryJpaAdapter implements UserRepository {

    private final JpaUserRepository jpaRepository;

    public UserRepositoryJpaAdapter(JpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        // Nuevo usuario (sin id en el dominio)
        if (user.getId() == null) {
            UserEntity newEntity = toEntityForCreate(user);
            UserEntity saved = jpaRepository.save(newEntity);
            return toDomain(saved);
        }

        // Usuario existente (con id)
        Long id = user.getId().getValue();
        Optional<UserEntity> existingOpt = jpaRepository.findById(id);

        if (existingOpt.isPresent()) {
            UserEntity existing = existingOpt.get();
            updateExistingEntityFromDomain(existing, user);
            UserEntity saved = jpaRepository.save(existing);
            return toDomain(saved);
        } else {
            // Si llega un agregado con id pero no existe en BD,
            // lo tratamos como creación (opcional: podrías lanzar excepción).
            UserEntity newEntity = toEntityForCreate(user);
            UserEntity saved = jpaRepository.save(newEntity);
            return toDomain(saved);
        }
    }

    @Override
    public Optional<User> findById(UserId id) {
        if (id == null) return Optional.empty();
        return jpaRepository.findById(id.getValue())
                .map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UserId id) {
        if (id == null) return;
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public Optional<User> findByIamUserId(Long iamUserId) {
        if (iamUserId == null) return Optional.empty();
        return jpaRepository.findByIamUserId(iamUserId)
                .map(this::toDomain);
    }

    // ==============================
    // Mapping helpers
    // ==============================

    /**
     * Mapping para crear una nueva entidad JPA a partir del agregado de dominio.
     * No se establece el id, se deja que la BD lo genere.
     */
    private UserEntity toEntityForCreate(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        entity.setIamUserId(domain.getIamUserId());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setEmail(domain.getEmail());
        entity.setRoleType(domain.getRoleType());

        if (domain.getSellerProfile() != null) {
            entity.setSellerProfile(toEmbeddable(domain.getSellerProfile()));
        }

        return entity;
    }

    /**
     * Actualiza una entidad JPA ya administrada con los datos del agregado de dominio.
     */
    private void updateExistingEntityFromDomain(UserEntity existing, User domain) {
        existing.setFirstName(domain.getFirstName());
        existing.setLastName(domain.getLastName());
        existing.setEmail(domain.getEmail());
        existing.setRoleType(domain.getRoleType());

        if (domain.getSellerProfile() != null) {
            existing.setSellerProfile(toEmbeddable(domain.getSellerProfile()));
        } else {
            existing.setSellerProfile(null);
        }
    }

    private User toDomain(UserEntity entity) {
        if (entity == null) return null;

        SellerProfile profile = entity.getSellerProfile() != null
                ? toDomainProfile(entity.getSellerProfile())
                : null;

        return new User(
                entity.getId() != null ? UserId.of(entity.getId()) : null,
                entity.getIamUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getRoleType(),
                profile
        );
    }

    private SellerProfileEmbeddable toEmbeddable(SellerProfile profile) {
        if (profile == null) return null;

        SellerProfileEmbeddable embeddable = new SellerProfileEmbeddable();
        embeddable.setRuc(profile.getRuc());
        embeddable.setBusinessType(profile.getBusinessType());
        embeddable.setBusinessName(profile.getBusinessName());
        embeddable.setAddress(profile.getAddress());
        embeddable.setPhoneNumber(profile.getPhoneNumber());
        return embeddable;
    }

    private SellerProfile toDomainProfile(SellerProfileEmbeddable embeddable) {
        if (embeddable == null) return null;

        return new SellerProfile(
                embeddable.getRuc(),
                embeddable.getBusinessType(),
                embeddable.getBusinessName(),
                embeddable.getAddress(),
                embeddable.getPhoneNumber()
        );
    }
}
