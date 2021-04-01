package com.example.sweater;

import com.example.sweater.domain.Machine;
import com.example.sweater.repos.MachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@RestController
public class JsonController {
    private static final String DEFAULT_VALUE = "null";

    @Autowired
    private MachineRepo machineRepo;

    @GetMapping("all-machines")
    public List<Machine> getJson() {
        return machineRepo.findAll();
    }

    @GetMapping("source-id")
    public Machine getData(@RequestParam("sourceId") String sourceId) {
        return machineRepo.findBySourceId(sourceId);
    }

    @GetMapping("amount-by-model")
    public Integer amountByModel(@RequestParam String model) {
        List<Machine> machines = machineRepo.findByMachineType(model);
        return machines.size();
    }


    @GetMapping("amount-each-model")
    public Map<String, String> amountEachModel() {
        Map<String, String> amountEachModel = new HashMap<>();
        Set<String> models = new HashSet<>();
        Iterable<Machine> machines = machineRepo.findAll();


        for (Machine machine : machines) {
            models.add(machine.getMachineType());
        }

        for (String model : models) {
            String amount = String.valueOf(machineRepo.findByMachineType(model).size());
            amountEachModel.put(model, amount);
        }


        return amountEachModel;
    }

    @PostMapping("create")
    public void create(@RequestParam("owner") String owner,
                       @RequestParam("available") String available,
                       @RequestParam("country") String country,
                       @RequestParam("currency") String currency,
                       @RequestParam("machineInfo") String machineInfo,
                       @RequestParam("machineType") String machineType,
                       @RequestParam("photos") String photos,
                       @RequestParam("sourceId") String sourceId,
                       @RequestParam("price") String price,
                       @RequestParam("source") String source,
                       @RequestParam("url") String url) {

        Machine machine = new Machine();

        machine.setOwner(owner);
        machine.setAvailable(available);
        machine.setCountry(country);
        machine.setCurrency(currency);
        machine.setMachineInfo(machineInfo);
        machine.setMachineType(machineType);
        machine.setPhotos(photos);
        machine.setSourceId(sourceId);
        machine.setPrice(price);
        machine.setSource(source);
        machine.setUrl(url);

        machineRepo.save(machine);
    }

    @PostMapping("update")
    public String update(@RequestParam(value = "sourceId", required = false) String sourceId,
                         @RequestParam(value = "owner", required = false) String owner,
                         @RequestParam(value = "available", required = false) String available,
                         @RequestParam(value = "country", required = false) String country,
                         @RequestParam(value = "currency", required = false) String currency,
                         @RequestParam(value = "machineInfo", required = false) String machineInfo,
                         @RequestParam(value = "machineType", required = false) String machineType,
                         @RequestParam(value = "photos", required = false) String photos,
                         @RequestParam(value = "price", required = false) String price,
                         @RequestParam(value = "source", required = false) String source,
                         @RequestParam(value = "url", required = false) String url) {

        if (sourceId == null) return "Please enter sourceId!";



        Machine machine = machineRepo.findBySourceId(sourceId);


        if (machine != null) {
            if (owner != null) machine.setOwner(owner);
            if (available != null) machine.setAvailable(available);
            if (country != null) machine.setCountry(country);
            if (currency != null) machine.setCurrency(currency);
            if (machineInfo != null) machine.setMachineInfo(machineInfo);
            if (machineType != null) machine.setMachineType(machineType);
            if (photos != null) machine.setPhotos(photos);
            if (price != null) machine.setPrice(price);
            if (source != null) machine.setSource(source);
            if (url != null) machine.setUrl(url);

            machineRepo.save(machine);

            return "Success!";
        }
        return "There is no such machine.";
    }

    @GetMapping("multiple-field")
    public List<Machine> getMultipleField(@RequestParam(value = "owner", required = false) String owner,
                                          @RequestParam(value = "available", required = false) String available,
                                          @RequestParam(value = "country", required = false) String country,
                                          @RequestParam(value = "currency", required = false) String currency,
                                          @RequestParam(value = "machineInfo", required = false) String machineInfo,
                                          @RequestParam(value = "machineType", required = false) String machineType,
                                          @RequestParam(value = "photos", required = false) String photos,
                                          @RequestParam(value = "price", required = false) String price,
                                          @RequestParam(value = "source", required = false) String source,
                                          @RequestParam(value = "url", required = false) String url) {


        Machine machine = new Machine();
        if(owner != null) machine.setOwner(owner);
        if(available != null) machine.setAvailable(available);
        if(country != null) machine.setCountry(country);
        if(currency != null) machine.setCurrency(currency);
        if(machineInfo != null) machine.setMachineInfo(machineInfo);
        if(machineType != null) machine.setMachineType(machineType);
        if(photos != null) machine.setPhotos(photos);
        if(price != null) machine.setPrice(price);
        if(source != null) machine.setSource(source);
        if(url != null) machine.setUrl(url);

        Example<Machine> example = Example.of(machine);
        List<Machine> result = machineRepo.findAll(example);


        return result;
    }

}
