package com.task.csv.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private int statusCode;

}
