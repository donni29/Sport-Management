package Exam;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.Serial;

public class ArchivioPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;

    public ArchivioPanel(String nome) {
        this.nome = nome;


        JTable table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setGridColor(Color.LIGHT_GRAY);

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        // Set up column sizes.
        initColumnSizes(table);

        // Fiddle with the Sport column's cell editors/renderers.
        setUpSportColumn(table, table.getColumnModel().getColumn(0));

        // Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void setUpSportColumn(JTable table, TableColumn column) {
    }

    private void initColumnSizes(JTable table) {

    }

    private class MyTableModel extends AbstractTableModel {
        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getColumnCount() {
            return 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }
    }
}


