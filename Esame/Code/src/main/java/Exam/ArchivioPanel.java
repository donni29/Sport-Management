package Exam;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class ArchivioPanel extends JPanel implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    JTextArea text= new JTextArea();


    public ArchivioPanel(String nome) {
        if (nome == "atleti"){
            JPanel pane =new JPanel();
            JButton prova =new JButton();
            prova.add("prova",text);
            pane.add(prova);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


