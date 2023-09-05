package com.example.myproducts.Model;

public class Product {

    private String name;
    private String image;
    private String price;
    private String menu_id;
    private String discr;

    public Product(String name, String image, String price, String menu_id, String discr) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.menu_id = menu_id;
        this.discr = discr;
    }

    public Product() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getDiscr() {
        return discr;
    }

    public void setDiscr(String discr) {
        this.discr = discr;
    }
}
