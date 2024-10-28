package com.crud.library.repository;

import com.crud.library.domain.entities.ReadersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReaderRepository extends CrudRepository<ReadersEntity, Long> {
}
