package de.dhbwka.java.exercise.collections.librarian;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a library that manages a collection of books.
 * Supports importing from BibTeX files and sorting by author, year, title, or publisher.
 */
public class Library {
    private final List<Book> books;

    /**
     * Constructor initializes an empty library.
     */
    public Library() {
        this.books = new ArrayList<>();
    }

    /**
     * Adds a book to the library.
     *
     * @param book The book to add.
     */
    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    /**
     * Imports books from a BibTeX file.
     *
     * @param filePath Path to the BibTeX file.
     * @throws IOException If the file cannot be read.
     */
    public void importFromBibTeX(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String author = null;
            String title = null;
            String year = null;
            String publisher = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("    author = ")) {
                    author = extractValue(line);
                } else if (line.startsWith("    title = ")) {
                    title = extractValue(line);
                } else if (line.startsWith("    year = ")) {
                    year = extractValue(line);
                } else if (line.startsWith("    publisher = ")) {
                    publisher = extractValue(line);
                }

                // Add book when all fields are collected
                if (author != null && title != null && year != null && publisher != null &&
                        line.trim().equals("}")) {
                    books.add(new Book(author, title, year, publisher));
                    author = title = year = publisher = null;
                }
            }
        }
    }

    /**
     * Extracts the value from a BibTeX line (e.g., "  author = {"Max Mustermann"}" → "Max Mustermann").
     *
     * @param line A BibTeX line.
     * @return The extracted value.
     */
    private String extractValue(String line) {
        int start = line.indexOf('{') + 1;
        int end = line.lastIndexOf('}');
        return line.substring(start, end);
    }

    /**
     * Returns the list of books.
     *
     * @return Unmodifiable list of books.
     */
    public List<Book> getBooks() {
        return List.copyOf(books);
    }

    /**
     * Sorts books by a given criterion.
     *
     * @param criterion "author", "year", "title", or "publisher".
     * @return A new sorted list of books.
     */
    public List<Book> sortBooks(String criterion) {
        if (criterion == null || books.isEmpty()) {
            return new ArrayList<>(books);
        }

        List<Book> sortedBooks = new ArrayList<>(books);

        switch (criterion.toLowerCase()) {
            case "author":
                sortedBooks.sort(Comparator.comparing(Book::getAuthor));
                break;
            case "year":
                sortedBooks.sort(Comparator.comparing(Book::getYear));
                break;
            case "title":
                sortedBooks.sort(Comparator.comparing(Book::getTitle));
                break;
            case "publisher":
                sortedBooks.sort(Comparator.comparing(Book::getPublisher));
                break;
            default:
                System.err.println("Invalid criterion. Using unsorted list.");
        }

        return sortedBooks;
    }
}
