package com.example.sweater;

import com.example.sweater.domain.Company;
import com.example.sweater.repos.CompanyRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Example;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Api(value = "Data of Companies.", description = "Operations with companies database.")
public class CompanyController {

    private final CompanyRepo companyRepo;

    @GetMapping("/api/v1/companies")
    @ApiOperation(value = "Shows all companies with sorting and paging.",
            notes = "Provide array params sort like<id,asc> or <title,desc>, page like<0> and size like<20>.",
            response = Page.class)
    public Page<Company> findAll(@ApiParam(name = "(Example - id,asc)",value = "Array of two parameters you need, to sort the companies on page.", required = true) @RequestParam Optional<String>[] sort,
                                 @ApiParam(value = "The companies page you need to check out.") @RequestParam Optional<Integer> page,
                                 @ApiParam(value = "Amount of companies you want to get on one page.", required = true) @RequestParam Integer size) {

        return companyRepo.findAll(PageRequest.of(
                page.orElse(0), size,
                (sort[1].get().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC,
                sort[0].orElse("id")));
    }

    @GetMapping("/api/v1/companies/{companyId}")
    @ApiOperation(value = "Finds company by id.",
            notes = "Provide an id to look up specific company.",
            response = Company.class)
    public Company findById(@ApiParam(value = "Id value for the company you need to retrieve.", required = true) @PathVariable Long companyId) {
        return companyRepo.findById(companyId).orElseThrow(() -> new CompanyNotFoundException(companyId));
    }

    @PostMapping("/api/v1/companies")
    @ApiOperation(value = "Creates a new company.",
            notes = "Add the company to the request body in json format.",
            response = Company.class)
    public Company create(@ApiParam(value = "The company you need to add to the database.", required = true) @RequestBody Company company) {
            return companyRepo.save(company);
    }

    @PutMapping("/api/v1/companies/{companyId}")
    @ApiOperation(value = "Updates company data for the specified id.",
            notes = "Provide the identifier by which the data will be updated and add the company to the request body in json format.",
            response = Company.class)
    public Company update(@ApiParam(value = "Id by which the new company will be overwritten.", required = true) @PathVariable Long companyId,
                          @ApiParam(value = "The new company you need to overwrite at the specified id.", required = true) @RequestBody Company newCompany) {

        return companyRepo.findById(companyId)
                .map(company -> {
                    company.setTitle(newCompany.getTitle());
                    company.setFounded(newCompany.getFounded());
                    return companyRepo.save(company);
                })
                .orElseGet(() -> {
                    newCompany.setId(companyId);
                    return companyRepo.save(newCompany);
                });
    }

    @DeleteMapping("/api/v1/companies/{companyId}")
    @ApiOperation(value = "Removes company by id.", notes = "Provide an id to delete specific company.")
    public void deleteEmployee(@ApiParam(value = "Id of the company that you need to remove from database.", required = true) @PathVariable Long companyId) {

        try {
            companyRepo.deleteById(companyId);
        } catch (EmptyResultDataAccessException e) {
            throw new CompanyNotFoundException(companyId);
        }
    }
}
