package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.MeetRequest;
import nails.yona.dto.response.MeetResponse;
import nails.yona.service.MeetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meets")
@RequiredArgsConstructor
@Tag(name = "Meet")
public class MeetController {

    private final MeetService meetService;

    @Operation(operationId = "getAllMeets")
    @GetMapping
    public List<MeetResponse> getAllMeets() {
        return meetService.getAllMeets();
    }

    @Operation(operationId = "createMeet")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeetResponse createMeet(@Valid @RequestBody MeetRequest request) {
        return meetService.createMeet(request);
    }

    @Operation(operationId = "deleteMeet")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeet(@PathVariable UUID id) {
        meetService.deleteMeet(id);
    }

    @Operation(operationId = "cancelMeet")
    @PatchMapping("/{id}/cancel")
    public MeetResponse cancelMeet(@PathVariable UUID id) {
        return meetService.cancelMeet(id);
    }
}