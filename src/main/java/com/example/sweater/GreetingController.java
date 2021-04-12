package com.example.sweater;

import au.com.bytecode.opencsv.CSVReader;
import com.example.sweater.domain.Machine;
import com.example.sweater.repos.MachineRepo;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/greeting")
public class GreetingController {

    private static final String DIRECTORY = "src/main/resources";
    private static final String DEFAULT_FILE_NAME = "carData.csv";

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private MachineRepo machineRepo;


    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Machine> machines = machineRepo.findAll();
        model.put("machines", machines);
        return "main";
    }
    @PostMapping("/getMachine")
    public String selectCountry(@RequestParam("country") String country, Map<String, Object> model){
        Iterable<Machine> machines = machineRepo.findAll();
        model.put("machines", machines);
        return "main";
    }
    @GetMapping("/download")
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
                .contentLength(file.length())
                .body(resource);
    }


    @PostMapping("/csv")
    public String uploadCSV(@RequestParam("csv") MultipartFile csv, Map<String, Object> model) {
        String[] captions;
        String[] values;
        Map<String, String> machineMap = new HashMap<>();
        Machine machine;

        try (InputStream stream = csv.getInputStream();
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
        return "main";
    }

}
