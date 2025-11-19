package automach.profiles.interfaces.rest;

import automach.profiles.application.internal.queryservices.ReniecQueryService;
import automach.profiles.interfaces.rest.resources.ReniecPersonResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profiles/reniec")
@Tag(name = "RENIEC Integration", description = "External API integration with RENIEC for DNI lookup")
public class ReniecController {

    private final ReniecQueryService reniecService;

    public ReniecController(ReniecQueryService reniecService) {
        this.reniecService = reniecService;
    }

    @Operation(summary = "Get person data by DNI using RENIEC API")
    @GetMapping("/{dni}")
    public ReniecPersonResource getPersonByDni(@PathVariable String dni) {
        return new ReniecPersonResource(reniecService.getPersonInfo(dni));
    }
}
