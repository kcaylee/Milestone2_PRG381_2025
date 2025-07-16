package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.sql.*;
import controller.AppointmentController;
import model.Appointment;
import controller.CounselorController;
import utils.*;

public class AppointmentPanel extends JPanel {
    
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private final AppointmentController appointmentController;
    private JButton refreshButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton findButton;

    public AppointmentPanel() {
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
    
    //Function to load all the appointments into the table
    private void loadAppointments(ArrayList<Appointment> appointments) {
        // Clear existing data
        tableModel.setRowCount(0);
        
        try {
            //Checking if appointments is empty, if not, display it in the table
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
                    //Adding 1 row at a time while looping through the appointments List
                    tableModel.addRow(row);
                }
            } else {
                System.out.println("No appointments to display");
            }
        } catch (Exception e) {
            System.err.println("Error loading appointments: " + e.getMessage());
            utils.DialogHelper.showError("Error loading appointments:");
        }
    }
    
    //Function to refresh the table
    public void refresh(){
        ArrayList<Appointment> appointments = appointmentController.getAll();
        
        loadAppointments(appointments);
    }
    
    //Function to find appointments based on ID
    private void findAppointment() {
        String input = JOptionPane.showInputDialog(null,
                "Please enter the ID of the Appointment:"
                );
        
        //Checks if input is valid integer and not null
        if (input != null && InputValidator.isInteger(input)){
            
            //Parse to int
            int ID = Integer.parseInt(input);
            
            //Calls getByID function to find an entry with ID, returns list
            ArrayList<Appointment> appointments = appointmentController.getByID(ID);

            //If list is not empty or null
            if (appointments != null && !appointments.isEmpty()){
                loadAppointments(appointments); //show searched for entry in table
            } else {
                DialogHelper.showInfo("No entries found"); //else show no entries found
            }
        } else {
            //If invalid integer is entered
            DialogHelper.showError("Invalid ID Number, Please Try Again");
        }
    }
    
    //Function to create the overlay used to add and edit entries
    private String[] createOverlay(String name, String counselor, String date, String time, String status) { //If status is null, then the user is trying to ADD and NOT EDIT
        //Creates string array now in order to manipulate later and return
        final String[] result = {null, null, null, null, null};        

        //Trimming the :00 at the back of the time string
        if (status != null){
            time = time.substring(0,5);
        }
        
        //Creating the Dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Appointment", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        //Creating form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        //Using gbc to display components neatly
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        //Student Name field
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        JTextField studentNameField = new JTextField(20);
        //If a user is editing, then the entry the user chose will appear hear
        studentNameField.setText(name);
        formPanel.add(studentNameField, gbc);
        
        //Counselor Name field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Counselor Name:"), gbc);
        gbc.gridx = 1;
        CounselorController cc = new CounselorController();
        ArrayList<String> counselors = cc.getCounselorNames();
        JComboBox<String> counselorCombo = new JComboBox<>(counselors.toArray(new String[0]));
        //If a user is editing, then the entry the user chose will appear hear
        counselorCombo.setSelectedItem(counselor);
        formPanel.add(counselorCombo, gbc);
        
        //Date field
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        JTextField dateField = new JTextField(20);
        //If a user is editing, then the entry the user chose will appear hear
        dateField.setText(date);
        formPanel.add(dateField, gbc);
        
        //Time field
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Time (HH:MM):"), gbc);
        gbc.gridx = 1;
        JTextField timeField = new JTextField(20);
        //If a user is editing, then the entry the user chose will appear hear
        timeField.setText(time);
        formPanel.add(timeField, gbc);
        
        //Status field
        gbc.gridx = 0; gbc.gridy = 4;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Scheduled", "Rescheduled", "Cancelled"});
        statusCombo.setSelectedItem(status);
        
        //If the user is Adding and not edditing, we dont display the STATUS options
        if (status != null){ 
            formPanel.add(new JLabel("Status:"), gbc);
            gbc.gridx = 1;
            formPanel.add(statusCombo, gbc);
        }
        
        //Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        //Save button action
        saveButton.addActionListener(e -> {
            String studentName = studentNameField.getText().trim();
            String counselorName = (String) counselorCombo.getSelectedItem();
            String dateStr = dateField.getText().trim();
            String timeStr = timeField.getText().trim();
            String statusStr;
            
            //If status == null then we auto assign it to Scheduled, as it was just added
            if (status != null){
                statusStr = (String) statusCombo.getSelectedItem();
            } else {
                statusStr = "Scheduled";
            }
            
            //Validate inputs
            if (studentName.isEmpty() || counselorName.isEmpty() || dateStr.isEmpty() || timeStr.isEmpty()) {
                DialogHelper.showError("Please fill in all fields");
                return;
            }
            
            //Validate date format
            if (!InputValidator.isValidDate(dateStr)) {
                DialogHelper.showError("Please enter date in YYYY-MM-DD format");
                return;
            }
            
            //Validate time format
            if (!InputValidator.isValidTime(timeStr)) {
                DialogHelper.showError("Please enter time in HH:MM format");
                return;
            }

            //Assign new values to the String Array
            result[0] = studentName;
            result[1] = counselorName;
            result[2] = dateStr;
            result[3] = timeStr;
            result[4] = statusStr;
            
            //Close overlay
            dialog.dispose();   
        });
        
        //Cancel button action
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        //Add panels to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        //Show dialog
        dialog.setVisible(true); 
        return result;
    }
    
    //Function to add an appointment
    private void addAppointment(){
        //Creates the overlay and returns the array of results
        String[] result = createOverlay("", "", "", "", null);
        
        //Checks if user canceled the operation
        if (result[0] == null || result[0].isEmpty()) {
            return;
        }
        
        //Parsing date and time as it has already been verified
        Date date = Date.valueOf(result[2]);
        Time time = Time.valueOf(result[3] + ":00");

        //Adding the entry and checking if it fails
        boolean success = appointmentController.addAppointment(result[0], result[1], date, time, result[4]);

        if (success){
            DialogHelper.showInfo("Appointment added successfully!");
            refresh();
        } else {
            DialogHelper.showError("Appointment failed to be added!");
        }
    }
    
    //Function to edit a currently selected entry
    private void editAppointment() {
        int selectedRow = appointmentTable.getSelectedRow(); //Gets selected index
        //If -1, then nothing is selected
        if (selectedRow == -1) {
            DialogHelper.showError("Please select an appointment to edit");
        } else {
            //Getting the selected item based on the index in the database
            Appointment selectedItem = appointmentController.getAll().get(selectedRow);
            
            //Creating overlay and storing results
            String[] result = createOverlay(selectedItem.getStudentName(), 
                    selectedItem.getCounselorName(), 
                    selectedItem.getAppointmentDate().toString(), 
                    selectedItem.getAppointmentTime().toString(), 
                    selectedItem.getStatus());
            
            //Checks if user canceled the operation
            if (result[0] == null || result[0].isEmpty()) {
                return;
            }
            
            Date date = Date.valueOf(result[2]);
            Time time = Time.valueOf(result[3] + ":00");

            boolean success = appointmentController.updateAppointment(result[0], result[1], date, time, result[4], selectedItem.getId());

            if (success){
                DialogHelper.showInfo("Appointment edited successfully!");
                refresh();
            } else {
                DialogHelper.showError("Appointment failed to be edited!");
            }
        }
    }
    
    //Function to delete a currently selected entry
    private void deleteAppointment() {
        int selectedRow = appointmentTable.getSelectedRow(); //Gets index
        //Checks if something is selected
        if (selectedRow == -1) {
            DialogHelper.showError("Please select an appointment to delete");
            return;
        }
        
        //Gets the ID of the selected entry
        int ID = (Integer) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);
        
        boolean confirm = DialogHelper.showConfirm("Are you sure you want to delete the appointment for " + studentName + "?");
        
        if (confirm) {
            appointmentController.deleteByID(ID);
            refresh();
            DialogHelper.showInfo("Successfully Deleted " + studentName +"'s Appointment");
        }
    }
}