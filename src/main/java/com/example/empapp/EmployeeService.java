package com.example.empapp;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
@Slf4j
public class EmployeeService {

    private List<Employee> employees = new CopyOnWriteArrayList<>();

    @Inject
    private ModelMapper mapper;

    private Path tempDir;

    @PostConstruct
    public void initEmployee() {
        createEmployee(new CreateEmployeeCommand("John Doe"));
        createEmployee(new CreateEmployeeCommand("Jane Doe"));

        try {
            tempDir = Files.createTempDirectory("empapp");
            log.info(tempDir.toString());
        } catch (IOException ioException) {
            throw new IllegalStateException("Cannot create dir", ioException);
        }
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        log.info("Employee created: " + command.getName());
        Employee employee = mapper.map(command, Employee.class);
        saveFile(command, employee);
        employee.setId(UUID.randomUUID().toString());
        employees.add(employee);
        return mapper.map(employee, EmployeeDto.class);
    }

    public List<EmployeeDto> listEmployees(String query) {
        Type targetListType = new TypeToken<List<EmployeeDto>>() {
        }.getType();
        List<Employee> filteredEmployees = employees.stream()
                .filter(e -> query == null || e.getName().startsWith(query))
                .collect(Collectors.toList());
        return mapper.map(filteredEmployees, targetListType);
    }

    public Optional<EmployeeDto> updateEmployee(UpdateEmployeeCommand command) {
        return employees.stream()
                .filter(e -> e.getId().equals(command.getId()))
                .peek(e -> mapper.map(command, e))
                .map(e -> mapper.map(e, EmployeeDto.class))
                .findFirst();
    }

    public void deleteEmployee(String id) {
        Optional<Employee> employee = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        employee.ifPresent(value -> employees.remove(value));
    }

    public Optional<EmployeeDto> findById(String id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .map(e -> mapper.map(e, EmployeeDto.class))
                .findFirst();
    }

    public List<String> listTypes() {
        return Arrays.asList("full-time", "part-time");
    }

    public List<Skill> listSkills() {
        return Arrays.asList(new Skill("java", "java programing"), new Skill("jsf", "Java ServerFaces"));
    }

    private void saveFile(CreateEmployeeCommand command, Employee employee) {
        if (command.getFile() != null) {
            String filename = command.getFile().getSubmittedFileName();
            try {
                Files.copy(command.getFile().getInputStream(), tempDir.resolve(filename));
            } catch (IOException ioe) {
                throw new IllegalStateException("Can not copy to temp dir", ioe);
            }
            employee.setFilename(filename);
        }
    }

    public Path getFile(String filename) {
        return tempDir.resolve(filename);
    }

    public boolean isEmployeeWithCardNumber(String cardNumber) {
        return employees.stream().anyMatch(e -> e.getCardNumber() != null && e.getCardNumber().equals(cardNumber));
    }
}
