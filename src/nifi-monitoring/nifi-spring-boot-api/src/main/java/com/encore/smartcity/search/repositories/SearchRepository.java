package com.encore.smartcity.search.repositories;

import com.encore.smartcity.search.PGSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SearchRepository extends JpaRepository<PGSearch, Long> {

    Page<PGSearch> findPGSearchByLevel(Long level, Pageable pageable);

    Page<PGSearch> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<PGSearch> findPGSearchByLevelAndNameContainingIgnoreCase(Long level, String name, Pageable pageable);

    @Query(value = "select distinct (p.level) from PGSearch p")
    List<Long> getAllAvailableLevel();

}
