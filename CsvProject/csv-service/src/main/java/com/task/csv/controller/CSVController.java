package com.task.csv.controller;

import com.task.csv.dto.ResponseMessage;
import com.task.csv.exception.RecordNotFoundException;
import com.task.csv.model.CsvFileEntity;
import com.task.csv.service.CSVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/csv")
public class CSVController {

    private static final Logger logger = LoggerFactory.getLogger(CSVController.class);

    @Autowired
    CSVService csvService;

    //Get CSV Record on the basis of Primary Key Attribute
    @GetMapping("/csvRecord/{id}")
    public ResponseEntity<CsvFileEntity> getCsvRecord(@PathVariable String id){
        try {
        CsvFileEntity record = csvService.getRecord(id);

            if (record == null) {
                throw new RecordNotFoundException("Record not found");
            }

            return ResponseEntity.status(HttpStatus.OK).body(record);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Upload CSV File
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFileToDB(@RequestPart("file") MultipartFile file) {
        String message = "";
        try {
            message = file.getOriginalFilename() + " Uploaded";
            if (csvService.hasCSVFormat(file)) {
                csvService.save(file);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
        message = "Please upload csv/excel file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    //Delete CSV Record on the basis of Primary Key Attribute
    @DeleteMapping("/csvRecord/{id}")
    public ResponseEntity<ResponseMessage> deleteRecord(@PathVariable String id){
        csvService.deleteRecord(id);
        String message = "Record has been successfully deleted ";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

    }
}
