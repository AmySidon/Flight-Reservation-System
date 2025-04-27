package com.gp11.flightapp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.gp11.flightapp.dao.BookingService;
import com.gp11.flightapp.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private BookingService bookingService;
    public MainFrame() {
        //Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb+srv://user_01:group16@g16-project.rl4rjyj.mongodb.net/?retryWrites=true&w=majority&appName=g16-project");
        MongoDatabase database = mongoClient.getDatabase("flight_reservation_db"); // <-- We'll double check the database name soon
        bookingService = new BookingService(database);

        //Set up the main frame
        setTitle("Flight Reservation System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window

        //Panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // rows, columns, hgap, vgap

        // Buttons
        JButton addUserButton = new JButton("Add User");
        JButton addFlightButton = new JButton("Add Flight");
        JButton bookReservationButton = new JButton("Book Reservation");
        JButton viewFlightsButton = new JButton("View Flights");
        JButton viewReservationsButton = new JButton("View Reservations");
        JButton searchFlightsButton = new JButton("Search Flights");

        // Add action listeners to buttons (empty for now)
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Ask for user details
                    String name = JOptionPane.showInputDialog(MainFrame.this, "Enter User Name:");
                    String email = JOptionPane.showInputDialog(MainFrame.this, "Enter User Email:");
                    // Validate inputs
                    if (name != null && !name.trim().isEmpty() && email != null && !email.trim().isEmpty()) {
                        // Create new User object (MongoDB will auto-generate ID)
                        User newUser = new User(null, name, email);
                        // Call BookingService to add user
                        bookingService.addUser(newUser);
                        // Show success message
                        JOptionPane.showMessageDialog(MainFrame.this, "✅ User added successfully!");
                        System.out.println("✅ Added user: " + name + ", " + email);
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Invalid input. User not added.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error: " + ex.getMessage());
                    System.out.println("Error adding user: " + ex.getMessage());
                }
            }
        });        

        addFlightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Add Flight button clicked!");
            }
        });

        bookReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Book Reservation button clicked!");
            }
        });
        viewFlightsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get all flights from the database
                    java.util.List<com.gp11.flightapp.model.Flight> flights = bookingService.getAllFlights();
        
                    if (flights.isEmpty()) {
                        JOptionPane.showMessageDialog(MainFrame.this, "No flights found.");
                    } else {
                        // Build a string to display all flight information
                        StringBuilder flightInfo = new StringBuilder();
                        for (com.gp11.flightapp.model.Flight flight : flights) {
                            flightInfo.append("Flight ID: ").append(flight.getId()).append("\n");
                            flightInfo.append("Departure Airport: ").append(flight.getDepartureAirport()).append("\n");
                            flightInfo.append("Arrival Airport: ").append(flight.getArrivalAirport()).append("\n");
                            flightInfo.append("Date: ").append(flight.getDate()).append("\n\n");
                        }
                        // Show flights in a message dialog
                        JTextArea textArea = new JTextArea(flightInfo.toString());
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(500, 400));
        
                        JOptionPane.showMessageDialog(MainFrame.this, scrollPane, "All Flights", JOptionPane.INFORMATION_MESSAGE);
                    }} catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error fetching flights: " + ex.getMessage());
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        });
        
        viewReservationsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "View Reservations button clicked!");
            }
        });
        searchFlightsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Search Flights button clicked!");
            }
        });

        // Add buttons to the panel
        panel.add(addUserButton);
        panel.add(addFlightButton);
        panel.add(bookReservationButton);
        panel.add(viewFlightsButton);
        panel.add(viewReservationsButton);
        panel.add(searchFlightsButton);

        // Add panel to frame
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}
