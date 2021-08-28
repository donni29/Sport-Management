package Exam.Utils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;

public class JtableRef extends JPanel {
    private static String column[]= {"Data","Località","Campionato","Titolo Prestazione","Compenso"};
    private ArrayList<Date> date;


    public JtableRef(ArrayList<Date> datelist) {
        this.date =datelist;
        JTable table =new JTable();
        DefaultTableModel dm =new DefaultTableModel();







    }





    public int getColumnCount(){
        return  column.length;
    }

    public int getRowsCount(){
        return date.size();
    }
}


    class JtableModelRef extends AbstractTableModel{
        private ArrayList<Date> dates;
        private String[] columns;

        public JtableModelRef(ArrayList<Date> dates) {



        }

        @Override
        public int getRowCount() {
            return columns.length;
        }

        @Override
        public int getColumnCount() {
            return dates.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }
    }
