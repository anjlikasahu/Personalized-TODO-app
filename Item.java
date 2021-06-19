package com.example.mainproject;


public class Item {
    private int id;
    private String item;
    private int quantity;
    private int size;
    private String color;
    private String date;

    public Item(String item, int quantity, int size, String color, String date) {
        this.item = item;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.date = date;
    }

    public Item(int id, String item, int quantity, int size, String color, String date) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.date = date;
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
