package de.dhbwka.java.exercise.libary;

import java.util.HashMap;

public class Book {
    public final String title;
    public final String author;
    public final String publisher;
    public final String year;

    public Book(String title, String author, String publisher, String year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    @Override
    public String toString() {
        return title + ";" + author + ";" + publisher + ";" + year;
    }

    public HashMap<String,String> getBookdata() {
        HashMap<String,String> bookdata = new HashMap<>();
        bookdata.put("title", title);
        bookdata.put("author", author);
        bookdata.put("publisher", publisher);
        bookdata.put("year", year);
        return bookdata;
    }
}
