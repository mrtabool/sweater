package com.example.sweater.service;

import com.example.sweater.domain.Company;
import com.example.sweater.exeption.CompanyNotFoundException;
import com.example.sweater.repos.CompanyRepo;
import com.example.sweater.dto.CompanyDto;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.stream.Collectors;


@Service
public class CompanyService {

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    ConverterService converterService;

    public Page<CompanyDto> findAll(Pageable pageable) {
        Page<Company> companyList = companyRepo.findAll(pageable);
        return companyList.map(converterService::convertToDto);
    }

    public CompanyDto findById(Long companyId) {
        return converterService.convertToDto(companyRepo.findById(companyId).orElseThrow(() -> new CompanyNotFoundException(companyId)));
    }

    public CompanyDto create(Company company) {
        return converterService.convertToDto(companyRepo.save(company));
    }

    public CompanyDto update(Long companyId, Company newCompany) {

        return converterService.convertToDto(companyRepo.findById(companyId)
                .map(company -> {
                    company.setTitle(newCompany.getTitle());
                    company.setFounded(newCompany.getFounded());
                    return companyRepo.save(company);
                })
                .orElseGet(() -> {
                    newCompany.setId(companyId);
                    return companyRepo.save(newCompany);
                }));
    }

    public void deleteCompany(Long companyId) {
        try {
            companyRepo.deleteById(companyId);
        } catch (EmptyResultDataAccessException e) {
            throw new CompanyNotFoundException(companyId);
        }
    }
}
