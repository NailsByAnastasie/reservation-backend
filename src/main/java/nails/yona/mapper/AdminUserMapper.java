package nails.yona.mapper;

import nails.yona.dto.request.AdminUserRequest;
import nails.yona.dto.response.AdminUserResponse;
import nails.yona.model.AdminUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminUserMapper extends GenericMapper<AdminUser, AdminUserRequest, AdminUserResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    AdminUser toEntity(AdminUserRequest request);

}