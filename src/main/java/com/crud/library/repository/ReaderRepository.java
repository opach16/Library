package com.crud.library.repository;

import com.crud.library.domain.entities.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReaderRepository extends CrudRepository<Reader, Long> {

    List<Reader> findAll();
}
