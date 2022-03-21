package com.example.javainterpretorgui;

public class symItem {
    private String varname;
    private String value;

    public symItem(String adres, String val)
    {
        this.varname = adres;
        this.value = val;
    }


    public String getVarname() {
        return varname;
    }

    public String getValue() {
        return value;
    }
}
