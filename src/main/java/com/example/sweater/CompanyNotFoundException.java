package com.example.sweater;

public class CompanyNotFoundException extends RuntimeException {

    CompanyNotFoundException(Long id) {
        super("Could not find company " + id);
    }
}
