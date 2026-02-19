package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.EmailUpdateRequest;
import nails.yona.dto.request.PasswordUpdateRequest;
import nails.yona.dto.response.AdminUserResponse;
import nails.yona.service.AdminUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Tag(name = "AdminUser")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @Operation(operationId = "getAllAdmins")
    @GetMapping
    public Page<AdminUserResponse> getAllAdmins(Pageable pageable) {
        return adminUserService.getAllAdmins(pageable);
    }

    @Operation(operationId = "getUserByEmail")
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminUserResponse getUserByEmail(
            Principal principal
    ) {
        return adminUserService.getUserByEmail(principal.getName());
    }

    @Operation(operationId = "updatePassword")
    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(
            Principal principal,
            @Valid @RequestBody PasswordUpdateRequest request
    ) {

        adminUserService.updatePassword(principal.getName(), request);
    }

    @Operation(operationId = "updateEmail")
    @PutMapping("/me/email")
    public AdminUserResponse updateEmail(
            Principal principal,
            @Valid @RequestBody EmailUpdateRequest request
    ) {

        return adminUserService.updateEmail(principal.getName(), request);
    }
}