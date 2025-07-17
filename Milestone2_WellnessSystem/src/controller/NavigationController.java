package controller;

import javax.swing.*;
import java.util.HashMap;

public class NavigationController {

    private final JTabbedPane tabbedPane;
    private final HashMap<String, Integer> tabIndexMap = new HashMap<>();

    public NavigationController(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        initializeTabIndexMap();
    }

    // Assigns names to indexes so you can use strings like "Appointments" to navigate
    private void initializeTabIndexMap() {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            String title = tabbedPane.getTitleAt(i);
            tabIndexMap.put(title, i);
        }
    }

    // Navigate to tab by name
    public void navigateTo(String tabName) {
        Integer index = tabIndexMap.get(tabName);
        if (index != null) {
            tabbedPane.setSelectedIndex(index);
        } else {
            System.err.println("❌ Tab not found: " + tabName);
        }
    }

    // Navigate to tab by index
    public void navigateTo(int index) {
        if (index >= 0 && index < tabbedPane.getTabCount()) {
            tabbedPane.setSelectedIndex(index);
        } else {
            System.err.println("❌ Invalid tab index: " + index);
        }
    }
}
