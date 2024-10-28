package com.crud.library.repository;

import com.crud.library.domain.entities.CopiesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CopyRepository extends CrudRepository<CopiesEntity, Long> {

    @Override
    List<CopiesEntity> findAll();

    List<CopiesEntity> findAllByTitleId(Long id);
}
