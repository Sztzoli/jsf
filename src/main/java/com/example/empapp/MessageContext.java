package com.example.empapp;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MessageContext {

    public void setFlashMessage(String message) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(message));
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);
    }
}
