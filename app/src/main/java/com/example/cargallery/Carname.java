package com.example.cargallery;

public class Carname {



    public String carproductname;

   public String Image;
    public  Carname(){}

    public Carname(String carproductnames) {
        carproductname = carproductnames;


    }

    public String getName() {
        return  carproductname;
    }

    public void setName(String name) {
        carproductname = name;
    }
}