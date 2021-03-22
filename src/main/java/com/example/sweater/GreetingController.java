package com.example.sweater;

import au.com.bytecode.opencsv.CSVReader;
import com.example.sweater.domain.Car;
import com.example.sweater.repos.CarRepo;
import com.example.sweater.utils.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GreetingController {

    private static final String DIRECTORY = "src/main/resources";
    private static final String DEFAULT_FILE_NAME = "carData.csv";

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private CarRepo carRepo;


    @GetMapping
    public String main(Map<String, Object> model) {
        return "main";
    }
    @RequestMapping(value = "/download", method = RequestMethod.GET)
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


    @PostMapping("csv")
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
