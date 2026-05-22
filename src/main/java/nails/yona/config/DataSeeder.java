package nails.yona.config;

import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.AdminUserRequest;
import nails.yona.repository.AdminUserRepository;
import nails.yona.service.AdminUserService;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AdminUserService adminUserService;
    private final AdminUserRepository adminUserRepository;

    @Value("${app.admin.default.email}")
    private String defaultEmail;

    @Value("${app.admin.default.password}")
    private String defaultPassword;

    @Override
    public void run(String @NonNull ... args) {
        if (!adminUserRepository.existsByEmailIgnoreCase(defaultEmail)) {
            AdminUserRequest request = new AdminUserRequest(defaultEmail, defaultPassword);
            adminUserService.createAdmin(request);
            System.out.println("✅ Compte Super Admin généré avec succès !");
        }
    }
}