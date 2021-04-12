package com.example.sweater.repos;

import com.example.sweater.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Long> {

    Page<Company> findAll(Pageable pageable);

    Optional<Company> findById(Long companyId);
}
