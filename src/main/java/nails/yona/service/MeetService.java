package nails.yona.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.MeetRequest;
import nails.yona.dto.response.MeetResponse;
import nails.yona.enums.MeetStatus;
import nails.yona.enums.WorkingDay;
import nails.yona.mapper.MeetMapper;
import nails.yona.model.Client;
import nails.yona.model.Meet;
import nails.yona.model.Prestation;
import nails.yona.model.WorkingHour;
import nails.yona.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final ClientRepository clientRepository;
    private final PrestationRepository prestationRepository;
    private final MeetMapper meetMapper;
    private final BlockedSlotRepository blockedSlotRepository;
    private final EmailService emailService;
    private final WorkingHourRepository workingHourRepository;

    @Transactional(readOnly = true)
    public Page<MeetResponse> getAllMeets(Pageable pageable) {
        return meetRepository.findAll(pageable).map(meetMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<MeetResponse> getUpcomingMeets() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusMonths(2).atTime(23, 59, 59);

        return meetRepository.findMeetsBetween(start, end)
                .stream()
                .map(meetMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public MeetResponse createMeet(MeetRequest request) {

        LocalDateTime now = LocalDateTime.now();
        if (request.dateStart().isBefore(now)) {
            throw new IllegalArgumentException("Impossible de prendre un rendez-vous dans le passé.");
        }
        if (request.dateStart().isAfter(now.plusMonths(2).withHour(23).withMinute(59))) {
            throw new IllegalArgumentException("Les réservations ne sont ouvertes que pour les 2 prochains mois.");
        }

        int minute = request.dateStart().getMinute();
        if (minute != 0 && minute != 30) {
            throw new IllegalArgumentException("Les rendez-vous doivent commencer à l'heure pile ou à la demi-heure (ex: 14:00 ou 14:30).");
        }

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente introuvable."));

        Prestation prestation = prestationRepository.findById(request.prestationId())
                .orElseThrow(() -> new EntityNotFoundException("Prestation introuvable."));

        LocalDateTime calculatedDateEnd = request.dateStart().plusMinutes(prestation.getTime());

        WorkingDay requestDay = WorkingDay.fromJavaDayOfWeek(request.dateStart().getDayOfWeek());
        WorkingHour workingHour = workingHourRepository.findByDay(requestDay)
                .orElseThrow(() -> new IllegalArgumentException("Jour non configuré."));

        if (workingHour.getIsClosed() ||
                request.dateStart().toLocalTime().isBefore(workingHour.getStartTime()) ||
                calculatedDateEnd.toLocalTime().isAfter(workingHour.getEndTime())) {
            throw new IllegalArgumentException("Le salon est fermé sur ce créneau horaire.");
        }

        if (meetRepository.hasOverlap(request.dateStart(), calculatedDateEnd)) {
            throw new IllegalArgumentException("Ce créneau est déjà réservé par une autre cliente.");
        }

        if (blockedSlotRepository.hasOverlap(request.dateStart(), calculatedDateEnd)) {
            throw new IllegalArgumentException("Le salon est indisponible à ces dates.");
        }

        Meet meet = meetMapper.toEntity(request);
        meet.setClient(client);
        meet.setPrestation(prestation);
        meet.setDateEnd(calculatedDateEnd);

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

    @Transactional
    public MeetResponse validateMeet(UUID id) {
        Meet meet = meetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable."));

        if (meet.getStatus() == MeetStatus.CONFIRMED) {
            throw new IllegalArgumentException("Ce rendez-vous est déjà validé.");
        }

        if (meet.getStatus() == MeetStatus.CANCELED) {
            throw new IllegalArgumentException("Impossible de valider un rendez-vous annulé.");
        }

        meet.setStatus(MeetStatus.CONFIRMED);

        Meet savedMeet = meetRepository.save(meet);

        emailService.sendMeetValidationToClient(savedMeet);

        return meetMapper.toResponse(savedMeet);
    }
}