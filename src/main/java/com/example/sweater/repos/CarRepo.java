package com.example.sweater.repos;

import com.example.sweater.domain.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepo extends CrudRepository<Car, Integer> {
}
