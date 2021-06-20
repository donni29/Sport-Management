package Exam;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SBM extends JFrame implements ActionListener {
    private static final long serialVersionUID =1L;
    private static JMenu openArchivio;
    private static JMenuItem close;
    private static JMenuItem Atleti;
    private static JMenuItem Allenatori;
    private static JMenuItem Dirigenti;
    private static JMenuItem Nuovo;
    private static JMenuItem Open;
    private static JFileChooser OpenSource;
    int selectedItem;
    /*
    Creare una variabile model( come nell'esempio SM_Updatable < sausagemanager < jdbc per separare la grafica dai dati
     */
    DB_Model model;

    public SBM(){
        super("Sport Business Management ");


        JMenuBar menuBar =new JMenuBar();
        JMenu Archivio =new JMenu("Archivio");
        menuBar.add(Archivio);
        JMenu Rimborso =new JMenu("Rimborso");
        JMenu Strutture =new JMenu("Strutture");
        menuBar.add(Rimborso);
        menuBar.add(Strutture);

        openArchivio =new JMenu("Open Archivio di ...");
        openArchivio.addSeparator();
        openArchivio.addActionListener(this);
        Archivio.add(openArchivio);

        //parte codice menu Archivio//

        Atleti =new JMenuItem("Atleti");
        Atleti.addActionListener(this);
        openArchivio.add(Atleti);

        Allenatori =new JMenuItem("Allenatori");
        Allenatori.addActionListener(this);
        openArchivio.add(Allenatori);

        Dirigenti = new JMenuItem("Dirigenti");
        Dirigenti.addActionListener(this);
        openArchivio.add(Dirigenti);

        close = new JMenuItem("Close");
         close.addActionListener(this);
        Archivio.add(close);

        //parte codice menu Rimborso//
        Nuovo = new JMenuItem("Nuovo...");
        Nuovo.addActionListener(this);
        Rimborso.add(Nuovo);
        //prova git//

        Open=new JMenuItem("Apri...");
        Open.addActionListener(this);
        Rimborso.add(Open);



        setJMenuBar(menuBar);
        setSize(640,480);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        try {
           /* System.out.println(("ciao"));*/
            model = new DB_Model();
            /*il problema è questo catch che non capisco perchè ci entro!!
            il resto ora funziona*/

        } catch (SQLException | NullPointerException e ){
            JOptionPane.showMessageDialog(this,"Errore nel Database!!");
        }
        // ShowItem(); come nel esempio del prof :  SM_Updatable < sausagemanager < jdbc
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == this.close) {
            dispose();
        }


        else
        {
            if (e.getSource() == this.Open){
                OpenSource =new JFileChooser();

                 int option = OpenSource.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION){

                 }
            }
        }
    }
}
