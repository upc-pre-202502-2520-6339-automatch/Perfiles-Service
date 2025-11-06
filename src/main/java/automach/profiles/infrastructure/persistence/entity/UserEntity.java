package automach.profiles.infrastructure.persistence.entity;

import automach.profiles.domain.model.valueobjects.RoleType;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Embedded
    private SellerProfileEmbeddable sellerProfile;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public RoleType getRoleType() { return roleType; }
    public void setRoleType(RoleType roleType) { this.roleType = roleType; }

    public SellerProfileEmbeddable getSellerProfile() { return sellerProfile; }
    public void setSellerProfile(SellerProfileEmbeddable sellerProfile) { this.sellerProfile = sellerProfile; }
}