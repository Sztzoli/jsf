package com.example.empapp;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Model
public class UpdateEmployeeController {

    @Getter
    private UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand();

    @Getter
    @Setter
    private String id;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private MessageContext messageContext;

    @Inject
    private ModelMapper modelMapper;

    public void findEmployee() {
        EmployeeDto employeeDto = employeeService.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));
        updateEmployeeCommand = modelMapper.map(employeeDto, UpdateEmployeeCommand.class);
    }

    public String update() {
        employeeService.updateEmployee(updateEmployeeCommand);
        messageContext.setFlashMessage("Employee has updated");
        return "employees.xhtml?faces-redirect=true";
    }
}
