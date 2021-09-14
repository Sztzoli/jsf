package com.example.empapp;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

@Model
public class EmployeesController {

    @Inject
    private EmployeeService employeeService;

    @Getter
    private List<EmployeeDto> employees;

    @Getter @Setter
    private String query;

    public void initEmployees() {
        employees = employeeService.listEmployees(query);
    }

    public String deleteEmployee(String id) {
        employeeService.deleteEmployee(id);
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage("Employee has deleted"));
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);
        return "employees.xhtml?faces-redirect=true";
    }
}
