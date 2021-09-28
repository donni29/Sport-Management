package Exam;

//questo è il nostro progetto, madonne//

import Exam.Utils.Utils;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.Objects;

public class SBM extends JFrame implements ActionListener, MenuListener {
    @Serial
    private static final long serialVersionUID = 1L;
    private static JMenu openArchivio;
    private static JMenuItem close;
    private static JMenuItem Atleti;
    private static JMenuItem Allenatori;
    private static JMenuItem Dirigenti;
    private static JMenuItem Open;
    private static JMenuItem Nuovo;
    private static JTextArea textArea;
    private static JMenu Strutture;
    private static JMenuItem CambiaPsw;
    private static JMenuItem NewUser;
    private static JMenuItem add_del_Sport;

    DB_Model model;

    public SBM() {
        super("Sport Business Management ");

        Utils.List_init();

        JMenuBar menuBar = new JMenuBar();
        JMenu Archivio = new JMenu("Archivio");
        menuBar.add(Archivio);
        JMenu Rimborso = new JMenu("Rimborso");
        Strutture = new JMenu("Strutture");
        menuBar.add(Rimborso);
        Strutture.addMenuListener(this);
        menuBar.add(Strutture);
        menuBar.add(Box.createHorizontalGlue());
        JMenu Impostazioni = new JMenu("Impostazioni");
        menuBar.add(Impostazioni);


        setContentPane(new DesktopTop());
        setVisible(true);


        openArchivio = new JMenu("Open Archivio di ...");
        openArchivio.addSeparator();
        //openArchivio.addActionListener(this);
        Archivio.add(openArchivio);

        //parte codice menu Archivio

        Atleti = new JMenuItem("Atleti");
        Atleti.addActionListener(this);
        openArchivio.add(Atleti);

        Allenatori = new JMenuItem("Allenatori");
        Allenatori.addActionListener(this);
        openArchivio.add(Allenatori);

        Dirigenti = new JMenuItem("Dirigenti");
        Dirigenti.addActionListener(this);
        openArchivio.add(Dirigenti);

        close = new JMenuItem("Close");
        close.addActionListener(this);
        Archivio.add(close);

        //parte codice menu Rimborso
        Nuovo = new JMenuItem("Nuovo...");
        Nuovo.addActionListener(this);
        Rimborso.add(Nuovo);

        Open = new JMenuItem("Apri...");
        Open.addActionListener(this);
        Rimborso.add(Open);

        // parte di menù Impostazioni

        JMenu modifica_Login = new JMenu("Modifica Login...");
        modifica_Login.addActionListener(this);
        Impostazioni.add(modifica_Login);

        NewUser = new JMenuItem("Aggiungi User");
        NewUser.addActionListener(this);
        modifica_Login.add(NewUser);

        CambiaPsw = new JMenuItem("Cambia Password");
        CambiaPsw.addActionListener(this);
        modifica_Login.add(CambiaPsw);

        add_del_Sport = new JMenuItem("Aggiungi/Elimina Sport");
        add_del_Sport.addActionListener(this);
        Impostazioni.add(add_del_Sport);

        setJMenuBar(menuBar);

        setSize(1000,600);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        try {
            model = new DB_Model();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            DesktopTop desktopTop    = new DesktopTop();
            setContentPane(desktopTop);
            setVisible(true);

        } else {
            if (e.getSource() == Open) {
                JFileChooser openSource = new JFileChooser();

                int option = openSource.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    textArea.setText("");

                    try {
                        File file = new File((openSource.getSelectedFile().getPath()));
                        System.out.println(new FileReader(openSource.getSelectedFile().getPath()));
                        if (!Desktop.isDesktopSupported()) {
                            System.out.println("not supported");
                        }
                        Desktop desktop = Desktop.getDesktop();
                        if (file.exists()) {
                            desktop.open(file);
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        System.out.println("IOException" + "\n" + e2);
                    }
                }
            }
            if (e.getSource() == Atleti) {
                try {
                    String query = "SELECT * FROM Persona WHERE tipo like 'Atleta'";
                    PersonaPanel pp = new PersonaPanel(query);
                    setContentPane(pp);
                    setVisible(true);
                } catch (SQLException throwables) {
                    System.out.println("Errore Database Atleta" + throwables);
                    throwables.printStackTrace();
                }


            }
            if (e.getSource() == Allenatori) {
                try {
                    String query = "SELECT * FROM Persona WHERE tipo like 'Allenatore'";
                    PersonaPanel pp = new PersonaPanel(query);
                    setContentPane(pp);
                    setVisible(true);
                } catch (SQLException throwables) {
                    System.out.println("Errore Database Allenatore" + throwables);
                    throwables.printStackTrace();
                }
            }
            if (e.getSource() == Dirigenti) {
                try {
                    String query = "SELECT * FROM Persona WHERE tipo like 'Dirigente'";
                    PersonaPanel pp = new PersonaPanel(query);
                    setContentPane(pp);
                    setVisible(true);
                } catch (SQLException throwables) {
                    System.out.println("Errore Database Dirigente" + throwables);
                    throwables.printStackTrace();
                }

            }
            if (e.getSource() == Nuovo) {
                Rimborso rim = new Rimborso();
                setContentPane(rim);
                setVisible(true);
            }
            if(e.getSource() == Strutture){
                StrutturaPanel st = new StrutturaPanel();
                setContentPane(st);
                setVisible(true);
            }
            if (e.getSource() == NewUser){
                new Change_User_Password_Sport(0);
            }
            if (e.getSource() == CambiaPsw){
                new Change_User_Password_Sport(1);
            }
            if(e.getSource() == add_del_Sport){
                new Change_User_Password_Sport(2);
            }
        }
    }


    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource() == Strutture) {
            StrutturaPanel st = new StrutturaPanel();
            setContentPane(st);
            setVisible(true);
        }
    }
    //questi due metodi implementano la classe per non far diventare la classe abstract
    @Override
    public void menuDeselected(MenuEvent e) {
    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }
}

class DesktopTop extends JPanel {

    public DesktopTop() {
        ImageIcon sportIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/sportinsime.jpg")));
        Image image = sportIcon.getImage();
        Image newing = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        sportIcon = new ImageIcon(newing);
        JLabel jLabelObject = new JLabel();
        jLabelObject.setText("<html>Welcome to <br> SPORTINSIEME's <br> Sport Management");
        jLabelObject.setIcon(sportIcon);
        Font font = new Font("Helvetica", Font.BOLD, 30);
        jLabelObject.setFont(font);
        add(jLabelObject);
        setSize(900, 600);
        setVisible(true);
    }
}