package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.BlockedSlotRequest;
import nails.yona.dto.response.BlockedSlotResponse;
import nails.yona.service.BlockedSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blocked-slots")
@RequiredArgsConstructor
@Tag(name = "BlockedSlot")
public class BlockedSlotController {

    private final BlockedSlotService blockedSlotService;

    @Operation(operationId = "getAllBlockedSlots")
    @GetMapping
    public List<BlockedSlotResponse> getAllBlockedSlots() {
        return blockedSlotService.getAllBlockedSlots();
    }

    @Operation(operationId = "createBlockedSlot")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlockedSlotResponse createBlockedSlot(@Valid @RequestBody BlockedSlotRequest request) {
        return blockedSlotService.createBlockedSlot(request);
    }

    @Operation(operationId = "deleteBlockedSlot")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlockedSlot(@PathVariable UUID id) {
        blockedSlotService.deleteBlockedSlot(id);
    }
}