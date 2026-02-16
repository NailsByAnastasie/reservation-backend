package nails.yona.mapper;

import nails.yona.dto.request.BlockedSlotRequest;
import nails.yona.dto.response.BlockedSlotResponse;
import nails.yona.model.BlockedSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlockedSlotMapper extends GenericMapper<BlockedSlot, BlockedSlotRequest, BlockedSlotResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BlockedSlot toEntity(BlockedSlotRequest request);
}