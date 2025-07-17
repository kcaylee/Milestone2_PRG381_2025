package controller;

import javax.swing.*;
import java.util.HashMap;

/**
 * NavigationController handles tab-based navigation for the Dashboard GUI.
 * It maps tab titles to their indexes and allows switching tabs by name or index.
 * Supports GUI expectations for Milestone 2 (menu/tab navigation).
 */
public class NavigationController {

    private final JTabbedPane tabbedPane;
    private final HashMap<String, Integer> tabIndexMap = new HashMap<>();

    /**
     * Constructs a NavigationController for the specified JTabbedPane.
     * @param tabbedPane the tab container used in the GUI.
     */
    public NavigationController(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        initializeTabIndexMap();
    }

    /**
     * Builds a mapping between tab titles and their index positions.
     * This allows for navigation using tab names (e.g., "Appointments").
     */
    private void initializeTabIndexMap() {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            String title = tabbedPane.getTitleAt(i);
            tabIndexMap.put(title, i);
        }
    }

    /**
     * Navigates to the tab with the given title (e.g., "Feedback").
     * If the title is not found, prints an error.
     * @param tabName the name/title of the tab to switch to
     */
    public void navigateTo(String tabName) {
        Integer index = tabIndexMap.get(tabName);
        if (index != null) {
            tabbedPane.setSelectedIndex(index);
        } else {
            System.err.println("Tab not found: " + tabName);
        }
    }

    /**
     * Navigates to the tab at the given index.
     * @param index the numeric index of the tab
     */
    public void navigateTo(int index) {
        if (index >= 0 && index < tabbedPane.getTabCount()) {
            tabbedPane.setSelectedIndex(index);
        } else {
            System.err.println("Invalid tab index: " + index);
        }
    }
}
