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

/**
 * Infrastructure adapter that connects the domain UserRepository with the JPA database.
 * It handles manual mapping between domain and persistence entities.
 */
@Repository
@Transactional
public class UserRepositoryJpaAdapter implements UserRepository {

    private final JpaUserRepository jpaRepository;

    public UserRepositoryJpaAdapter(JpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    // ------------------------------
    // Domain Repository Methods
    // ------------------------------

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
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

    // ------------------------------
    // Manual Mapping Logic
    // ------------------------------

    /**
     * Maps a Domain User to a JPA Entity.
     */
    private UserEntity toEntity(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().getValue());
        }

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
     * Maps a JPA Entity to a Domain User.
     */
    private User toDomain(UserEntity entity) {
        if (entity == null) return null;

        SellerProfile profile = entity.getSellerProfile() != null
                ? toDomainProfile(entity.getSellerProfile())
                : null;

        return new User(
                new UserId(entity.getId()),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getRoleType(),
                profile
        );
    }

    /**
     * Maps a Domain SellerProfile to an Embeddable JPA object.
     */
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

    /**
     * Maps an Embeddable SellerProfile to a Domain SellerProfile.
     */
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
