package nails.yona.service;

import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.AdminUserRequest;
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
}