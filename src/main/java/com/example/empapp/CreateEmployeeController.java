package com.example.empapp;

import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;
import java.util.ResourceBundle;

@Model
public class CreateEmployeeController {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private MessageContext messageContext;

    @Getter
    private CreateEmployeeCommand command = new CreateEmployeeCommand();

    @Getter
    private List<String> types;

    @Getter
    private List<Skill> skills;


    @PostConstruct
    public void init() {
        types = employeeService.listTypes();
        skills = employeeService.listSkills();
    }

    public String saveEmployee() {
        if (employeeService.isEmployeeWithCardNumber(command.getCardNumber())) {
            FacesContext.getCurrentInstance()
                    .addMessage("create-form:card-number-input", new FacesMessage("Card number already used"));
            return null;
        }

        employeeService.createEmployee(command);


        ResourceBundle resourceBundle = FacesContext.getCurrentInstance().getApplication()
                .evaluateExpressionGet(FacesContext.getCurrentInstance(),
                        "#{msgs}", ResourceBundle.class);


        String message = resourceBundle.getString("employee-created");

        messageContext.setFlashMessage(message);

        return "employees.xhtml?faces-redirect=true";
    }
}
