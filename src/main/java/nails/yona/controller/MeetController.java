package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.MeetRequest;
import nails.yona.dto.response.MeetResponse;
import nails.yona.service.MeetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<MeetResponse> getAllMeets(Pageable pageable) {
        return meetService.getAllMeets(pageable);
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

    @Operation(operationId = "getUpcomingMeets")
    @GetMapping("/upcoming")
    public List<MeetResponse> getUpcomingMeets() {
        return meetService.getUpcomingMeets();
    }
}