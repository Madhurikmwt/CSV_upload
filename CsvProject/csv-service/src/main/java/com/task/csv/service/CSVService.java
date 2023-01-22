package com.task.csv.service;

import com.task.csv.model.CsvFileEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface CSVService {
    boolean hasCSVFormat(MultipartFile file);
    public void save(MultipartFile file) throws IOException;

    public CsvFileEntity getRecord(String primaryValue);

    public void deleteRecord(String primaryValue);

}
