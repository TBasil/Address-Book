package addressbook;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class Addressbook {
    private HashMap<String, Person> addressBook;

    public Addressbook() {
        addressBook = new HashMap<>();
    }

    public void addPerson(String name, String address, int phone) {
        addressBook.put(name, new Person(name, address, phone));
    }

    public void deletePerson(String name) {
        addressBook.remove(name);
    }

    public Person searchPerson(String name) {
        return addressBook.get(name);
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(addressBook);
            System.out.println("Address book saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving address book: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            addressBook = (HashMap<String, Person>) ois.readObject();
            System.out.println("Address book loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading address book: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Addressbook book = new Addressbook();
        book.loadFromFile("addressbook.dat"); // Load data from file if exists
        JFrame frame = new JFrame("Address Book");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, book);

        frame.setVisible(true);

        // Save address book data to file when closing the application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            book.saveToFile("addressbook.txt");
        }));
    }

    private static void placeComponents(JPanel panel, Addressbook book) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(100, 20, 165, 25);
        panel.add(nameText);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 50, 80, 25);
        panel.add(addressLabel);

        JTextField addressText = new JTextField(20);
        addressText.setBounds(100, 50, 165, 25);
        panel.add(addressText);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(10, 80, 80, 25);
        panel.add(phoneLabel);

        JTextField phoneText = new JTextField(20);
        phoneText.setBounds(100, 80, 165, 25);
        panel.add(phoneText);

        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 110, 80, 25);
        addButton.addActionListener(e -> {
            String name = nameText.getText();
            String address = addressText.getText();
            int phone = Integer.parseInt(phoneText.getText());
            book.addPerson(name, address, phone);
        });
        panel.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(100, 110, 80, 25);
        deleteButton.addActionListener(e -> {
            String name = nameText.getText();
            book.deletePerson(name);
        });
        panel.add(deleteButton);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(190, 110, 80, 25);
        searchButton.addActionListener(e -> {
            String name = nameText.getText();
            Person person = book.searchPerson(name);
            if (person != null) {
                JOptionPane.showMessageDialog(null, "Address: " + person.getAddress() + "\nPhone: " + person.getPhone());
            } else {
                JOptionPane.showMessageDialog(null, "Person not found!");
            }
        });
        panel.add(searchButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(280, 110, 80, 25);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);
    }
}
