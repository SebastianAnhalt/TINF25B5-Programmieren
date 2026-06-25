package de.dhbwka.java.exercise.libary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Libary {
    private List<Book> books = new ArrayList<>();
    private File dir = new File("archive");
    private File archive = new File(dir,"archive");

    public Libary() throws Exception {
        try {
            dir.mkdir();
            archive.createNewFile();
            readFile(archive);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public String readAllBooks() throws IOException {
        StringBuilder books = new StringBuilder();
        for (Book book: this.books) {
            books.append(book.toString()).append("\n");
        }
        return books.toString();
    }

    public void addBook(Book book) throws Exception {
        books.add(book);
        writeBook(book);
    }


    private void writeBook(Book book) throws Exception {
        if (!archive.exists()) {
            throw new Exception("Book does not exist");
        }

        if (archive.canWrite()) {
            FileWriter writer = new FileWriter(archive, true);
            writer.write(book.toString()+ "\n");
            writer.close();
        }
    }

    private  void writeBooks(List<Book> books) throws Exception {
        for (Book book : books) {
            writeBook(book);
        }
    }

    private void deleteBooks(List<Book> books) throws Exception {
        archive.delete();
    }

    private void readFile(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.lines().forEach(line -> {
            String[] split = line.split(";");
            Book book = new Book(split[0],split[1],split[2],split[3]);
            books.add(book);
        });
        reader.close();
    }
}
