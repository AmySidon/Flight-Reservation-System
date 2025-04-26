package com.gp11.flightapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Set up the main frame
        setTitle("Flight Reservation System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // rows, columns, hgap, vgap

        // Create buttons
        JButton addUserButton = new JButton("Add User");
        JButton addFlightButton = new JButton("Add Flight");
        JButton bookReservationButton = new JButton("Book Reservation");
        JButton viewFlightsButton = new JButton("View Flights");
        JButton viewReservationsButton = new JButton("View Reservations");
        JButton searchFlightsButton = new JButton("Search Flights");

        // Add action listeners to buttons (empty for now)
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Add User button clicked!");
                // Here you will later open a new window or form for adding a user
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
                JOptionPane.showMessageDialog(MainFrame.this, "View Flights button clicked!");
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
