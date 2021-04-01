package com.example.sweater.repos;

import com.example.sweater.domain.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MachineRepo extends JpaRepository<Machine, String> {


    List<Machine> findByOwner(String owner);

    List<Machine> findByAvailable(String available);

    List<Machine> findByCountry(String country);

    List<Machine> findByCurrency(String currency);

    List<Machine> findByMachineInfo(String machineInfo);

    List<Machine> findByMachineType(String machineType);

    List<Machine> findByPhotos(String photos);

    Machine findBySourceId(String sourceId);

    List<Machine> findByPrice(String price);

    List<Machine> findBySource(String source);

    List<Machine> findByUrl(String url);

    List<Machine> findByOwnerAndAvailableAndCountryAndCurrency(@RequestParam(required = false) String owner,
                                                               @RequestParam(required = false) String available,
                                                               @RequestParam(required = false) String country,
                                                               @RequestParam(required = false) String currency);
}
