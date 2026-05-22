package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.LoginRequest;
import nails.yona.dto.response.TokenResponse;
import nails.yona.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @Operation(operationId = "login")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}