package view;

import controller.CounselorController;
import model.Counselor;
import utils.DialogHelper;
import utils.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CounselorPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private final CounselorController controller = new CounselorController();

    public CounselorPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Counselor Management", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        setupTable();
        setupButtons();

        loadCounselors();
    }

    private void setupTable() {
        String[] columns = {"ID", "Name", "Specialization", "Availability"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addBtn = new JButton("Add Counselor");
        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton refreshBtn = new JButton("Refresh");

        addBtn.addActionListener(e -> addCounselor());
        editBtn.addActionListener(e -> editCounselor());
        deleteBtn.addActionListener(e -> deleteCounselor());
        refreshBtn.addActionListener(e -> loadCounselors());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadCounselors() {
        tableModel.setRowCount(0);
        ArrayList<Counselor> list = controller.getAllCounselors();

        for (Counselor c : list) {
            tableModel.addRow(new Object[]{
                    c.getId(), c.getName(), c.getSpecialization(), c.getAvailability()
            });
        }
    }

    private void addCounselor() {
    String[] input = showInputDialog(null, null, null);
    if (input == null) return;

    if (controller.counselorExists(input[0])) {
        DialogHelper.showError("A counselor with this name already exists.");
        return;
    }

    boolean success = controller.addCounselor(input[0], input[1], input[2]);
    if (success) {
        DialogHelper.showInfo("Counselor added successfully.");
        loadCounselors();
    } else {
        DialogHelper.showError("Failed to add counselor.");
    }
}


    private void editCounselor() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            DialogHelper.showError("Please select a counselor to edit.");
            return;
        }

        int id = (int) tableModel.getValueAt(selected, 0);
        String name = (String) tableModel.getValueAt(selected, 1);
        String spec = (String) tableModel.getValueAt(selected, 2);
        String avail = (String) tableModel.getValueAt(selected, 3);

        String[] input = showInputDialog(name, spec, avail);
        if (input == null) return;

        boolean success = controller.updateCounselor(id, input[0], input[1], input[2]);
        if (success) {
            DialogHelper.showInfo("Counselor updated successfully.");
            loadCounselors();
        } else {
            DialogHelper.showError("Failed to update counselor.");
        }
    }

    private void deleteCounselor() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            DialogHelper.showError("Please select a counselor to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(selected, 0);
        String name = (String) tableModel.getValueAt(selected, 1);

        if (DialogHelper.showConfirm("Delete counselor: " + name + "?")) {
            boolean success = controller.deleteCounselor(id);
            if (success) {
                DialogHelper.showInfo("Counselor deleted.");
                loadCounselors();
            } else {
                DialogHelper.showError("Failed to delete counselor.");
            }
        }
    }

    private String[] showInputDialog(String name, String specialization, String availability) {
        JTextField nameField = new JTextField(name != null ? name : "");
        JTextField specField = new JTextField(specialization != null ? specialization : "");
        JTextField availField = new JTextField(availability != null ? availability : "");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Specialization:"));
        panel.add(specField);
        panel.add(new JLabel("Availability:"));
        panel.add(availField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Counselor Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String n = nameField.getText().trim();
            String s = specField.getText().trim();
            String a = availField.getText().trim();

            if (InputValidator.isEmpty(n, s, a)) {
                DialogHelper.showError("All fields are required.");
                return null;
            }

            return new String[]{n, s, a};
        }

        return null;
    }
}
