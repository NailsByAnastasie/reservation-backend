package nails.yona.service;

import lombok.RequiredArgsConstructor;
import nails.yona.dto.request.PrestationRequest;
import nails.yona.dto.response.CategoryTarifResponse;
import nails.yona.dto.response.PrestationResponse;
import nails.yona.dto.response.PublicPrestationResponse;
import nails.yona.enums.PrestationCategory;
import nails.yona.mapper.PrestationMapper;
import nails.yona.model.Prestation;
import nails.yona.repository.MeetRepository;
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
    private final MeetRepository meetRepository;

    @Transactional(readOnly = true)
    public List<PrestationResponse> getAllPrestations() {
        return prestationMapper.toResponseList(prestationRepository.findAll());
    }

    @Transactional
    public PrestationResponse createPrestation(PrestationRequest request) {
        Prestation prestationToSave = prestationMapper.toEntity(request);
        Prestation savedPrestation = prestationRepository.save(prestationToSave);

        return prestationMapper.toResponse(savedPrestation);
    }

    @Transactional
    public PrestationResponse updatePrestation(UUID id, PrestationRequest request) {
        Prestation prestation = prestationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prestation introuvable"));

        prestationMapper.updateEntity(request, prestation);

        return prestationMapper.toResponse(prestationRepository.save(prestation));
    }

    @Transactional
    public void deletePrestation(UUID id) {
        if (!prestationRepository.existsById(id)) {
            throw new IllegalArgumentException("Impossible de supprimer : Prestation introuvable.");
        }

        meetRepository.unlinkPrestation(id);

        prestationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CategoryTarifResponse> getPublicTarifs() {

        List<Prestation> allActive = prestationRepository.findByActiveTrueOrderByPriceAsc();

        List<PrestationCategory> expectedOrder = List.of(
                PrestationCategory.HAND,
                PrestationCategory.FOOT,
                PrestationCategory.EYE
        );

        return expectedOrder.stream()
                .map(category -> {
                    List<PublicPrestationResponse> itemsForCategory = allActive.stream()
                            .filter(p -> p.getPrestationCategory() == category)
                            .map(p -> new PublicPrestationResponse(
                                    p.getId(),
                                    p.getName(),
                                    p.getPrice(),
                                    p.getPrestationCategory()
                            ))
                            .toList();

                    return new CategoryTarifResponse(
                            category.name(),
                            category.getLabel(),
                            itemsForCategory
                    );
                })
                .filter(group -> !group.items().isEmpty())
                .toList();
    }
}