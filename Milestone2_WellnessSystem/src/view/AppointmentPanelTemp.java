package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import controller.AppointmentController;
import model.Appointment;
import utils.*;

public class AppointmentPanelTemp extends JPanel {
    
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private AppointmentController appointmentController;
    private JButton refreshButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton findButton;

    public AppointmentPanelTemp() {
        appointmentController = new AppointmentController();
        initializeComponents();
        setupLayout();
        ArrayList<Appointment> appointments = appointmentController.getAll();
        loadAppointments(appointments);
    }
    
    private void initializeComponents() {
        // Create table model with column names
        String[] columnNames = {"ID", "Student Name", "Counselor Name", "Date", "Time", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        // Create table
        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentTable.setRowHeight(25);
        appointmentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        
        // Create buttons
        refreshButton = new JButton("Refresh");
        addButton = new JButton("Add Appointment");
        editButton = new JButton("Edit Selected");
        deleteButton = new JButton("Delete Selected");
        findButton = new JButton("Find By ID");
        
        // Add action listeners
        refreshButton.addActionListener(e -> refresh());
        addButton.addActionListener(e -> addAppointment());
        editButton.addActionListener(e -> editAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());
        findButton.addActionListener(e -> findAppointment());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Appointment Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshButton);
        buttonPanel.add(findButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        // Table panel with scroll
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadAppointments(ArrayList<Appointment> appointments) {
        // Clear existing data
        tableModel.setRowCount(0);
        
        try {
            
            if (appointments != null && !appointments.isEmpty()) {
                for (Appointment appointment : appointments) {
                    Object[] row = {
                        appointment.getId(),
                        appointment.getStudentName(),
                        appointment.getCounselorName(),
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime(),
                        appointment.getStatus()
                    };
                    tableModel.addRow(row);
                }
                System.out.println("✅ Loaded " + appointments.size() + " appointments into table");
            } else {
                System.out.println("📭 No appointments to display");
            }
        } catch (Exception e) {
            System.err.println("❌ Error loading appointments: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error loading appointments: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refresh(){
        ArrayList<Appointment> appointments = appointmentController.getAll();
        
        loadAppointments(appointments);
    }
    
    private void findAppointment() {
        String input = JOptionPane.showInputDialog(null,
                "Please enter the ID of the Appointment:"
                );
        
        if (input != null && InputValidator.isInteger(input)){
            
            int ID = Integer.parseInt(input);
            
            ArrayList<Appointment> appointments = appointmentController.getByID(ID);

            if (appointments != null && !appointments.isEmpty()){
                loadAppointments(appointments);
            } else {
                JOptionPane.showMessageDialog(null, "No entries found");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID Number");
        }
    }
    
    private void addAppointment() {
        JOptionPane.showMessageDialog(this, 
            "Add appointment functionality to be implemented", 
            "Feature Not Implemented", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void editAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an appointment to edit", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Edit appointment functionality to be implemented", 
            "Feature Not Implemented", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void deleteAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an appointment to delete", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int appointmentId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete the appointment for " + studentName + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "Delete appointment functionality to be implemented", 
                "Feature Not Implemented", 
                JOptionPane.INFORMATION_MESSAGE);
            // TODO: Implement actual deletion
            // appointmentController.deleteByID(appointmentId);
            // loadAppointments(); // Refresh table
        }
    }
}