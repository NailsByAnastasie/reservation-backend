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
import nails.yona.repository.BlockedSlotRepository;
import nails.yona.repository.ClientRepository;
import nails.yona.repository.MeetRepository;
import nails.yona.repository.PrestationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final ClientRepository clientRepository;
    private final PrestationRepository prestationRepository;
    private final MeetMapper meetMapper;
    private final BlockedSlotRepository blockedSlotRepository;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public Page<MeetResponse> getAllMeets(Pageable pageable) {
        return meetRepository.findAll(pageable).map(meetMapper::toResponse);
    }

    @Transactional
    public MeetResponse createMeet(MeetRequest request) {

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente introuvable."));

        Prestation prestation = prestationRepository.findById(request.prestationId())
                .orElseThrow(() -> new EntityNotFoundException("Prestation introuvable."));

        if (meetRepository.hasOverlap(request.dateStart(), request.dateEnd())) {
            throw new IllegalArgumentException("Ce créneau est déjà réservé par une autre cliente.");
        }

        if (blockedSlotRepository.hasOverlap(request.dateStart(), request.dateEnd())) {
             throw new IllegalArgumentException("Le salon est indisponible à ces dates.");
         }

        Meet meet = meetMapper.toEntity(request);

        meet.setClient(client);
        meet.setPrestation(prestation);

        Meet savedMeet = meetRepository.save(meet);

        emailService.sendMeetConfirmation(savedMeet);

        return meetMapper.toResponse(savedMeet);
    }

    @Transactional
    public void deleteMeet(UUID id) {
        Meet meet = meetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable."));

        if (meet.getStatus() != MeetStatus.CANCELED) {
            throw new IllegalArgumentException("Impossible de supprimer un rendez-vous qui n'est pas annulé.");
        }

        meetRepository.delete(meet);
    }

    @Transactional
    public MeetResponse cancelMeet(UUID id) {

        Meet meet = meetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable."));

        if (meet.getStatus() == MeetStatus.CANCELED) {
            throw new IllegalArgumentException("Ce rendez-vous est déjà annulé.");
        }

        meet.setStatus(MeetStatus.CANCELED);

        Meet savedMeet = meetRepository.save(meet);

        emailService.sendMeetCancellationToClient(savedMeet);
        emailService.sendMeetCancellationToAdmin(savedMeet);

        return meetMapper.toResponse(savedMeet);
    }
}