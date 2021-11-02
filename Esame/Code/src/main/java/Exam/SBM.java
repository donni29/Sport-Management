package Exam;


/**
 *Implement a SBM class representing the core JFrame of this Desktop Application.
 *<p>
 *The class extends a JFrame with one constructor:
 *SBM() - Setting all the components of the JFrame.
 *
 *<p>
 * @authors Rossi Nicolò Delsante Laura
 */

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
    private static JMenuItem close;
    private static JMenuItem Atleti;
    private static JMenuItem Allenatori;
    private static JMenuItem Dirigenti;
    private static JMenuItem Open;
    private static JMenuItem Nuovo;
    private static JMenu Strutture;
    private static JMenu Impostazioni;

    public SBM() {
        super("Sport Business Management ");

        try {
            Utils.List_init();
            Utils.Create_Dir();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.createImage(Objects.requireNonNull(this.getClass().getResource("/sportinsime.jpg")));
        setIconImage(image);

        JMenuBar menuBar = new JMenuBar();
        JMenu Archivio = new JMenu("Archivio");
        menuBar.add(Archivio);
        JMenu Rimborso = new JMenu("Rimborso");
        menuBar.add(Rimborso);
        Strutture = new JMenu("Strutture");
        Strutture.addMenuListener(this);
        menuBar.add(Strutture);
        menuBar.add(Box.createHorizontalGlue());
        Impostazioni = new JMenu("Impostazioni");
        Impostazioni.addMenuListener(this);
        menuBar.add(Impostazioni);


        setContentPane(new DesktopTop());
        setVisible(true);

        JMenu openArchivio = new JMenu("Open Archivio di ...");
        Archivio.add(openArchivio);

        /* parte di codice che implementa il menu */

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

        setJMenuBar(menuBar);

        setSize(1040,600);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            DesktopTop desktopTop    = new DesktopTop();
            setContentPane(desktopTop);
            setVisible(true);

        } else {
            if (e.getSource() == Open) {
                JFileChooser openSource = new JFileChooser();
                openSource.setCurrentDirectory(new File(Utils.path1));

                int option = openSource.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    JTextArea textArea = new JTextArea();
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
        }
    }


    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource() == Strutture) {
            StrutturaPanel st = null;
            try {
                st = new StrutturaPanel();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            setContentPane(st);
            setVisible(true);
        }
        if (e.getSource() == Impostazioni){
            new Impostazioni_Frame();
        }
    }
    //questi due metodi implementano la classe per non far diventare la classe abstract
    @Override
    public void menuDeselected(MenuEvent e) {}
    @Override
    public void menuCanceled(MenuEvent e) {}
}

/**
 * This Inner Class implements a JPanel to create the home screen of the application.
 *
 * @authors Rossi Nicolò Delsante Laura
 */

class DesktopTop extends JPanel {

    public DesktopTop() {
        super();
        setLayout(new BorderLayout());
        ImageIcon sportIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/sportinsime.jpg")));
        Image image = sportIcon.getImage();
        Image newing = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        sportIcon = new ImageIcon(newing);
        JLabel jLabelObject = new JLabel();
        jLabelObject.setText("<html>Welcome to  SPORTINSIEME's  Sport Management <br/> Created and Managed By Students: <br/> Rossi Nicol\u00F2 & Delsante Laura");
        jLabelObject.setIcon(sportIcon);
        Font font = new Font("Helvetica", Font.BOLD, 30);
        jLabelObject.setFont(font);
        jLabelObject.setHorizontalAlignment(JLabel.CENTER);
        add(jLabelObject,BorderLayout.CENTER);
        setSize(900, 600);
        setVisible(true);
    }
}