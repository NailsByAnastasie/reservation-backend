package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.ClientRequest;
import nails.yona.dto.response.ClientResponse;
import nails.yona.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
@Tag(name = "Client")
public class ClientController {

    private final ClientService clientService;

    @Operation(operationId = "getClientByEmail")
    @GetMapping("/search")
    public ClientResponse getClientByEmail(@RequestParam String email) {
        return clientService.findByEmail(email);
    }

    @Operation(operationId = "createClient")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse createClient(@Valid @RequestBody ClientRequest request) {
        return clientService.createClient(request);
    }

    @Operation(operationId = "updateClient")
    @PutMapping("/{id}")
    public ClientResponse updateClient(
            @PathVariable UUID id,
            @Valid @RequestBody ClientRequest request) {
        return clientService.updateClient(id, request);
    }
}