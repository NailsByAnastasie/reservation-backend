package nails.yona.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface GenericMapper<E, REQ, RES> {

    E toEntity(REQ request);

    RES toResponse(E entity);

    List<RES> toResponseList(List<E> entities);

    void updateEntity(REQ request, @MappingTarget E entity);
}