package nails.yona.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.CostRequest;
import nails.yona.dto.response.CostResponse;
import nails.yona.mapper.CostMapper;
import nails.yona.model.Cost;
import nails.yona.repository.CostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CostService {

    private final CostRepository costRepository;
    private final CostMapper costMapper;

    @Transactional(readOnly = true)
    public Page<CostResponse> getAllCosts(Pageable pageable) {
        return costRepository.findAll(pageable).map(costMapper::toResponse);
    }

    @Transactional
    public CostResponse createCost(CostRequest request) {
        Cost cost = costMapper.toEntity(request);
        Cost savedCost = costRepository.save(cost);
        return costMapper.toResponse(savedCost);
    }

    @Transactional
    public CostResponse updateCost(UUID id, CostRequest request) {
        Cost cost = costRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dépense introuvable."));

        costMapper.updateEntity(request, cost);

        Cost updatedCost = costRepository.save(cost);
        return costMapper.toResponse(updatedCost);
    }

    @Transactional
    public void deleteCost(UUID id) {
        if (!costRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : Dépense introuvable.");
        }
        costRepository.deleteById(id);
    }
}