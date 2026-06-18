package de.dhbwka.java.exercise.collections.librarian;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        try {
            library.importFromBibTeX("/Users/ot2661/Documents/08_lehre/dhbw/TINF25B5-Programmieren/src/de/dhbwka/java/exercise/collections/librarian/books.bib"); // Replace with your BibTeX file path
            System.out.println("Imported " + library.getBooks().size() + " books.");

            // Sort by author
            List<Book> sortedByAuthor = library.sortBooks("author");
            System.out.println("\nSorted by author:");
            sortedByAuthor.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error reading BibTeX file: " + e.getMessage());
        }
    }
}
