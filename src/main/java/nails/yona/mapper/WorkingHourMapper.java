package nails.yona.mapper;

import nails.yona.dto.request.WorkingHourRequest;
import nails.yona.dto.response.WorkingHourResponse;
import nails.yona.model.WorkingHour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkingHourMapper extends GenericMapper<WorkingHour, WorkingHourRequest, WorkingHourResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorkingHour toEntity(WorkingHourRequest request);
}