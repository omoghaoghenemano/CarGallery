package com.example.cargallery;

public class Method {
    public String productname;
    public String productmodel;
    public String productprice;
    public int imagename;
    public Method(){

    }

    public Method(String Productname, String Productmodel, String Productprice) {
        this.productname = Productname;
        this.productmodel = Productmodel;
        this.productprice = Productprice;

    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductmodel() {
        return productmodel;
    }

    public void setProductmodel(String productmodel) {
        this.productmodel = productmodel;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }
}
