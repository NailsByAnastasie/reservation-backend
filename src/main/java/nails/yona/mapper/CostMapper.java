package nails.yona.mapper;

import nails.yona.dto.request.CostRequest;
import nails.yona.dto.response.CostResponse;
import nails.yona.model.Cost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CostMapper extends GenericMapper<Cost, CostRequest, CostResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cost toEntity(CostRequest request);
}