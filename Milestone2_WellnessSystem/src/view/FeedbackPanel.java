package view;

import controller.FeedbackController;
import model.Feedback;
import utils.DialogHelper;
import utils.InputValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FeedbackPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private final FeedbackController controller = new FeedbackController();

    public FeedbackPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Student Feedback", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        setupTable();
        setupButtons();

        loadFeedbacks();
    }

    private void setupTable() {
        String[] columns = {"ID", "Student Name", "Rating", "Comments"};
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

        JButton addBtn = new JButton("Add Feedback");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton refreshBtn = new JButton("Refresh");

        addBtn.addActionListener(e -> addFeedback());
        deleteBtn.addActionListener(e -> deleteFeedback());
        refreshBtn.addActionListener(e -> loadFeedbacks());

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadFeedbacks() {
        tableModel.setRowCount(0);
        ArrayList<Feedback> feedbackList = controller.getAllFeedback();

        for (Feedback f : feedbackList) {
            tableModel.addRow(new Object[]{
                f.getId(),
                f.getStudentName(),
                f.getRating(),
                f.getComments()
            });
        }
    }

    private void addFeedback() {
        JTextField studentField = new JTextField();
        JTextField ratingField = new JTextField();
        JTextField commentField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Student Name:"));
        panel.add(studentField);
        panel.add(new JLabel("Rating (1–5):"));
        panel.add(ratingField);
        panel.add(new JLabel("Comments:"));
        panel.add(commentField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Feedback",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String student = studentField.getText().trim();
            String ratingStr = ratingField.getText().trim();
            String comments = commentField.getText().trim();

            if (InputValidator.isEmpty(student, ratingStr)) {
                DialogHelper.showError("Student name and rating are required.");
                return;
            }

            if (!InputValidator.isValidRating(ratingStr)) {
                DialogHelper.showError("Rating must be between 1 and 5.");
                return;
            }

            int rating = Integer.parseInt(ratingStr);
            boolean success = controller.addFeedback(student, "", rating, comments);

            if (success) {
                DialogHelper.showInfo("Feedback added.");
                loadFeedbacks();
            } else {
                DialogHelper.showError("Failed to add feedback.");
            }
        }
    }

    private void deleteFeedback() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            DialogHelper.showError("Select a feedback entry to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(selected, 0);
        String studentName = (String) tableModel.getValueAt(selected, 1);

        if (DialogHelper.showConfirm("Delete feedback from " + studentName + "?")) {
            boolean success = controller.deleteFeedback(id);
            if (success) {
                DialogHelper.showInfo("Feedback deleted.");
                loadFeedbacks();
            } else {
                DialogHelper.showError("Failed to delete feedback.");
            }
        }
    }
}
