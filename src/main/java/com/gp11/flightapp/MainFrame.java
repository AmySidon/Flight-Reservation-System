package com.gp11.flightapp;

import com.gp11.flightapp.dao.BookingService;
import com.gp11.flightapp.model.User;
import com.gp11.flightapp.utils.MongoUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private BookingService bookingService;
    private User currentUser;
    private JLabel userLabel;

    public MainFrame() {
        //Connect to MongoDB
        bookingService = new BookingService(MongoUtil.getDatabase());

        // Start logged out
        currentUser = null;

        //Set up the main frame
        setTitle("Flight Reservation System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window

        // Creating menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create user menu
        JMenu userMenu = new JMenu("User");
        menuBar.add(userMenu);

        // Add buttons to user menu
        JMenuItem logOutItem = new JMenuItem("Log Out");
        JMenuItem editUserItem = new JMenuItem("Edit Info");
        userMenu.add(logOutItem);
        userMenu.add(editUserItem);

        setJMenuBar(menuBar);

        // Initialize the userLabel
        userLabel = new JLabel("Not logged in");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Dispaly userLabel
        setLayout(new BorderLayout());
        add(userLabel, BorderLayout.NORTH);

        //Panel to hold the buttons
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // rows, columns, hgap, vgap

        // Buttons
        JButton addUserButton = new JButton("Add User");
        JButton addFlightButton = new JButton("Add Flight");
        JButton loginButton = new JButton("Log in");
        JButton viewFlightsButton = new JButton("View Flights");
        JButton viewReservationsButton = new JButton("View Reservations");
        JButton searchFlightsButton = new JButton("Search Flights");

        // Add action listeners to buttons (empty for now)
        logOutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Already logged out!");
                } else {
                    logOut();
                }

            }
        });

        editUserItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Must be logged in to edit info!");
                } else {
                    editUser();
                }
            }
        });


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

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String email= JOptionPane.showInputDialog(MainFrame.this, "Enter your email to log in:");

                    if (email != null && !email.trim().isEmpty()) {
                        User user = bookingService.searchUserEmail(email.trim());
                        if (user != null) {
                            currentUser = user;
                            userLabel.setText("Logged in as " + currentUser.getName());
                            JOptionPane.showMessageDialog(MainFrame.this, "Logged in successfully! Welcome back, " + currentUser.getName() + "!");
                            System.out.println("Logged in as " + currentUser.getName());
                        } else {
                            JOptionPane.showMessageDialog(MainFrame.this, "No user found with that email.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Invalid email input.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error: " + ex.getMessage());
                    System.out.println("Login error: " + ex.getMessage());
                }
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
        panel.add(loginButton);
        panel.add(addFlightButton);
        panel.add(viewFlightsButton);
        panel.add(viewReservationsButton);
        panel.add(searchFlightsButton);

        // Add panel to frame
        add(panel);

        setVisible(true);
    }

    private void logOut() {
        currentUser = null;
        userLabel.setText("Not logged in");
        JOptionPane.showMessageDialog(this, "You have logged out.");
    }

    private void editUser() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Must be logged in to edit info!");
            return;
        }

        JTextField nameField = new JTextField(currentUser.getName());
        JTextField emailField = new JTextField(currentUser.getEmail());

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Edit User", JOptionPane.OK_CANCEL_OPTION);

        if(option == JOptionPane.OK_OPTION) {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();

            if (newName.isEmpty() || newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and email cannot be empty.");
                return;
            }

            try {
                User newUser = new User(currentUser.getId(), newName, newEmail);
                bookingService.updateUser(newUser);
                currentUser = newUser;
                userLabel.setText("Logged in as: " + currentUser.getName());
                JOptionPane.showMessageDialog(this, "User updated successfully.");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Error " + ex.getMessage());
            }
        }
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
