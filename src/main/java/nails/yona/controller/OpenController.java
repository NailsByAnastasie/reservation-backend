package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.response.CategoryTarifResponse;
import nails.yona.dto.response.PublicPrestationResponse;
import nails.yona.service.AvailabilityService;
import nails.yona.service.PrestationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/open")
@RequiredArgsConstructor
@Tag(name = "Open")
public class OpenController {

    private final AvailabilityService availabilityService;
    private final PrestationService prestationService;

    @Operation(operationId = "getAvailableSlots")
    @GetMapping("/slots")
    public List<String> getAvailableSlots(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return availabilityService.getAvailableSlotsForDate(date);
    }

    @Operation(operationId = "getPublicTarifs")
    @GetMapping("/tarifs")
    public List<CategoryTarifResponse> getPublicTarifs() {
        return prestationService.getPublicTarifs();
    }
}