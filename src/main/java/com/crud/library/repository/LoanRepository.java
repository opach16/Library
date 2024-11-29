package com.crud.library.repository;

import com.crud.library.domain.entities.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findAll();
}
