package com.example.empapp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.Part;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeCommand {

    @NotBlank(message = "{create-employee-command.not-blank}")
    private String name;

    private String cardNumber;

    private String type;

    private List<String> skills;

    private Part file;

    private LocalDateTime startedAt = LocalDateTime.now();

    public CreateEmployeeCommand(String name) {
        this.name = name;
    }
}
