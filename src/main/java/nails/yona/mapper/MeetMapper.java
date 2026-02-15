package nails.yona.mapper;

import nails.yona.dto.request.MeetRequest;
import nails.yona.dto.response.MeetResponse;
import nails.yona.model.Meet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, PrestationMapper.class})
public interface MeetMapper extends GenericMapper<Meet, MeetRequest, MeetResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "prestation", ignore = true)
    Meet toEntity(MeetRequest request);
}