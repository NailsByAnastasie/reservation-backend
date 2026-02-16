package nails.yona.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.response.AdminUserResponse;
import nails.yona.service.AdminUserService;
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
}