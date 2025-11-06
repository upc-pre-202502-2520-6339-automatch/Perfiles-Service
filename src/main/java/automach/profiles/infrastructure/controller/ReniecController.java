package automach.profiles.infrastructure.controller;

import automach.profiles.domain.model.query.ReniecQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users/reniec")
@Tag(name = "RENIEC Integration", description = "External API integration with RENIEC for DNI lookup")
public class ReniecController {

    private final ReniecQueryService reniecService;

    public ReniecController(ReniecQueryService reniecService) {
        this.reniecService = reniecService;
    }

    @Operation(summary = "Get person data by DNI using RENIEC API")
    @GetMapping("/{dni}")
    public Map<String, Object> getPersonByDni(@PathVariable String dni) {
        return reniecService.getPersonInfo(dni);
    }
}