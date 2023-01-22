package com.task.csv.repository;

import com.task.csv.model.CsvFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CSVRepository extends JpaRepository<CsvFileEntity,Long> {
    CsvFileEntity findByPrimaryKey(String id);
}
