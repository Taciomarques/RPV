package com.unipampa.padel.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="dialogMB")
@RequestScoped
public class DialogMB {
    private boolean _isDialogVisible;

    public String hideDialog() {
        setDialogVisible(false);
        return null;
    }

    public String showDialog() {
        setDialogVisible(true);
        return null;
    }

    public boolean getDialogVisible() {
        return _isDialogVisible;
    }

    public void setDialogVisible(boolean isDialogVisible) {
        _isDialogVisible = isDialogVisible;
    }
}
