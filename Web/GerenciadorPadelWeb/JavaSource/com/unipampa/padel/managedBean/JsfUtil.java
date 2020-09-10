package com.unipampa.padel.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JsfUtil {


    public static void addErrorMessage(String msg) {

        System.out.println("classe JsfUtil - A1");

        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);

        System.out.println("classe JsfUtil - A2");

    }

    public static void addSuccessMessage(String msg) {

        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);

        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);

    }
    
    

}

