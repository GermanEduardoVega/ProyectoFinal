package io.anchormind.backend.business.services.base;

import io.anchormind.backend.domain.entities.base.BaseEntity;
import io.anchormind.backend.repositories.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract  class BaseServiceImpl<E extends BaseEntity, ID extends Serializable> implements BaseService<E, ID> {

    // Usamos protected para que ClinicServiceImpl pueda usarlo sin drama
    protected BaseRepository<E, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }


    @Override
    @Transactional
    public List<E> findAll() throws Exception {
        try {
            return baseRepository.findAll();
        }catch (Exception e){
            throw new Exception(e.getMessage());}
    }

    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            return entityOptional.orElseThrow(() -> new Exception("Entidad no encontrada"));
        }catch (Exception e){
            throw new Exception(e.getMessage());}

    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            return baseRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            if (!baseRepository.existsById(id)) {
                throw new Exception("Entidad no encontrada para actualizar");
            }
            entity.setId((Long) id); // Aseguramos que el ID se mantenga para el UPDATE
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            if (baseRepository.existsById(id)) {
                baseRepository.deleteById(id); // Borrado físico por defecto
                return true;
            } else {
                throw new Exception("Entidad no encontrada");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
