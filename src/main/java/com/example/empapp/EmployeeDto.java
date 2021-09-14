package com.example.empapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private String id;

    private String name;

    private String cardNumber;

    private String type;

    private List<String> skills;

    private String filename;

    private LocalDateTime startedAt;

    public EmployeeDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
