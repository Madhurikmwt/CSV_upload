package com.task.csv.model;


import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.sql.Timestamp;


@Entity
@Table(name = "CsvTable")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CsvFileEntity {

    @Id
    @Column(name = "primary_key")
    private long primaryKey;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private Timestamp updated_timestamp;

}
