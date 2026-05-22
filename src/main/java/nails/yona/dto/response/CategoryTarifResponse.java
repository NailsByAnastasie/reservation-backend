package nails.yona.dto.response;

import java.util.List;

public record CategoryTarifResponse(
        String categoryCode,
        String categoryLabel,
        List<PublicPrestationResponse> items
) {}