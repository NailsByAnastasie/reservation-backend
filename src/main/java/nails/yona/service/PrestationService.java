package nails.yona.service;

import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.PrestationRequest;
import nails.yona.dto.response.PrestationResponse;
import nails.yona.mapper.PrestationMapper;
import nails.yona.model.Prestation;
import nails.yona.repository.PrestationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrestationService {

    private final PrestationRepository prestationRepository;
    private final PrestationMapper prestationMapper;

    @Transactional(readOnly = true)
    public List<PrestationResponse> getAllPrestations() {
        return prestationMapper.toResponseList(prestationRepository.findAll());
    }

    // TODO move to admin controller
    @Transactional
    public PrestationResponse createPrestation(PrestationRequest request) {
        Prestation prestationToSave = prestationMapper.toEntity(request);
        Prestation savedPrestation = prestationRepository.save(prestationToSave);

        return prestationMapper.toResponse(savedPrestation);
    }

    // TODO move to admin controller
    @Transactional
    public PrestationResponse updatePrestation(UUID id, PrestationRequest request) {
        Prestation prestation = prestationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prestation introuvable"));

        prestationMapper.updateEntity(request, prestation);

        return prestationMapper.toResponse(prestationRepository.save(prestation));
    }

    // TODO move to admin controller
    @Transactional
    public void deletePrestation(UUID id) {
        if (!prestationRepository.existsById(id)) {
            throw new IllegalArgumentException("Impossible de supprimer : Prestation introuvable.");
        }
        prestationRepository.deleteById(id);
    }
}