package com.crud.library.repository;

import com.crud.library.domain.entities.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TitleRepository extends CrudRepository<Title, Long> {

    List<Title> findAll();
}
