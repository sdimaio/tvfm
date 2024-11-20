package jexer;

import java.util.ArrayList;
import java.util.List;

import jexer.bits.BorderStyle;
import jexer.bits.CellAttributes;
import jexer.bits.StringUtils;
import jexer.event.TKeypressEvent;
import jexer.event.TMouseEvent;

/**
 * TTabbedPane is a widget that manages multiple tabs with panels. Each tab
 * displays its own content, and users can switch tabs using keyboard,
 * mouse, or navigation arrows.
 */
public class TTabbedPane extends TWidget {
    private final List<String> tabTitles = new ArrayList<>();
    private final List<TPanel> tabPanels = new ArrayList<>();
    private int currentTabIndex = 0;
    private int scrollIndex = 0; // For scrolling tabs when many are present

    public TTabbedPane(final TWidget parent, final int x, final int y, final int width, final int height) {
        super(parent, x, y, width, height);
    }

    /**
     * Adds a new tab to the pane.
     *
     * @param title the tab title
     * @param panel the content panel
     */
    public void addTab(String title, TPanel panel) {
        tabTitles.add(title);
        tabPanels.add(panel);

        // Add the panel to the widget tree but hide it by default
        panel.setVisible(false);
        addPanel(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());

        // Show the first tab by default
        if (tabTitles.size() == 1) {
            panel.setVisible(true);
        }
    }

    /**
     * Switches to the next tab.
     */
    private void nextTab() {
        if (currentTabIndex < tabTitles.size() - 1) {
            currentTabIndex++;
            updateActiveTab();
        } else if (scrollIndex + getMaxVisibleTabs() < tabTitles.size()) {
            scrollIndex++; // Scroll the tab bar
        }
    }

    /**
     * Switches to the previous tab.
     */
    private void previousTab() {
        if (currentTabIndex > 0) {
            currentTabIndex--;
            updateActiveTab();
        } else if (scrollIndex > 0) {
            scrollIndex--; // Scroll the tab bar
        }
    }

    /**
     * Updates the active tab to display its content.
     */
    private void updateActiveTab() {
        for (int i = 0; i < tabPanels.size(); i++) {
            tabPanels.get(i).setVisible(i == currentTabIndex);
        }
        getApplication().doRepaint();
    }

    /**
     * Gets the maximum number of tabs that can be displayed.
     */
    private int getMaxVisibleTabs() {
        int totalWidth = 0;
        int visibleTabs = 0;
        for (int i = scrollIndex; i < tabTitles.size(); i++) {
            totalWidth += tabTitles.get(i).length() + 4; // Tab width + padding
            if (totalWidth > getWidth() - 6) { // Space for arrows and X button
                break;
            }
            visibleTabs++;
        }
        return visibleTabs;
    }


    @Override
    public void draw() {
        CellAttributes background = getTheme().getColor("twindow.border");
        CellAttributes activeTab = getTheme().getColor("tcheckbox.checked");
        CellAttributes inactiveTab = getTheme().getColor("tcheckbox.unchecked");

        // Verifica che i colori siano inizializzati correttamente
        if (background == null) {
            background = new CellAttributes();
        }
        if (activeTab == null) {
            activeTab = new CellAttributes();
        }
        if (inactiveTab == null) {
            inactiveTab = new CellAttributes();
        }

        // Disegna la barra delle schede
        int xOffset = 0;
        for (int i = scrollIndex; i < tabTitles.size(); i++) {
            String title = " " + tabTitles.get(i) + " ";
            int tabWidth = title.length();
            if (xOffset + tabWidth > getWidth() - 6) { // Spazio per frecce e "X"
                break;
            }
            putStringXY(xOffset, 0, title, (i == currentTabIndex) ? activeTab : inactiveTab);

            // Disegna la "X" per chiudere la scheda
            putStringXY(xOffset + tabWidth - 1, 0, "X", inactiveTab);

            xOffset += tabWidth;
        }

        // Disegna le frecce di navigazione
        putStringXY(getWidth() - 4, 0, "<", inactiveTab); // Freccia sinistra
        putStringXY(getWidth() - 2, 0, ">", inactiveTab); // Freccia destra

        // Disegna il contenuto del pannello attivo
        if (!tabPanels.isEmpty() && currentTabIndex >= 0 && currentTabIndex < tabPanels.size()) {
            drawBox(0, 1, getWidth(), getHeight(), background,
                    background, BorderStyle.SINGLE, true);
            tabPanels.get(currentTabIndex).draw();
        }
    }


    @Override
    public void onKeypress(final TKeypressEvent keypress) {
        TKeypress key = keypress.getKey();
        if (key.getKeyCode() == TKeypress.TAB) {
            nextTab();
        } else if (key.getKeyCode() == TKeypress.LEFT) {
            previousTab();
        } else if (key.getKeyCode() == TKeypress.RIGHT) {
            nextTab();
        }
    }

    @Override
    public void onMouseDown(final TMouseEvent mouse) {
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();

        // Handle tab click
        if (mouseY == 0) {
            int xOffset = 0;
            for (int i = scrollIndex; i < tabTitles.size(); i++) {
                String title = " " + tabTitles.get(i) + " ";
                int tabWidth = title.length();

                // Check if the click is on the "X" to close the tab
                if (mouseX == xOffset + tabWidth - 1) {
                    tabTitles.remove(i);
                    tabPanels.remove(i);
                    if (currentTabIndex >= tabTitles.size()) {
                        currentTabIndex = tabTitles.size() - 1;
                    }
                    updateActiveTab();
                    return;
                }

                // Check if the click is on the tab
                if (mouseX >= xOffset && mouseX < xOffset + tabWidth) {
                    currentTabIndex = i;
                    updateActiveTab();
                    return;
                }

                xOffset += tabWidth;
            }

            // Handle arrow clicks for scrolling
            if (mouseX == getWidth() - 4) { // Left arrow
                previousTab();
            } else if (mouseX == getWidth() - 2) { // Right arrow
                nextTab();
            }
        }
    }
}

