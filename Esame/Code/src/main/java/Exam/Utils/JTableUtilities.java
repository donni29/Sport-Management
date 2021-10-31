package Exam.Utils;
/**
 * Class for implementing form of our table
 *
 * @authors Rossi Nicolò Delsante Laura
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class JTableUtilities {
    /**
     *Constructor to set the alignment in our Table
     * @param table - table which entries we want to align
     * @param alignment - index for the position
     */
    public static void setCellsAlignment(JTable table, int alignment) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(alignment);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
    }
}
