package de.dhbwka.java.exercise.collections.librarian;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * GUI Application for Library Management with BibTeX support.
 * Features:
 * - Load existing entries from a file.
 * - Add new unique entries.
 * - Display all entries in a scrollable table.
 * - Sort entries via pop-up windows (author, title, year, publisher).
 */
public class LibraryApp {
    private final Library library = new Library();
    private String currentFilePath = null;

    // GUI Components
    private JFrame mainFrame;
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private JTextField authorField, titleField, yearField, publisherField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryApp().startGUI());
    }

    private void startGUI() {
        mainFrame = new JFrame("Bibliotheksverwaltung");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Datei");

        JMenuItem openItem = new JMenuItem("BibTeX-Datei öffnen");
        openItem.addActionListener(e -> loadLibrary());

        JMenuItem saveItem = new JMenuItem("Speichern");
        saveItem.addActionListener(e -> saveLibrary());

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);

        // Input Panel (top)
        JPanel inputPanel = createInputPanel();
        mainFrame.add(inputPanel, BorderLayout.NORTH);

        // Table Panel (center) – Scrollable
        JPanel tablePanel = createTablePanel();
        mainFrame.add(tablePanel, BorderLayout.CENTER);

        // Sort & Action Buttons (bottom)
        JPanel bottomPanel = createBottomPanel();
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // Optional: Load default file on startup
        // loadLibrary(); // Uncomment if you want automatic load
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Autor:"));
        authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("Titel:"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Jahr:"));
        yearField = new JTextField();
        panel.add(yearField);

        panel.add(new JLabel("Verlag:"));
        publisherField = new JTextField();
        panel.add(publisherField);

        JButton addButton = new JButton("Hinzufügen");
        addButton.addActionListener(e -> addBook());

        panel.add(new JLabel()); // spacer
        panel.add(addButton);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table Model
        String[] columnNames = {"Autor", "Titel", "Jahr", "Verlag"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        booksTable = new JTable(tableModel);
        booksTable.setAutoCreateRowSorter(true); // Enable column sorting
        booksTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        booksTable.setRowHeight(22);

        JScrollPane scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        String[] sortCriteria = {"Autor", "Titel", "Jahr", "Verlag"};
        for (String criterion : sortCriteria) {
            JButton b = new JButton("Sortiert: " + criterion);
            b.addActionListener(e -> showSortedBooks(criterion.toLowerCase()));
            panel.add(b);
        }

        return panel;
    }

    private void addBook() {
        String author = authorField.getText().trim();
        String title = titleField.getText().trim();
        String year = yearField.getText().trim();
        String publisher = publisherField.getText().trim();

        if (author.isEmpty() || title.isEmpty() || year.isEmpty() || publisher.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Bitte alle Felder ausfüllen!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book newBook = new Book(author, title, year, publisher);
        if (!containsBook(newBook)) {
            library.addBook(newBook);
            updateTable();
            JOptionPane.showMessageDialog(mainFrame,
                    "Buch hinzugefügt: " + title, "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame,
                    "Buch existiert bereits!", "Hinweis", JOptionPane.WARNING_MESSAGE);
        }
        clearFields();
    }

    private boolean containsBook(Book newBook) {
        for (Book b : library.getBooks()) {
            if (b.getAuthor().equalsIgnoreCase(newBook.getAuthor()) &&
                    b.getTitle().equalsIgnoreCase(newBook.getTitle()) &&
                    b.getYear().equalsIgnoreCase(newBook.getYear()) &&
                    b.getPublisher().equalsIgnoreCase(newBook.getPublisher())) {
                return true;
            }
        }
        return false;
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Clear table
        for (Book book : library.getBooks()) {
            tableModel.addRow(new Object[]{
                    book.getAuthor(),
                    book.getTitle(),
                    book.getYear(),
                    book.getPublisher()
            });
        }
    }

    private void loadLibrary() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("BibTeX Files (*.bib)", "bib"));
        int result = fc.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFilePath = fc.getSelectedFile().getAbsolutePath();
            try {
                library.importFromBibTeX(currentFilePath);
                updateTable();
                JOptionPane.showMessageDialog(mainFrame,
                        "Geladen: " + library.getBooks().size() + " Bücher.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Fehler beim Laden: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveLibrary() {
        if (currentFilePath == null) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Keine Datei zum Speichern gewählt.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
            for (Book book : library.getBooks()) {
                writer.write(String.format("@book{%s,%n", UUID.randomUUID().toString().substring(0, 8)));
                writer.write(String.format("  author = {%s},%n", book.getAuthor()));
                writer.write(String.format("  title = {%s},%n", book.getTitle()));
                writer.write(String.format("  year = {%s},%n", book.getYear()));
                writer.write(String.format("  publisher = {%s}%n", book.getPublisher()));
                writer.write("}\n\n");
            }
            JOptionPane.showMessageDialog(mainFrame,
                    "Gespeichert erfolgreich.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Fehler beim Speichern: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSortedBooks(String criterion) {
        if (library.getBooks().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Keine Bücher zum Anzeigen.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<Book> sortedBooks = library.sortBooks(criterion);
        String title = "Sortiert nach " + criterion.substring(0, 1).toUpperCase() + criterion.substring(1);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder();
        for (Book book : sortedBooks) {
            sb.append(book.toString()).append("\n");
        }
        textArea.setText(sb.toString());

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(600, 400));

        JDialog dialog = new JDialog(mainFrame, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.add(scroll, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }

    private void clearFields() {
        authorField.setText("");
        titleField.setText("");
        yearField.setText("");
        publisherField.setText("");
    }
}
