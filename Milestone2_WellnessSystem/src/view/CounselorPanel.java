package view;

import controller.CounselorController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CounselorPanel extends JPanel {
    private JTextField txfId, txfName, txfSpecialization, txfAvailability;
    private JButton btnAdd, btnView, btnUpdate, btnDelete;
    private JTable tblView;

    public CounselorPanel() {
        initComponents();
        initListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Counselor Management");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("ID:"), gbc);
        txfId = new JTextField(5);
        gbc.gridx = 1;
        inputPanel.add(txfId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Name:"), gbc);
        txfName = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txfName, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Specialization:"), gbc);
        txfSpecialization = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txfSpecialization, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Availability:"), gbc);
        txfAvailability = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txfAvailability, gbc);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("View");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);

        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(inputPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Table setup
        String[] columns = {"ID", "Name", "Specialization", "Availability"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tblView = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblView);
        scrollPane.setPreferredSize(new Dimension(600, 150));

        add(scrollPane, BorderLayout.SOUTH);
    }

    private void initListeners() {
        btnAdd.addActionListener(e -> {
            String name = txfName.getText().trim();
            String spec = txfSpecialization.getText().trim();
            String avail = txfAvailability.getText().trim();
            if (!name.isEmpty() && !spec.isEmpty() && !avail.isEmpty()) {
                CounselorController.insertCounselor(name, spec, avail);
                clearFields();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required.");
            }
        });

        btnView.addActionListener(e -> loadData());

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
                JOptionPane.showMessageDialog(this, "Invalid ID format.");
            }
        });

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

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblView.getModel();
        CounselorController.loadCounselors(model);
    }

    private void clearFields() {
        txfId.setText("");
        txfName.setText("");
        txfSpecialization.setText("");
        txfAvailability.setText("");
    }
}
