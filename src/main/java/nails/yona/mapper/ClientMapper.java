package nails.yona.mapper;

import nails.yona.dto.request.ClientRequest;
import nails.yona.dto.response.ClientResponse;
import nails.yona.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper extends GenericMapper<Client, ClientRequest, ClientResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Client toEntity(ClientRequest request);
}