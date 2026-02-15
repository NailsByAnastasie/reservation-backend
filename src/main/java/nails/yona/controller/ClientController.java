package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nails.yona.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
@Tag(name = "Client")
public class ClientController {

    private final ClientService clientService;

    @Operation(operationId = "getClientIdByEmail")
    @GetMapping("/search")
    public UUID getClientIdByEmail(@RequestParam String email) {
        return clientService.findIdByEmail(email);
    }
}