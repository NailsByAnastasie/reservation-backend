package nails.yona.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.BlockedSlotRequest;
import nails.yona.dto.response.BlockedSlotResponse;
import nails.yona.mapper.BlockedSlotMapper;
import nails.yona.model.BlockedSlot;
import nails.yona.repository.BlockedSlotRepository;
import nails.yona.repository.MeetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockedSlotService {

    private final BlockedSlotRepository blockedSlotRepository;
    private final BlockedSlotMapper blockedSlotMapper;
    private final MeetRepository meetRepository;

    @Transactional(readOnly = true)
    public List<BlockedSlotResponse> getAllBlockedSlots() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusMonths(6 ).atTime(23, 59, 59);

        List<BlockedSlot> slots = blockedSlotRepository.findSlotsBetweenDates(start, end);

        return blockedSlotMapper.toResponseList(slots);
    }

    @Transactional
    public BlockedSlotResponse createBlockedSlot(BlockedSlotRequest request) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime maxDate = now.plusMonths(6);

        if (request.dateStart().isAfter(request.dateEnd())) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }

        if (request.dateStart().isBefore(now)) {
            throw new IllegalArgumentException("Impossible de créer une absence dans le passé.");
        }

        if (request.dateEnd().isAfter(maxDate)) {
            throw new IllegalArgumentException("Vous ne pouvez pas bloquer une période au-delà des 6 prochains mois.");
        }

        if (blockedSlotRepository.hasOverlap(request.dateStart(), request.dateEnd())) {
            throw new IllegalArgumentException("Une absence ou un chevauchement existe déjà sur cette période.");
        }

        if (meetRepository.hasOverlap(request.dateStart(), request.dateEnd())) {
            throw new IllegalArgumentException("Impossible de bloquer cette période : un ou plusieurs rendez-vous sont déjà prévus. Veuillez d'abord les annuler.");
        }

        BlockedSlot blockedSlot = blockedSlotMapper.toEntity(request);
        BlockedSlot savedSlot = blockedSlotRepository.save(blockedSlot);

         blockedSlotRepository.deleteByDateEndBefore(now);

        return blockedSlotMapper.toResponse(savedSlot);
    }

    @Transactional
    public void deleteBlockedSlot(UUID id) {
        if (!blockedSlotRepository.existsById(id)) {
            throw new EntityNotFoundException("Créneau bloqué introuvable.");
        }
        blockedSlotRepository.deleteById(id);
    }
}