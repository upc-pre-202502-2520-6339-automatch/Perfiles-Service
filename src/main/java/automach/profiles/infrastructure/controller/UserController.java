package automach.profiles.infrastructure.controller;

import automach.profiles.application.service.UserApplicationService;
import automach.profiles.domain.model.aggregates.*;
import automach.profiles.domain.model.valueobjects.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios y Roles", description = "Gestión de perfiles de cliente y vendedor")
public class UserController {

    private final UserApplicationService service;

    public UserController(UserApplicationService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar un cliente")
    @PostMapping("/customers")
    public User createCustomer(@RequestBody CreateCustomerRequest request) {
        return service.registerCustomer(request.firstName(), request.lastName(), request.email());
    }

    @Operation(summary = "Registrar un vendedor")
    @PostMapping("/sellers")
    public User createSeller(@RequestBody CreateSellerRequest request) {
        SellerProfile profile = new SellerProfile(
                request.ruc(),
                request.businessType(),
                request.businessName(),
                request.address(),
                request.phoneNumber()
        );
        return service.registerSeller(request.firstName(), request.lastName(), request.email(), profile);
    }

    @Operation(summary = "Listar todos los usuarios")
    @GetMapping
    public List<User> listUsers() {
        return service.listAllUsers();
    }

    @Operation(summary = "Buscar usuario por ID")
    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return service.getUserById(new UserId(UUID.fromString(id)))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Operation(summary = "Eliminar usuario")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(new UserId(UUID.fromString(id)));
    }
}