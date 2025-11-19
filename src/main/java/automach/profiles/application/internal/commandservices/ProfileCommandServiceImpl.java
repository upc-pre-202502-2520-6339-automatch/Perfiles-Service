package automach.profiles.application.internal.commandservices;

import automach.profiles.domain.exceptions.ProfileNotFoundException;
import automach.profiles.domain.model.aggregates.SellerProfile;
import automach.profiles.domain.model.aggregates.User;
import automach.profiles.domain.model.commands.DeleteProfileCommand;
import automach.profiles.domain.model.commands.RegisterCustomerProfileCommand;
import automach.profiles.domain.model.commands.RegisterSellerProfileCommand;
import automach.profiles.domain.model.commands.UpdateProfileCommand;
import automach.profiles.domain.model.queries.UserRepository;
import automach.profiles.domain.model.valueobjects.RoleType;
import automach.profiles.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

@Service
public class ProfileCommandServiceImpl {

    private final UserRepository userRepository;

    public ProfileCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Maneja el comando para registrar un perfil de cliente.
     */
    public User handle(RegisterCustomerProfileCommand command) {
        User user = User.createCustomer(
                command.iamUserId(),
                command.firstName(),
                command.lastName(),
                command.email()
        );
        return userRepository.save(user);
    }


    /**
     * Maneja el comando para registrar un perfil de vendedor.
     */
    public User handle(RegisterSellerProfileCommand command) {
        SellerProfile sellerProfile = new SellerProfile(
                command.ruc(),
                command.businessType(),
                command.businessName(),
                command.address(),
                command.phoneNumber()
        );

        User user = User.createSeller(
                command.iamUserId(),
                command.firstName(),
                command.lastName(),
                command.email(),
                sellerProfile
        );
        return userRepository.save(user);
    }

    /**
     * Maneja el comando para eliminar un perfil.
     */
    public void handle(DeleteProfileCommand command) {
        UserId userId = command.userId();
        userRepository.deleteById(userId);
    }

    /**
     * Maneja el comando para actualizar la información de un perfil.
     */
    public User handle(UpdateProfileCommand command) {
        // 1. Buscar el agregado existente
        User existing = userRepository.findById(command.userId())
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile with id %s not found".formatted(command.userId().getValue())
                ));

        // 2. Actualizar datos básicos
        existing.updateBasicInfo(
                command.firstName(),
                command.lastName(),
                command.email()
        );

        // 3. Actualizar rol y datos de vendedor si aplica
        if (command.roleType() != null) {
            if (command.roleType() == RoleType.CUSTOMER) {
                // Pasa a ser solo cliente (sin datos de negocio)
                existing.downgradeToCustomer();
            } else if (command.roleType() == RoleType.SELLER) {
                // Construimos un nuevo SellerProfile con los datos del comando
                SellerProfile sellerProfile = new SellerProfile(
                        command.ruc(),
                        command.businessType(),
                        command.businessName(),
                        command.address(),
                        command.phoneNumber()
                );
                existing.promoteToSeller(sellerProfile);
            }
        }

        // 4. Guardar cambios
        return userRepository.save(existing);
    }
}
