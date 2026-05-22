package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.PrestationRequest;
import nails.yona.dto.response.PrestationResponse;
import nails.yona.service.PrestationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prestations")
@RequiredArgsConstructor
@Tag(name = "Prestation")
public class PrestationController {

    private final PrestationService prestationService;

    @Operation(operationId = "getAllPrestations")
    @GetMapping
    public List<PrestationResponse> getAllPrestations() {
        return prestationService.getAllPrestations();
    }

    @Operation(operationId = "createPrestation")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrestationResponse createPrestation(@Valid @RequestBody PrestationRequest request) {
        return prestationService.createPrestation(request);
    }

    @Operation(operationId = "updatePrestation")
    @PutMapping("/{id}")
    public PrestationResponse updatePrestation(
            @PathVariable UUID id,
            @Valid @RequestBody PrestationRequest request) {
        return prestationService.updatePrestation(id, request);
    }

    @Operation(operationId = "deletePrestation")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrestation(@PathVariable UUID id) {
        prestationService.deletePrestation(id);
    }
}