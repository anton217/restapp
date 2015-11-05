package com.restapp.repository;

import com.restapp.domain.Accounting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Accounting entity.
 */
public interface AccountingRepository extends JpaRepository<Accounting,Long> {

}
