package com.example.empapp;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

//@Model
@Named
@SessionScoped
public class HelloController implements Serializable {

//    @Getter
//    private String message;

    @Getter
    @Setter
    private String name;

    public String sayHello() {
        String message = "Hello " + name;
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("message", message);
        return "hello.xhtml?faces-redirect=true";
    }
}
