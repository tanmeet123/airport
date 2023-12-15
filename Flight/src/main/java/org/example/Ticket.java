package org.example;

public class Ticket {
    private String title;
    private Integer ID;
    private Float price;
    private String category;

    public Ticket() {
    }

    public Ticket(String title, Integer ID, Float price, String category) {
        this.title = title;
        this.ID = ID;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
