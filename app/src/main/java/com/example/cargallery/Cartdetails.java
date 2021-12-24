package com.example.cargallery;

public class Cartdetails {
    public String carimage;
    public String productmodel, productname, productprice,quantity;

public Cartdetails(){}
    public Cartdetails(String carimage, String productmodel, String productname, String productprice, String quantity) {
        this.carimage = carimage;
        this.productmodel = productmodel;
        this.productname = productname;
        this.productprice = productprice;
        this.quantity = quantity;

    }

    public String getCarimage() {
        return carimage;
    }

    public void setCarimage(String carimage) {
        this.carimage = carimage;
    }

    public String getProductmodel() {
        return productmodel;
    }

    public void setProductmodel(String productmodel) {
        this.productmodel = productmodel;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
