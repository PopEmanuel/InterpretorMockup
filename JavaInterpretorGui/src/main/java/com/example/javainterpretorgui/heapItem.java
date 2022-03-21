package com.example.javainterpretorgui;

public class heapItem {
    private int adress;
    private String value;

    public heapItem(int adres, String val)
    {
        this.adress = adres;
        this.value = val;
    }

    public int getAdress() {
        return adress;
    }

    public String getValue() {
        return value;
    }
}
