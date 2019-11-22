package com.example.acer.litepaltest;

import org.litepal.crud.DataSupport;

/**
 * Created by acer on 2017/2/23.
 */

public class Book extends DataSupport{
    private int id;
    private String author;
    private double price;
    private int page;
    private String name;
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getPage(){
        return page;
    }

    public void setPage(int page){
        this.page = page;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    }
