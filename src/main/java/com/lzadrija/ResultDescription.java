package com.lzadrija;

public class ResultDescription {

    protected String description;
    protected boolean success;

    public ResultDescription() {
    }

    public ResultDescription(String description, boolean success) {
        this.description = description;
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "RegistrationDescription{"
               + "description=" + description
               + ", success=" + success
               + '}';
    }

}
