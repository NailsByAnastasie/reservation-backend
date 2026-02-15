package nails.yona.service;

import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.ClientRequest;
import nails.yona.dto.response.ClientResponse;
import nails.yona.mapper.ClientMapper;
import nails.yona.model.Client;
import nails.yona.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional(readOnly = true)
    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toResponseList(clients);
    }

    @Transactional
    public ClientResponse getOrCreateClient(ClientRequest request) {

        Optional<Client> existingClient = clientRepository.findByEmailIgnoreCase(request.email());

        if (existingClient.isPresent()) {
            return clientMapper.toResponse(existingClient.get());
        }

        Client newClient = clientMapper.toEntity(request);
        Client savedClient = clientRepository.save(newClient);
        return clientMapper.toResponse(savedClient);
    }
}