package pl.power.mapper;

import pl.power.domain.entity.EntityInterface;
import pl.power.model.DTOInterface;

import java.util.List;
import java.util.stream.Collectors;

public interface MapperInterface<E extends EntityInterface, D extends DTOInterface> {
    E toEntity(D dto);
    D toDTO(E entity);

    default List<E> toEntities(List<D> dtos){
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    default List<D> toDTOs(List<E> entities){
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
