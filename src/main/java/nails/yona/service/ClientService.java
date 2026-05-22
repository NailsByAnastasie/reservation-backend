package nails.yona.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.ClientRequest;
import nails.yona.dto.response.ClientResponse;
import nails.yona.mapper.ClientMapper;
import nails.yona.model.Client;
import nails.yona.repository.ClientRepository;
import nails.yona.repository.MeetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final MeetRepository meetRepository;

    @Transactional(readOnly = true)
    public List<ClientResponse> searchByEmail(String email) {
        return clientRepository.findByEmailContainingIgnoreCase(email)
                .stream()
                .map(clientMapper::toResponse)
                .toList();
    }

    @Transactional
    public ClientResponse createClient(ClientRequest request) {
        if (clientRepository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Un client avec cet email existe déjà.");
        }
        Client client = clientMapper.toEntity(request);
        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Transactional
    public ClientResponse updateClient(UUID id, ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable."));

        if (!client.getEmail().equalsIgnoreCase(request.email()) &&
                clientRepository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un autre client.");
        }

        clientMapper.updateEntity(request, client);
        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Transactional
    public void deleteClient(UUID id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Client introuvable.");
        }

        meetRepository.unlinkClient(id);

        clientRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponse> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toResponse);
    }
}