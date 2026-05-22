package nails.yona.service;

import lombok.RequiredArgsConstructor;
import nails.yona.config.security.JwtService;
import nails.yona.dto.request.LoginRequest;
import nails.yona.dto.response.TokenResponse;
import nails.yona.model.AdminUser;
import nails.yona.repository.AdminUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {

        AdminUser admin = adminUserRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.password(), admin.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(admin.getEmail());

        return new TokenResponse(token);
    }
}