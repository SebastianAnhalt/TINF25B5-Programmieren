package de.dhbwka.java.exercise.collections.librarian;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

/**
 * GUI-Applikation für eine Bibliotheksverwaltung mit BibTeX-Unterstützung.
 */
public class LibraryApp {
    private final Library library = new Library();
    private String currentFilePath = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryApp().startGUI());
    }

    private void startGUI() {
        JFrame frame = new JFrame("Bibliotheksverwaltung");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Menüleiste
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Datei");

        JMenuItem openItem = new JMenuItem("BibTeX-Datei öffnen");
        openItem.addActionListener(e -> loadLibrary());

        JMenuItem saveItem = new JMenuItem("Speichern");
        saveItem.addActionListener(e -> saveLibrary());

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        // Eingabebereich
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel authorLabel = new JLabel("Autor:");
        JTextField authorField = new JTextField();

        JLabel titleLabel = new JLabel("Titel:");
        JTextField titleField = new JTextField();

        JLabel yearLabel = new JLabel("Jahr:");
        JTextField yearField = new JTextField();

        JLabel publisherLabel = new JLabel("Verlag:");
        JTextField publisherField = new JTextField();

        JButton addButton = new JButton("Hinzufügen");
        addButton.addActionListener(e -> {
            String author = authorField.getText().trim();
            String title = titleField.getText().trim();
            String year = yearField.getText().trim();
            String publisher = publisherField.getText().trim();

            if (author.isEmpty() || title.isEmpty() || year.isEmpty() || publisher.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Bitte alle Felder ausfüllen!", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book newBook = new Book(author, title, year, publisher);
            if (!containsBook(newBook)) {
                library.addBook(newBook);
                JOptionPane.showMessageDialog(frame, "Buch hinzugefügt: " + title, "Erfolg", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Buch existiert bereits!", "Hinweis", JOptionPane.WARNING_MESSAGE);
            }
            clearFields(authorField, titleField, yearField, publisherField);
        });

        inputPanel.add(authorLabel);
        inputPanel.add(authorField);
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
        inputPanel.add(publisherLabel);
        inputPanel.add(publisherField);
        inputPanel.add(new JLabel()); // Leerzeile
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.CENTER);

        // Sortier-Buttons
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        String[] sortCriteria = {"Autor", "Titel", "Jahr", "Verlag"};

        for (String criterion : sortCriteria) {
            JButton sortButton = new JButton("Sortiert nach " + criterion);
            sortButton.addActionListener(e -> showSortedBooks(criterion.toLowerCase()));
            sortPanel.add(sortButton);
        }

        frame.add(sortPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Optional: Automatisches Laden beim Start
        loadLibrary();
    }

    private void loadLibrary() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("BibTeX-Dateien (*.bib)", "bib"));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            currentFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                library.importFromBibTeX(currentFilePath);
                JOptionPane.showMessageDialog(null, "BibTeX-Datei erfolgreich geladen.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Laden der BibTeX-Datei: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveLibrary() {
        if (currentFilePath == null) {
            JOptionPane.showMessageDialog(null, "Keine Datei zum Speichern ausgewählt.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath, false))) {
            for (Book book : library.getBooks()) {
                writer.write(String.format("@book{%s,%n", UUID.randomUUID().toString().substring(0, 8)));
                writer.write(String.format("  author = {%s},%n", book.getAuthor()));
                writer.write(String.format("  title = {%s},%n", book.getTitle()));
                writer.write(String.format("  year = {%s},%n", book.getYear()));
                writer.write(String.format("  publisher = {%s}%n", book.getPublisher()));
                writer.write("}\n\n");
            }
            JOptionPane.showMessageDialog(null, "Bibliothek erfolgreich gespeichert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSortedBooks(String criterion) {
        if (library.getBooks().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Keine Bücher zum Anzeigen vorhanden.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        java.util.List<Book> sortedBooks = library.sortBooks(criterion);
        String title = "Sortiert nach " + criterion.substring(0, 1).toUpperCase() + criterion.substring(1);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder();
        for (Book book : sortedBooks) {
            sb.append(book.toString()).append("\n");
        }
        textArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private boolean containsBook(Book newBook) {
        for (Book book : library.getBooks()) {
            if (book.getAuthor().equalsIgnoreCase(newBook.getAuthor()) &&
                    book.getTitle().equalsIgnoreCase(newBook.getTitle()) &&
                    book.getYear().equalsIgnoreCase(newBook.getYear()) &&
                    book.getPublisher().equalsIgnoreCase(newBook.getPublisher())) {
                return true;
            }
        }
        return false;
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}
