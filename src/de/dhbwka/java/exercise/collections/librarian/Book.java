package de.dhbwka.java.exercise.collections.librarian;

/**
 * Represents a book with author, title, publication year, and publisher.
 */
public class Book {
    // Private fields for encapsulation
    private String author;
    private String title;
    private String year;
    private String publisher;

    /**
     * Constructor to initialize a Book object.
     *
     * @param author     The author of the book.
     * @param title      The title of the book.
     * @param year       The publication year of the book.
     * @param publisher  The publisher of the book.
     */
    public Book(String author, String title, String year, String publisher) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.publisher = publisher;
    }

    // Getters and Setters for each field
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Returns a string representation of the Book object.
     *
     * @return A string containing book details.
     */
    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
