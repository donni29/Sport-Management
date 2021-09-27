package Exam;

import Exam.Utils.Persona;
import Exam.Utils.PersonaTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;


public class SearchFrame extends JFrame implements ActionListener {

    private final JButton bsearch;
    private final JTextField tfserach;
    private final JButton binsert;
    public static JTextField tfcf;
    private JTable table;
    private List<Persona> personas;

    public SearchFrame() {
        super("Search Athlete Frame");

        bsearch = new JButton("Search Athlete");
        bsearch.addActionListener(this);
        tfserach = new JTextField("");
        binsert = new JButton("Insert CF");
        binsert.addActionListener(this);
        tfcf = new javax.swing.JTextField("");

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(1, 3,5,5));
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(new Label("Insert Cognome: "));
        panel.add(tfserach);
        panel.add(bsearch);

        JPanel panel1 = new JPanel(new GridLayout(1, 2,5,5));
        panel1.add(tfcf);
        panel1.add(binsert);

        JPanel panel2 = new JPanel(new BorderLayout(10,5));
        panel2.add(panel, BorderLayout.NORTH);
        panel2.add(panel1, BorderLayout.PAGE_END);

        add(panel2, BorderLayout.NORTH);

        setSize(1000, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.bsearch) {
            Table();
            setVisible(true);
        }

        if (e.getSource() == this.binsert) {
            Rimborso.tcf.setText(tfcf.getText());
            System.out.println(tfcf.getText());
            dispose();
        }
    }


    public void Table() {
        String query = String.format("SELECT * FROM Persona WHERE cognome like '%s'", tfserach.getText());
        PersonaPanel pp = null;
        try {
            pp = new PersonaPanel(query);
            personas = pp.getListPersona(query);
            if (personas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Non sono stati riscontrati valori!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (!personas.isEmpty()) {
            table = new JTable();
            PersonaTableModel model = new PersonaTableModel(personas);
            table.setModel(model);
            table.setAutoCreateRowSorter(true);

            add(new JScrollPane(table), BorderLayout.CENTER);
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int selectedPersonaIndex = table.getSelectedRow();
                    Persona person = personas.get(selectedPersonaIndex);

                    tfcf.setText(person.getCF());
                }
            });
            setVisible(false);
        }
    }
}

