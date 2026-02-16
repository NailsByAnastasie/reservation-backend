package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.AdminUserRequest;
import nails.yona.dto.response.AdminUserResponse;
import nails.yona.service.AdminUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Tag(name = "AdminUser")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @Operation(operationId = "getAllAdmins")
    @GetMapping
    public List<AdminUserResponse> getAllAdmins() {
        return adminUserService.getAllAdmins();
    }

    @Operation(operationId = "createAdmin")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminUserResponse createAdmin(@Valid @RequestBody AdminUserRequest request) {
        return adminUserService.createAdmin(request);
    }
}