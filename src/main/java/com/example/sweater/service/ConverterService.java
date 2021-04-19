package com.example.sweater.service;

import com.example.sweater.domain.Company;
import com.example.sweater.dto.CompanyDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterService {

    @Autowired
    ModelMapper modelMapper;

    public CompanyDto convertToDto(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }
}
