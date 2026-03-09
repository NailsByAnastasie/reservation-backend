package nails.yona.dto.response;
import nails.yona.enums.PrestationCategory;
import java.util.UUID;

public record PublicPrestationResponse (
        UUID id,
        String name,
        Integer price,
        PrestationCategory prestationCategory
) {}
