package com.example.sweater;

import au.com.bytecode.opencsv.CSVReader;
import com.example.sweater.domain.Machine;
import com.example.sweater.repos.MachineRepo;
import com.example.sweater.utils.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RestController
public class JsonController {

    private static final String DIRECTORY = "src/main/resources";
    private static final String DEFAULT_FILE_NAME = "carData.csv";

    private static final String DEFAULT_VALUE = "null";

    @Autowired
    private ServletContext servletContext;

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

    @GetMapping("/download-csv")
    public ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        File file = new File(DIRECTORY + "/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length()) //
                .body(resource);
    }

    @PostMapping("upload-csv")
    public String uploadCSV(@RequestParam("upload-csv") MultipartFile uploadCsv) {
        String[] captions;
        String[] values;
        Map<String, String> machineMap = new HashMap<>();
        Machine machine;

        try (InputStream stream = uploadCsv.getInputStream();
             CSVReader csvReader = new CSVReader(new InputStreamReader(stream, "UTF-8"))) {

            String[] nextLine;

            if ((nextLine = csvReader.readNext()) != null) {
                captions = nextLine;

                while ((values = csvReader.readNext()) != null) {
                    if (captions.length == values.length) {
                        for (int i = 0; i < captions.length; i++) {
                            machineMap.put(captions[i], values[i]);
                        }
                        machine = new Machine();
                        machine.setOwner(machineMap.get("owner"));
                        machine.setAvailable(machineMap.get("available"));
                        machine.setCountry(machineMap.get("country"));
                        machine.setCurrency(machineMap.get("currency"));
                        machine.setMachineInfo(machineMap.get("machineInfo"));
                        machine.setMachineType(machineMap.get("machineType"));
                        machine.setPhotos(machineMap.get("photos"));
                        machine.setSourceId(machineMap.get("sourceId"));
                        machine.setPrice(machineMap.get("price"));
                        machine.setSource(machineMap.get("source"));
                        machine.setUrl(machineMap.get("url"));

                        machineRepo.save(machine);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Success";
    }

    @PostMapping("delete-all")
    public void deleteAll() {
        machineRepo.deleteAll();
    }

    @PostMapping("add-all")
    public List<Machine> addAll(@RequestBody List<Machine> machines) {
        machineRepo.saveAll(machines);
        return machines;
    }

}
