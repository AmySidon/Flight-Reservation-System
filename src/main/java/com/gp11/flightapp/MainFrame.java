package com.gp11.flightapp;

import com.gp11.flightapp.dao.BookingService;
import com.gp11.flightapp.model.*;
import com.gp11.flightapp.utils.MongoUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.time.LocalDate;
import java.time.DateTimeException;

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
                logOut();
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
                    List<Flight> flights = bookingService.getAllFlights();
                    displayFlights(flights);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(MainFrame.this, "Error fetching flights: " + ex.getMessage());
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
                try {
                    String origin = JOptionPane.showInputDialog(MainFrame.this, "Enter departure airport:");
                    String destination = JOptionPane.showInputDialog(MainFrame.this, "Enter arrival airport:");
                    LocalDate startDate = getDateFromUser("Enter beginning date");
                    LocalDate endDate = getDateFromUser("Enter end date");
                    if (origin != null && destination != null && startDate != null && endDate != null) {
                        List<Flight> flights = bookingService.searchFlights(startDate, endDate, origin.trim(), destination.trim());
                        displayFlights(flights);
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this, "Search cancelled.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error searching flights: " + ex.getMessage()); 
                }
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
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Arleady logged out!");
            return;
        }
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

    private void displayFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights available.");
            return;
        }

        JFrame flightFrame = new JFrame("Available Flights");
        flightFrame.setSize(300,400);
        flightFrame.setLocationRelativeTo(this);

        DefaultListModel<Flight> listModel = new DefaultListModel<>();
        for (Flight flight : flights) {
            listModel.addElement(flight);
        }

        JList<Flight> flightList = new JList<>(listModel);
        flightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(flightList);

        JButton bookButton = new JButton("Book Flight");

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flight selectedFlight = flightList.getSelectedValue();
                if (selectedFlight == null) {
                    JOptionPane.showMessageDialog(flightFrame, "Please select a flight.");
                    return;
                }
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(flightFrame, "You must be logged in to book a flight.");
                    return;
                }

                try {
                    bookingService.bookReservation(currentUser, selectedFlight);
                    JOptionPane.showMessageDialog(flightFrame, "Flight booked!");
                    flightFrame.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(flightFrame, "Error booking flight: " + ex.getMessage());
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(bookButton);

        flightFrame.setLayout(new BorderLayout());
        flightFrame.add(scrollPane, BorderLayout.CENTER);
        flightFrame.add(bottomPanel, BorderLayout.SOUTH);

        flightFrame.setVisible(true);
    }

    private LocalDate getDateFromUser(String prompt) {
        JComboBox<Integer> yearBox = new JComboBox<>();
        JComboBox<Integer> monthBox = new JComboBox<>();
        JComboBox<Integer> dayBox = new JComboBox<>();

        for (int y = 2025; y<= 2030; y++) yearBox.addItem(y);
        for (int m = 1; m <= 12; m++) monthBox.addItem(m);
        for (int d = 1; d <= 31; d++) dayBox.addItem(d);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Year:"));
        panel.add(yearBox);
        panel.add(new JLabel("Month:"));
        panel.add(monthBox);
        panel.add(new JLabel("Day:"));
        panel.add(dayBox);

        int result = JOptionPane.showConfirmDialog(MainFrame.this, panel, prompt, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int year = (Integer) yearBox.getSelectedItem();
            int month = (Integer) monthBox.getSelectedItem();
            int day = (Integer) dayBox.getSelectedItem();
            try {
                return LocalDate.of(year, month, day);
            } catch (DateTimeException e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Invalid date selected. Please try again.","Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
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
