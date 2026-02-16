package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.WorkingHourRequest;
import nails.yona.dto.response.WorkingHourResponse;
import nails.yona.enums.WorkingDay;
import nails.yona.service.WorkingHourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/working-hours")
@RequiredArgsConstructor
@Tag(name = "WorkingHour")
public class WorkingHourController {

    private final WorkingHourService workingHourService;

    @Operation(operationId = "getAllWorkingHours")
    @GetMapping
    public List<WorkingHourResponse> getAllWorkingHours() {
        return workingHourService.getAllWorkingHours();
    }

    @Operation(operationId = "upsertWorkingHour")
    @PutMapping("/{day}")
    public WorkingHourResponse upsertWorkingHour(
            @PathVariable WorkingDay day,
            @Valid @RequestBody WorkingHourRequest request) {
        return workingHourService.upsertWorkingHour(day, request);
    }
}