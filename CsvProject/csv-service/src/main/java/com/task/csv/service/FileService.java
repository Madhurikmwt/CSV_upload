package com.task.csv.service;

import com.task.csv.model.CsvFileEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public interface FileService {
        List<CsvFileEntity> fileToCsvEntity(InputStream inputStream);

}
