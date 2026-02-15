package nails.yona.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nails.yona.model.Client;
import nails.yona.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public UUID findIdByEmail(String email) {
        return clientRepository.findByEmailIgnoreCase(email)
                .map(Client::getId)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable avec l'email : " + email));
    }
}