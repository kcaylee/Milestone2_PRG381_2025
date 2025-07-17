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
        String[] columnNames = {"ID", "Student Name", "Counselor Name", "Date", "Time", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentTable.setRowHeight(25);
        appointmentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        refreshButton = new JButton("Refresh");
        addButton = new JButton("Add Appointment");
        editButton = new JButton("Edit Selected");
        deleteButton = new JButton("Delete Selected");
        findButton = new JButton("Find By ID");

        refreshButton.addActionListener(e -> refresh());
        addButton.addActionListener(e -> addAppointment());
        editButton.addActionListener(e -> editAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());
        findButton.addActionListener(e -> findAppointment());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Appointment Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshButton);
        buttonPanel.add(findButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadAppointments(ArrayList<Appointment> appointments) {
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
            } else {
                System.out.println("No appointments to display");
            }
        } catch (Exception e) {
            System.err.println("Error loading appointments: " + e.getMessage());
            DialogHelper.showError("Error loading appointments.");
        }
    }

    public void refresh() {
        ArrayList<Appointment> appointments = appointmentController.getAll();
        loadAppointments(appointments);
    }

    private void findAppointment() {
        String input = JOptionPane.showInputDialog(null, "Please enter the ID of the Appointment:");
        if (input != null && InputValidator.isInteger(input)) {
            int ID = Integer.parseInt(input);
            ArrayList<Appointment> appointments = appointmentController.getByID(ID);
            if (appointments != null && !appointments.isEmpty()) {
                loadAppointments(appointments);
            } else {
                DialogHelper.showInfo("No entries found.");
            }
        } else {
            DialogHelper.showError("Invalid ID Number, Please Try Again");
        }
    }

    private String[] createOverlay(String name, String counselor, String date, String time, String status) {
        final String[] result = {null, null, null, null, null};
        if (status != null) time = time.substring(0, 5);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Appointment", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        JTextField studentNameField = new JTextField(20);
        studentNameField.setText(name);
        formPanel.add(studentNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Counselor Name:"), gbc);
        gbc.gridx = 1;
        CounselorController cc = new CounselorController();
        ArrayList<String> counselors = cc.getCounselorNames();
        JComboBox<String> counselorCombo = new JComboBox<>(counselors.toArray(new String[0]));
        counselorCombo.setSelectedItem(counselor);
        formPanel.add(counselorCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        JTextField dateField = new JTextField(20);
        dateField.setText(date);
        formPanel.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Time (HH:MM):"), gbc);
        gbc.gridx = 1;
        JTextField timeField = new JTextField(20);
        timeField.setText(time);
        formPanel.add(timeField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Scheduled", "Rescheduled", "Cancelled"});
        statusCombo.setSelectedItem(status);
        if (status != null) {
            formPanel.add(new JLabel("Status:"), gbc);
            gbc.gridx = 1;
            formPanel.add(statusCombo, gbc);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String studentName = studentNameField.getText().trim();
            String counselorName = (String) counselorCombo.getSelectedItem();
            String dateStr = dateField.getText().trim();
            String timeStr = timeField.getText().trim();
            String statusStr = (status != null) ? (String) statusCombo.getSelectedItem() : "Scheduled";

            if (studentName.isEmpty() || counselorName.isEmpty() || dateStr.isEmpty() || timeStr.isEmpty()) {
                DialogHelper.showError("Please fill in all fields.");
                return;
            }

            if (!InputValidator.isValidDate(dateStr)) {
                DialogHelper.showError("Enter date in YYYY-MM-DD format.");
                return;
            }

            if (!InputValidator.isValidTime(timeStr)) {
                DialogHelper.showError("Enter time in HH:MM format.");
                return;
            }

            result[0] = studentName;
            result[1] = counselorName;
            result[2] = dateStr;
            result[3] = timeStr;
            result[4] = statusStr;
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return result;
    }

    private void addAppointment() {
        String[] result = createOverlay("", "", "", "", null);
        if (result[0] == null || result[0].isEmpty()) return;
        Date date = Date.valueOf(result[2]);
        Time time = Time.valueOf(result[3] + ":00");

        boolean success = appointmentController.addAppointment(result[0], result[1], date, time, result[4]);
        if (success) {
            DialogHelper.showInfo("Appointment added successfully!");
            refresh();
        } else {
            DialogHelper.showError("Appointment failed to be added!");
        }
    }

    private void editAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            DialogHelper.showError("Please select an appointment to edit");
        } else {
            Appointment selectedItem = appointmentController.getAll().get(selectedRow);
            String[] result = createOverlay(
                selectedItem.getStudentName(),
                selectedItem.getCounselorName(),
                selectedItem.getAppointmentDate().toString(),
                selectedItem.getAppointmentTime().toString(),
                selectedItem.getStatus()
            );

            if (result[0] == null || result[0].isEmpty()) return;
            Date date = Date.valueOf(result[2]);
            Time time = Time.valueOf(result[3] + ":00");

            boolean success = appointmentController.updateAppointment(result[0], result[1], date, time, result[4], selectedItem.getId());

            if (success) {
                DialogHelper.showInfo("Appointment edited successfully!");
                refresh();
            } else {
                DialogHelper.showError("Appointment failed to be edited!");
            }
        }
    }

    private void deleteAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            DialogHelper.showError("Please select an appointment to delete");
            return;
        }

        int ID = (Integer) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);

        boolean confirm = DialogHelper.showConfirm("Are you sure you want to delete the appointment for " + studentName + "?");
        if (confirm) {
            appointmentController.deleteByID(ID);
            refresh();
            DialogHelper.showInfo("Successfully Deleted " + studentName + "'s Appointment");
        }
    }
}
