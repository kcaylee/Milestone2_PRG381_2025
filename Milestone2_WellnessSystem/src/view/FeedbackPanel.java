package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

// Feedback panel to define GUI for feedback management
public class FeedbackPanel extends JPanel {

    //Input fields
    public JTextField txtStudentName;
    public JComboBox<Integer> cmbRating;
    public JTextArea txtComments;
    public JButton btnSubmit, btnUpdate, btnDelete, btnClear;
    
    //Table for feedback display
    public JTable feedbackTable;
    
    //Model for row/col data management
    public DefaultTableModel tableModel;
    
    //Constructor to initialize GUI layout
    public FeedbackPanel() {
        //Divides panel into sections
        setLayout(new BorderLayout());

        //Title formatting
        JLabel label = new JLabel("Feedback Management", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        //Adds title on top (north) of main panel
        add(label, BorderLayout.NORTH);

        //Table formatting
        //Define col headers
        String[] columnNames = {"ID", "Student Name", "Rating", "Comments"};
        //Create table model based on headers
        tableModel = new DefaultTableModel(columnNames, 0);
        //Create the table and combine with model
        feedbackTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        //Adds table and centers in main panel
        add(scrollPane, BorderLayout.CENTER);
        
        //Form Panel formatting
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //Layout behavior for each component in panel (input fields and buttons)
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Lable and input for name
        JLabel lblStudentName = new JLabel("Student Name:");
        txtStudentName = new JTextField(15);
        //Lable and input for rating
        JLabel lblRating = new JLabel("Rating:");
        cmbRating = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        //Lable and input for comments
        JLabel lblComments = new JLabel("Comments:");
        txtComments = new JTextArea(3, 15);
        JScrollPane commentScroll = new JScrollPane(txtComments);

        //Adding buttons
        btnSubmit = new JButton("Submit");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        //Adding each componenet to the form panel with GridBagLayout
        //Labels, textbox, and combobox 
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblStudentName, gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(txtStudentName, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblRating, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(cmbRating, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblComments, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(commentScroll, gbc);

        //Buttons
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(btnSubmit, gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(btnUpdate, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(btnDelete, gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(btnClear, gbc);

        //Add form panel bottom (south) of main panel
        add(formPanel, BorderLayout.SOUTH);
    }
}
