package com.example.sweater;

import au.com.bytecode.opencsv.CSVReader;
import com.example.sweater.domain.Car;
import com.example.sweater.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private CarRepo carRepo;


    @GetMapping
    public String main(Map<String, Object> model) {
        return "main";
    }


    @PostMapping("/csv")
    public String uploadCSV(@RequestParam("csv") MultipartFile csv, Map<String, Object> model) {
        String[] captions;
        String[] values;
        Map<String, String> carMap = new HashMap<>();
        Car car;

        try (InputStream stream = csv.getInputStream();
             CSVReader csvReader = new CSVReader(new InputStreamReader(stream, "UTF-8"))) {

            String[] nextLine;

            if ((nextLine = csvReader.readNext()) != null) {
                captions = nextLine;

                while ((values = csvReader.readNext()) != null) {
                    if (captions.length == values.length) {
                        for (int i = 0; i < captions.length; i++) {
                            carMap.put(captions[i], values[i]);
                        }
                        car = new Car();
                        car.setOwner(carMap.get("owner"));
                        car.setAvailable(carMap.get("available"));
                        car.setCountry(carMap.get("country"));
                        car.setCurrency(carMap.get("currency"));
                        car.setMachineInfo(carMap.get("machineInfo"));
                        car.setMachineType(carMap.get("machineType"));
                        car.setPhotos(carMap.get("photos"));
                        car.setSourceId(carMap.get("sourceId"));
                        car.setPrice(carMap.get("price"));
                        car.setSource(carMap.get("source"));
                        car.setUrl(carMap.get("url"));

                        carRepo.save(car);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DONE");
        return "main";
    }
}
