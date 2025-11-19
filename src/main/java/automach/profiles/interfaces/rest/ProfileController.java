package automach.profiles.interfaces.rest;

import automach.profiles.application.internal.commandservices.ProfileCommandServiceImpl;
import automach.profiles.application.internal.queryservices.ProfileQueryServiceImpl;
import automach.profiles.domain.exceptions.ProfileNotFoundException;
import automach.profiles.domain.model.commands.DeleteProfileCommand;
import automach.profiles.domain.model.queries.GetAllProfilesQuery;
import automach.profiles.domain.model.queries.GetProfileByIamUserIdQuery;
import automach.profiles.domain.model.queries.GetProfileByIdQuery;
import automach.profiles.domain.model.valueobjects.UserId;
import automach.profiles.interfaces.rest.resources.CreateCustomerProfileResource;
import automach.profiles.interfaces.rest.resources.CreateSellerProfileResource;
import automach.profiles.interfaces.rest.resources.ProfileResource;
import automach.profiles.interfaces.rest.resources.UpdateProfileResource;
import automach.profiles.interfaces.rest.transformers.CreateCustomerProfileCommandFromResourceAssembler;
import automach.profiles.interfaces.rest.transformers.CreateSellerProfileCommandFromResourceAssembler;
import automach.profiles.interfaces.rest.transformers.ProfileResourceFromAggregateAssembler;
import automach.profiles.interfaces.rest.transformers.UpdateProfileCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@Tag(name = "Perfiles", description = "Gestión de perfiles de cliente y vendedor")
public class ProfileController {

    private final ProfileCommandServiceImpl commandService;
    private final ProfileQueryServiceImpl queryService;
    private final CreateCustomerProfileCommandFromResourceAssembler customerAssembler;
    private final CreateSellerProfileCommandFromResourceAssembler sellerAssembler;
    private final UpdateProfileCommandFromResourceAssembler updateAssembler;
    private final ProfileResourceFromAggregateAssembler profileAssembler;

    public ProfileController(ProfileCommandServiceImpl commandService,
                             ProfileQueryServiceImpl queryService,
                             CreateCustomerProfileCommandFromResourceAssembler customerAssembler,
                             CreateSellerProfileCommandFromResourceAssembler sellerAssembler,
                             UpdateProfileCommandFromResourceAssembler updateAssembler,
                             ProfileResourceFromAggregateAssembler profileAssembler) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.customerAssembler = customerAssembler;
        this.sellerAssembler = sellerAssembler;
        this.updateAssembler = updateAssembler;
        this.profileAssembler = profileAssembler;
    }

    // -------------------------
    // COMMANDS
    // -------------------------

    @Operation(summary = "Registrar un cliente")
    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResource createCustomer(
            @Valid @RequestBody CreateCustomerProfileResource resource
    ) {
        var command = customerAssembler.toCommand(resource);
        var user = commandService.handle(command);
        return profileAssembler.toResource(user);
    }

    @Operation(summary = "Registrar un vendedor")
    @PostMapping("/sellers")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResource createSeller(
            @Valid @RequestBody CreateSellerProfileResource resource
    ) {
        var command = sellerAssembler.toCommand(resource);
        var user = commandService.handle(command);
        return profileAssembler.toResource(user);
    }

    @Operation(summary = "Actualizar un perfil existente")
    @PutMapping("/{id}")
    public ProfileResource updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileResource resource
    ) {
        var command = updateAssembler.toCommand(id, resource);
        var updated = commandService.handle(command);
        return profileAssembler.toResource(updated);
    }

    @Operation(summary = "Eliminar un perfil")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable Long id) {
        var command = new DeleteProfileCommand(UserId.of(id));
        commandService.handle(command);
    }

    // -------------------------
    // QUERIES
    // -------------------------

    @Operation(summary = "Listar todos los perfiles")
    @GetMapping
    public List<ProfileResource> getAllProfiles() {
        var query = new GetAllProfilesQuery();
        return queryService.handle(query).stream()
                .map(profileAssembler::toResource)
                .toList();
    }

    @Operation(summary = "Obtener un perfil por ID")
    @GetMapping("/{id}")
    public ProfileResource getProfileById(@PathVariable Long id) {
        var query = new GetProfileByIdQuery(UserId.of(id));
        var user = queryService.handle(query)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile with id %s not found".formatted(id)
                ));
        return profileAssembler.toResource(user);
    }


    @Operation(summary = "Obtener el perfil del usuario autenticado")
    @GetMapping("/me")
    public ProfileResource getMyProfile(@AuthenticationPrincipal Jwt jwt) {

        // Aquí depende cómo hayas nombrado el claim en el JWT de IAM.
        // Supongamos que el token trae: { "user_id": 123, "roles": ["CUSTOMER"] ... }
        Long iamUserId = jwt.getClaim("user_id");

        var query = new GetProfileByIamUserIdQuery(iamUserId);
        var user = queryService.handle(query)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile for iamUserId %s not found".formatted(iamUserId)
                ));

        return profileAssembler.toResource(user);
    }
}
