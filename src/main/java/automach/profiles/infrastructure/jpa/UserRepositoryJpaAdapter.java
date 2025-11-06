package automach.profiles.infrastructure.jpa;

import automach.profiles.domain.repository.UserRepository;
import automach.profiles.domain.model.aggregates.SellerProfile;
import automach.profiles.domain.model.aggregates.User;
import automach.profiles.domain.model.valueobjects.UserId;
import automach.profiles.infrastructure.persistence.entity.SellerProfileEmbeddable;
import automach.profiles.infrastructure.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class UserRepositoryJpaAdapter implements UserRepository {

    private final JpaUserRepository jpaRepository;

    public UserRepositoryJpaAdapter(JpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        // If user has no id -> create flow
        if (user.getId() == null || user.getId().getValue() == null) {
            UserEntity newEntity = toEntityForCreate(user);
            UserEntity saved = jpaRepository.save(newEntity);
            return toDomain(saved);
        }

        // If user has id -> update flow: load managed entity, update fields, save
        UUID uuid = user.getId().getValue();
        Optional<UserEntity> existingOpt = jpaRepository.findById(uuid);
        if (existingOpt.isPresent()) {
            UserEntity existing = existingOpt.get();
            updateExistingEntityFromDomain(existing, user);
            UserEntity saved = jpaRepository.save(existing); // merge/persist on managed entity
            return toDomain(saved);
        } else {
            // ID provided but row does not exist -> treat as create (ensure id null to avoid detached issues)
            UserEntity newEntity = toEntityForCreate(user);
            UserEntity saved = jpaRepository.save(newEntity);
            return toDomain(saved);
        }
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.getValue()).map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(UserId id) {
        jpaRepository.deleteById(id.getValue());
    }

    // --- mapping helpers ---

    /**
     * Create mapping for new entities: do NOT set id (let JPA generate it).
     */
    private UserEntity toEntityForCreate(User domain) {
        if (domain == null) return null;
        UserEntity entity = new UserEntity();
        // do NOT set entity.id here
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
     * Update a managed entity with values from the domain aggregate.
     * This keeps the entity managed by the persistence context and avoids detached exceptions.
     */
    private void updateExistingEntityFromDomain(UserEntity existing, User domain) {
        existing.setFirstName(domain.getFirstName());
        existing.setLastName(domain.getLastName());
        existing.setEmail(domain.getEmail());
        existing.setRoleType(domain.getRoleType());

        if (domain.getSellerProfile() != null) {
            SellerProfileEmbeddable embed = toEmbeddable(domain.getSellerProfile());
            existing.setSellerProfile(embed);
        } else {
            existing.setSellerProfile(null);
        }
    }

    private UserEntity toEntity(User domain) {
        // Keep as a convenience but prefer toEntityForCreate + updateExisting...
        return toEntityForCreate(domain);
    }

    private User toDomain(UserEntity entity) {
        if (entity == null) return null;
        SellerProfile profile = entity.getSellerProfile() != null ?
                toDomainProfile(entity.getSellerProfile()) : null;

        return new User(
                new automach.profiles.domain.model.valueobjects.UserId(entity.getId()),
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
