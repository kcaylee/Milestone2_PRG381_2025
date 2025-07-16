package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import controller.AppointmentController;
import model.Appointment;
import controller.CounselorController;
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
        ArrayList<Appointment> appointments = appointmentController.getAll("Appointments");
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
        ArrayList<Appointment> appointments = appointmentController.getAll("Appointments");
        
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
                JOptionPane.showMessageDialog(null, 
                        "No entries found", 
                        "No Entries", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                    "Please enter a valid ID Number", 
                    "Invalid Option", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addAppointment() {
        // Create dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Appointment", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Student Name field
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        JTextField studentNameField = new JTextField(20);
        formPanel.add(studentNameField, gbc);
        
        // Counselor Name field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Counselor Name:"), gbc);
        gbc.gridx = 1;
        CounselorController cc = new CounselorController();
        ArrayList<String> counselors = cc.getCounselorNames();
        JComboBox<String> counselorCombo = new JComboBox<>(counselors.toArray(new String[0]));
        formPanel.add(counselorCombo, gbc);
        
        // Date field
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        JTextField dateField = new JTextField(20);
        formPanel.add(dateField, gbc);
        
        // Time field
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Time (HH:MM):"), gbc);
        gbc.gridx = 1;
        JTextField timeField = new JTextField(20);
        formPanel.add(timeField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        // Save button action
        saveButton.addActionListener(e -> {
            String studentName = studentNameField.getText().trim();
            String counselorName = (String) counselorCombo.getSelectedItem();
            String dateStr = dateField.getText().trim();
            String timeStr = timeField.getText().trim();
            
            // Validate inputs
            if (studentName.isEmpty() || counselorName.isEmpty() || dateStr.isEmpty() || timeStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please fill in all fields", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate date format
            if (!InputValidator.isValidDate(dateStr)) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter date in YYYY-MM-DD format", 
                    "Invalid Date Format", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate time format
            if (!InputValidator.isValidTime(timeStr)) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter time in HH:MM format", 
                    "Invalid Time Format", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                // Parse date and time
                java.sql.Date appointmentDate = java.sql.Date.valueOf(dateStr);
                java.sql.Time appointmentTime = java.sql.Time.valueOf(timeStr + ":00");
                
                // Create appointment using controller
                boolean success = appointmentController.addAppointment(
                    studentName, counselorName, appointmentDate, appointmentTime, "Scheduled"
                );
                
                if (success) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Appointment added successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    refresh(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(dialog, 
                        "Failed to add appointment", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Cancel button action
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Add panels to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Show dialog
        dialog.setVisible(true);
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
        
        int ID = (Integer) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete the appointment for " + studentName + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            
            appointmentController.deleteByID(ID);
            
            refresh();
            
            JOptionPane.showMessageDialog(this, 
                "Successfully Deleted " + studentName +"'s Appointment", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            // TODO: Implement actual deletion
            // appointmentController.deleteByID(appointmentId);
            // loadAppointments(); // Refresh table
        }
    }
}