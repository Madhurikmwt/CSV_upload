package com.task.csv.service;

import com.task.csv.model.CsvFileEntity;
import com.task.csv.repository.CSVRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CSVServiceImpl implements CSVService{

    private static final Logger logger = LoggerFactory.getLogger(CSVServiceImpl.class);

    @Autowired
    CSVRepository repository;

    public String TYPE = "text/csv";

    //Check for CSV file format
    @Override
    public boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equalsIgnoreCase(file.getContentType())) {
            return false;
        }
        return true;
    }

    //Saving CSV file into database
    @Override
    public void save(MultipartFile file) throws IOException {
        List<CsvFileEntity> recordList = fileToCsvRecord(file.getInputStream());

        if (recordList != null)
            repository.saveAll(recordList);
    }

    //Parsing CSV file records to Entity Record
    public List<CsvFileEntity> fileToCsvRecord(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<CsvFileEntity> recordList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CsvFileEntity record = new CsvFileEntity(
                        Long.valueOf(csvRecord.get("primary_key")),
                        csvRecord.get("name"),
                        csvRecord.get("description"),
                        changeIntoDate(csvRecord.get("updated_timestamp"))
                );

                recordList.add(record);
            }

            return recordList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }  catch (ParseException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    //Converting timestamp column from uploaded file
    public Timestamp changeIntoDate(String dateFromCsv) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date date = format.parse(dateFromCsv);
        Timestamp ts = new Timestamp(date.getTime());
        return ts;
    }

    //Get record from database on the basis of primary key
    @Override
    public CsvFileEntity getRecord(String primaryValue){
        CsvFileEntity record = repository.findByPrimaryKey(primaryValue);
        return record;
    }

    //Delete record from database on the basis of primary key
    @Override
    public void deleteRecord(String primaryValue){
        CsvFileEntity record = repository.findByPrimaryKey(primaryValue);
        repository.delete(record);
    }

}
