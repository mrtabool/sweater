package com.example.sweater.exeption;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(Long id) {
        super("Could not find company " + id);
    }
}
