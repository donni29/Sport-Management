package Exam;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class ArchivioPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private JPanel Archivio;
    private JButton Button;
    JTextArea text= new JTextArea();


    public  ArchivioPanel (){
            Archivio = new JPanel();
            Button = new JButton( "CAVOLI");
            Button.setSize(20,20);
            Archivio.add(Button);
            add(Archivio);
    }
}


