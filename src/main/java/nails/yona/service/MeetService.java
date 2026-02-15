package nails.yona.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.MeetRequest;
import nails.yona.dto.response.MeetResponse;
import nails.yona.enums.MeetStatus;
import nails.yona.mapper.MeetMapper;
import nails.yona.model.Client;
import nails.yona.model.Meet;
import nails.yona.model.Prestation;
import nails.yona.repository.ClientRepository;
import nails.yona.repository.MeetRepository;
import nails.yona.repository.PrestationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final ClientRepository clientRepository;
    private final PrestationRepository prestationRepository;
    private final MeetMapper meetMapper;

    @Transactional(readOnly = true)
    public List<MeetResponse> getAllMeets() {
        return meetMapper.toResponseList(meetRepository.findAll());
    }

    @Transactional
    public MeetResponse createMeet(MeetRequest request) {

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente introuvable" + request.clientId()));

        Prestation prestation = prestationRepository.findById(request.prestationId())
                .orElseThrow(() -> new EntityNotFoundException("Prestation introuvable" + request.prestationId()));

        Meet meet = meetMapper.toEntity(request);

        meet.setClient(client);
        meet.setPrestation(prestation);

        Meet savedMeet = meetRepository.save(meet);
        return meetMapper.toResponse(savedMeet);
    }

    @Transactional
    public void deleteMeet(UUID id) {
        if (!meetRepository.existsById(id)) {
            throw new EntityNotFoundException("Rendez-vous introuvable.");
        }
        meetRepository.deleteById(id);
    }

    @Transactional
    public MeetResponse cancelMeet(UUID id) {

        Meet meet = meetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable."));

        if (meet.getStatus() == MeetStatus.CANCELED) {
            throw new IllegalArgumentException("Ce rendez-vous est déjà annulé.");
        }

        meet.setStatus(MeetStatus.CANCELED);

        return meetMapper.toResponse(meetRepository.save(meet));
    }
}