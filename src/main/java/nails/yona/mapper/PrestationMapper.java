package nails.yona.mapper;

import nails.yona.dto.request.PrestationRequest;
import nails.yona.dto.response.PrestationResponse;
import nails.yona.model.Prestation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrestationMapper extends GenericMapper<Prestation, PrestationRequest, PrestationResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Prestation toEntity(PrestationRequest request);
}