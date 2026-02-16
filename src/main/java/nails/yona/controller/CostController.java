package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.CostRequest;
import nails.yona.dto.response.CostResponse;
import nails.yona.service.CostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequestMapping("/api/costs")
@RequiredArgsConstructor
@Tag(name = "Cost")
public class CostController {

    private final CostService costService;

    @Operation(operationId = "getAllCosts")
    @GetMapping
    public Page<CostResponse> getAllCosts(Pageable pageable) {
        return costService.getAllCosts(pageable);
    }
    @Operation(operationId = "createCost")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CostResponse createCost(@Valid @RequestBody CostRequest request) {
        return costService.createCost(request);
    }

    @Operation(operationId = "updateCost")
    @PutMapping("/{id}")
    public CostResponse updateCost(
            @PathVariable UUID id,
            @Valid @RequestBody CostRequest request) {
        return costService.updateCost(id, request);
    }

    @Operation(operationId = "deleteCost")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCost(@PathVariable UUID id) {
        costService.deleteCost(id);
    }
}