package com.example.empapp;

import lombok.Getter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleController implements Serializable {

    @Getter
    private Locale locale = new Locale("hu");

    public String changeLocale(String lang) {
        locale = new Locale(lang);
        return "employees.xhtml?faces-redirect=true";
    }
}
