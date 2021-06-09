package com.example.sweater;

import com.example.sweater.domain.Company;
import com.example.sweater.dto.CompanyDto;
import com.example.sweater.repos.CompanyRepo;
import com.example.sweater.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@Api(value = "Data of Companies.", description = "Operations with companies database.")
public class CompanyController {

    @Autowired
    private final CompanyRepo companyRepo;

    @Autowired
    private final CompanyService companyService;

    @GetMapping("/api/v1/companies")
    @ApiOperation(value = "Shows all companies with sorting and paging.",
            notes = "Provide array params sort like<id,asc> or <title,desc>, page like<0> and size like<20>.",
            response = Page.class)
    public Page<CompanyDto> findAll(@ApiParam(value = "Pageable ", required = true) Pageable pageable) {
        return companyService.findAll(pageable);
    }

    @GetMapping("/api/v1/companies/{companyId}")
    @ApiOperation(value = "Finds company by id.",
            notes = "Provide an id to look up specific company.",
            response = Company.class)
    public CompanyDto findById(@ApiParam(value = "Id value for the company you need to retrieve.", required = true) @PathVariable Long companyId) {
        return companyService.findById(companyId);
    }

    @PostMapping("/api/v1/companies")
    @ApiOperation(value = "Creates a new company.",
            notes = "Add the company to the request body in json format.",
            response = Company.class)
    public CompanyDto create(@ApiParam(value = "The company you need to add to the database.", required = true) @RequestBody Company company) {
            return companyService.create(company);
    }

    @PutMapping("/api/v1/companies/{companyId}")
    @ApiOperation(value = "Updates company data for the specified id.",
            notes = "Provide the identifier by which the data will be updated and add the company to the request body in json format.",
            response = Company.class)
    public CompanyDto update(@ApiParam(value = "Id by which the new company will be overwritten.", required = true) @PathVariable Long companyId,
                          @ApiParam(value = "The new company you need to overwrite at the specified id.", required = true) @RequestBody Company newCompany) {

        return companyService.update(companyId, newCompany);
    }

    @DeleteMapping("/api/v1/companies/{companyId}")
    @ApiOperation(value = "Removes company by id.", notes = "Provide an id to delete specific company.")
    public void deleteCompany(@ApiParam(value = "Id of the company that you need to remove from database.", required = true) @PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
    }
}
