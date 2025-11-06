package automach.profiles.application.service;


import automach.profiles.domain.model.aggregates.*;
import automach.profiles.domain.model.valueobjects.UserId;
import automach.profiles.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserApplicationService {

    private final UserRepository repository;

    public UserApplicationService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Crear un perfil de cliente.
     */
    public User registerCustomer(String firstName, String lastName, String email) {
        User user = User.createCustomer(firstName, lastName, email);
        return repository.save(user);
    }

    /**
     * Crear un perfil de vendedor (con datos del negocio).
     */
    public User registerSeller(String firstName, String lastName, String email, SellerProfile sellerProfile) {
        User user = User.createSeller(firstName, lastName, email, sellerProfile);
        return repository.save(user);
    }

    /**
     * Obtener un usuario por su ID.
     */
    public Optional<User> getUserById(UserId id) {
        return repository.findById(id);
    }

    /**
     * Listar todos los usuarios registrados.
     */
    public List<User> listAllUsers() {
        return repository.findAll();
    }

    /**
     * Eliminar un usuario.
     */
    public void deleteUser(UserId id) {
        repository.deleteById(id);
    }
}
