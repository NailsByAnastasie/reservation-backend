package nails.yona.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.BlockedSlotRequest;
import nails.yona.dto.response.BlockedSlotResponse;
import nails.yona.mapper.BlockedSlotMapper;
import nails.yona.model.BlockedSlot;
import nails.yona.repository.BlockedSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockedSlotService {

    private final BlockedSlotRepository blockedSlotRepository;
    private final BlockedSlotMapper blockedSlotMapper;

    @Transactional(readOnly = true)
    public List<BlockedSlotResponse> getAllBlockedSlots() {
        return blockedSlotMapper.toResponseList(blockedSlotRepository.findAll());
    }

    @Transactional
    public BlockedSlotResponse createBlockedSlot(BlockedSlotRequest request) {

        if (request.dateStart().isAfter(request.dateEnd())) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }

        BlockedSlot blockedSlot = blockedSlotMapper.toEntity(request);
        BlockedSlot savedSlot = blockedSlotRepository.save(blockedSlot);

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