package view;

import javax.swing.*;
import java.awt.*;
import controller.AppointmentController;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import model.Appointment;

public class AppointmentPanel extends JPanel {

    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private AppointmentController appointmentController;
//    private JButton refreshButton;
//    private JButton addButton;
//    private JButton editButton;
//    private JButton deleteButton;

    public AppointmentPanel() {
        
        appointmentController = new AppointmentController();
        
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Appointment Management", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(label, BorderLayout.NORTH);

        // To be implemented by teammate
    }
    
    public void initComp(){
        String[] columnNames = {"ID", "Student Name", "Counselor Name", "Date", "Time", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentTable.setRowHeight(25);
        appointmentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
    }
    
    public void loadAppointments() {
        tableModel.setRowCount(0);
        
        try {
            ArrayList<Appointment> appointments = appointmentController.getAll();
            
            if (appointments != null) {
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
            }   
        } catch (Exception e) {
            System.err.println("❌ Error loading appointments: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error loading appointments: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
