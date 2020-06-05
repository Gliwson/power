package pl.power.services.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.power.domain.entity.EntityInterface;
import pl.power.mapper.MapperInterface;
import pl.power.model.DTOInterface;
import pl.power.services.exception.IdIsNullException;
import pl.power.services.exception.NotFoundIDException;

import java.util.List;

public abstract class CrudAbstractService<E extends EntityInterface, D extends DTOInterface> {
    private final JpaRepository<E, Long> jpaRepository;
    protected final MapperInterface<E, D> mapper;

    public CrudAbstractService(JpaRepository<E, Long> jpaRepository, MapperInterface<E, D> mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    public List<D> findAll() {
        List<E> allEntities = jpaRepository.findAll();
        return mapper.toDTOs(allEntities);
    }

    public D findById(Long id) {
        if (id == null) {
            throw new IdIsNullException();
        }
        E task = jpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundIDException(id));
        return mapper.toDTO(task);
    }

    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IdIsNullException();
        }
        jpaRepository.deleteById(id);
    }

    @Transactional
    public Long save(D dto) {
        E entity = mapper.toEntity(dto);
        return jpaRepository.save(entity).getId();
    }

}
