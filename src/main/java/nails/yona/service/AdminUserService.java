package nails.yona.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.AdminUserRequest;
import nails.yona.dto.request.EmailUpdateRequest;
import nails.yona.dto.request.PasswordUpdateRequest;
import nails.yona.dto.response.AdminUserResponse;
import nails.yona.mapper.AdminUserMapper;
import nails.yona.model.AdminUser;
import nails.yona.repository.AdminUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<AdminUserResponse> getAllAdmins() {
        return adminUserMapper.toResponseList(adminUserRepository.findAll());
    }

    @Transactional
    public AdminUserResponse createAdmin(AdminUserRequest request) {

        if (adminUserRepository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Un administrateur avec cet email existe déjà.");
        }

        AdminUser admin = adminUserMapper.toEntity(request);

        admin.setPasswordHash(passwordEncoder.encode(request.password()));

        AdminUser savedAdmin = adminUserRepository.save(admin);
        return adminUserMapper.toResponse(savedAdmin);
    }

    @Transactional
    public void updatePassword(String adminEmail, PasswordUpdateRequest request) {

        AdminUser admin = adminUserRepository.findByEmailIgnoreCase(adminEmail)
                .orElseThrow(() -> new EntityNotFoundException("Administrateur introuvable."));

        if (!passwordEncoder.matches(request.oldPassword(), admin.getPasswordHash())) {
            throw new IllegalArgumentException("L'ancien mot de passe est incorrect.");
        }

        admin.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        adminUserRepository.save(admin);
    }

    @Transactional
    public AdminUserResponse updateEmail(String currentEmail, EmailUpdateRequest request) {

        AdminUser admin = adminUserRepository.findByEmailIgnoreCase(currentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Administrateur introuvable."));

        if (!currentEmail.equalsIgnoreCase(request.newEmail()) &&
                adminUserRepository.existsByEmailIgnoreCase(request.newEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte.");
        }

        admin.setEmail(request.newEmail());

        AdminUser savedAdmin = adminUserRepository.save(admin);
        return adminUserMapper.toResponse(savedAdmin);
    }
}