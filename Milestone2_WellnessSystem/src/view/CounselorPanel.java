package view;

import controller.CounselorController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CounselorPanel extends JPanel {

    //Declare text fields for input and buttons for CRUD operations.
    private JTextField txfId, txfName, txfSpecialization, txfAvailability;
    private JButton btnAdd, btnView, btnUpdate, btnDelete;
    private JTable tblView; //Declare a table to display the data

    //Constructor
    public CounselorPanel() {
        initComponents();//Sets up the GUI
        initListeners(); //Add button actions
    }

    //Method that initializes all GUI components
    private void initComponents() {
        //Sets  up a border layout for the main panel. 10px gaps. 
        setLayout(new BorderLayout(10, 10));

        //Creates and styles title label.
        JLabel lblTitle = new JLabel("Counselor Management");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        add(lblTitle, BorderLayout.NORTH);//Places title at the top

        //Declare a panel for for inputs
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); //This is for positioning components in the grid. 
        gbc.insets = new Insets(4, 4, 4, 4); //Adds padding between components
        gbc.anchor = GridBagConstraints.WEST; //Aligns components to the left

        //Add ID field
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("ID:"), gbc);
        txfId = new JTextField(5);
        gbc.gridx = 1;
        inputPanel.add(txfId, gbc);

        //Add Name field
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Name:"), gbc);
        txfName = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txfName, gbc);

        //Add Spicialization field
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Specialization:"), gbc);
        txfSpecialization = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txfSpecialization, gbc);

        //Add Availability field
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Availability:"), gbc);
        txfAvailability = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txfAvailability, gbc);

        //Creates buttons and add them to a flowlayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("View");

        //Adds buttons to the button panel
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);

        //Create a vertical panel to group form and buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Add form and buttons with spacing
        centerPanel.add(inputPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));//Adds the spacning
        centerPanel.add(buttonPanel);

        //Place in center of main layout
        add(centerPanel, BorderLayout.CENTER);

        // Table setup
        String[] columns = {"ID", "Name", "Specialization", "Availability"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);//empty table with column headers
        tblView = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblView);//Adds ability to scroll if the table is large
        scrollPane.setPreferredSize(new Dimension(600, 150));

        add(scrollPane, BorderLayout.SOUTH);//Places the table at the bottom
    }

    //Method to initialize button listeners
    private void initListeners() {
        //Adds the button logic
        btnAdd.addActionListener(e -> {
            String name = txfName.getText().trim();
            String spec = txfSpecialization.getText().trim();
            String avail = txfAvailability.getText().trim();
            if (!name.isEmpty() && !spec.isEmpty() && !avail.isEmpty()) {
                CounselorController.insertCounselor(name, spec, avail); //Call the controller to insert
                clearFields();//Reset form
                loadData();//Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required."); //Exception handler if a textfield is empty
            }
        });

        //View button logic
        btnView.addActionListener(e -> loadData());

        //Update button logic
        btnUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txfId.getText());
                String name = txfName.getText().trim();
                String spec = txfSpecialization.getText().trim();
                String avail = txfAvailability.getText().trim();
                if (!name.isEmpty() && !spec.isEmpty() && !avail.isEmpty()) {
                    CounselorController.updateCounselor(id, name, spec, avail);
                    clearFields();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "All fields are required.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format."); //Exception handler
            }
        });

        //Delete button logic
        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txfId.getText());
                CounselorController.deleteCounselor(id);
                clearFields();
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.");
            }
        });
    }

    //Loads the counselors into the table
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblView.getModel();
        CounselorController.loadCounselors(model);
    }

    //Clears akk form fields
    private void clearFields() {
        txfId.setText("");
        txfName.setText("");
        txfSpecialization.setText("");
        txfAvailability.setText("");
    }
}
