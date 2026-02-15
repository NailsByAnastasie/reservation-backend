package nails.yona.mapper;

import java.util.List;

public interface GenericMapper<E, REQ, RES> {

    E toEntity(REQ request);

    RES toResponse(E entity);

    List<RES> toResponseList(List<E> entities);
}