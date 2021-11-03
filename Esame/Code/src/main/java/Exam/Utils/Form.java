package Exam.Utils;
/**
 * a class that create a Jframe for the management of a calendar appointment , which is an event to be created, or to be deleted
 */


import com.mindfusion.common.DateTime;
import com.mindfusion.common.DayOfWeek;
import com.mindfusion.scheduling.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Form extends JFrame implements ActionListener {

    private final JLabel nome;
    private final JButton insert;
    private final JButton delete;
    private final JTextField titolo;
    private final JTextField descrizione;
    private final JSpinner inizio;
    private final JSpinner fine;
    private final Item item1;
    private final JTextField ric;
    private final JCheckBox day;
    private final JCheckBox week;

    public Form(Item item, Object nome_struttura) throws ParseException {
        super("Gestione Evento");
        setLocation(250,150);
        setSize(650,600);
        Font font = new Font("Helvetica", Font.BOLD, 30);
        setFont(font);
        JPanel p1 = new JPanel(new GridLayout(1,2,5,5));
        p1.setAlignmentY(Component.CENTER_ALIGNMENT);
        JLabel l0 = new JLabel("nome_struttura:");
        nome = new JLabel(nome_struttura.toString());
        p1.add(l0);
        p1.add(nome);

        JPanel p2 = new JPanel(new GridLayout(1,2,5,5));

        JLabel l1 = new JLabel("Titolo Evento:");
        titolo = new JTextField(item.getHeaderText());
        titolo.setPreferredSize(new Dimension(1,30));
        p2.add(l1);
        p2.add(titolo);

        JPanel p3 = new JPanel(new GridLayout(1,2,5,5));
        JLabel l2 = new JLabel("Descrizione Evento:");
        descrizione = new JTextField(item.getDescriptionText());
        descrizione.setSize(1,70);
        p3.add(l2);
        p3.add(descrizione);

        JPanel p4 = new JPanel(new GridLayout(1,2,5,5));

        JLabel l3 = new JLabel("Ora Inizio:");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALIAN);
        Date start = formatter.parse(item.getStartTime().toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println(start.getTime());
        SpinnerDateModel sm = new SpinnerDateModel(start,null,null, java.util.Calendar.HOUR_OF_DAY);
        inizio = new JSpinner(sm);
        inizio.setEditor(new JSpinner.DateEditor(inizio,"yyyy-MM-dd HH:mm:ss"));
        JLabel l4 = new JLabel("Ora Fine:");
        Date end = formatter.parse(item.getEndTime().toString("yyyy-MM-dd HH:mm:ss"));
        SpinnerDateModel sf = new SpinnerDateModel(end,null,null, java.util.Calendar.HOUR_OF_DAY);
        fine = new JSpinner(sf);
        fine.setEditor(new JSpinner.DateEditor(fine,"yyyy-MM-dd HH:mm:ss"));

        p4.add(l3);
        p4.add(inizio);
        p4.add(l4);
        p4.add(fine);

        JPanel p5 = new JPanel(new GridLayout(1,2,5,5));
        JLabel l5 = new JLabel("Inserisci  Numero Ricorsioni");
        ric = new JTextField();
        if (item.getOccurrenceIndex() < 0)
            ric.setText("1");
        else
            ric.setText(String.valueOf(item.getOccurrenceIndex()));

        p5.add(l5);
        p5.add(ric);

        JPanel p7 = new JPanel((new GridLayout(1,3,5,5)));
        day = new JCheckBox("giornalmente");
        day.setSelected(false);
        week = new JCheckBox("settimanalmente");
        week.setSelected(false);
        JCheckBox month = new JCheckBox("mensilmente");
        month.setSelected(false);

        p7.add(day);
        p7.add(week);
        p7.add(month);
        JPanel p0 = new JPanel(new GridLayout(8,1,10,5));
        p0.add(p1);
        p0.add(p2);
        p0.add(p3);
        p0.add(p4);
        p0.add(p5);
        p0.add(p7);
        add(p0,BorderLayout.NORTH);

        JPanel p6 = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        insert = new JButton("Inserisci Evento");
        insert.setPreferredSize(new Dimension(120,30));
        insert.addActionListener(this);
        delete = new JButton("Elimina Evento");
        delete.setPreferredSize(new Dimension(120,30));
        delete.addActionListener(this);
        p6.add(insert);
        p6.add(delete);

        setVisible(true);
        item1 = item;


        add(p6,BorderLayout.PAGE_END);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.insert){
            item1.setHeaderText(titolo.getText());
            item1.setDescriptionText(descrizione.getText());


            Date str = (Date) inizio.getValue();
            item1.setStartTime(new DateTime(str));

            str = (Date) fine.getValue();

            item1.setEndTime(new DateTime(str));

            if (Integer.parseInt(ric.getText()) > 1){
                Recurrence recurrence = new Recurrence();
                recurrence.setStartDate(item1.getStartTime());
                System.out.println(item1.getStartTime());
                System.out.println(item1.getStartTime().getDayOfWeek().getValue());
                System.out.println(item1.getStartTime().getDayOfWeek().toString());
                System.out.println(DayOfWeek.Monday.getValue());
                int dw = item1.getStartTime().getDayOfWeek().getValue();
                if(this.day.isSelected()){
                    recurrence.setDay(DayOfWeekType.AnyDay);
                    recurrence.setDays(1);
                    recurrence.setDailyRecurrence(DailyRecurrence.ByDayInterval);
                    recurrence.setNumOccurrences(Integer.parseInt(ric.getText()));
                    recurrence.setRecurrenceEnd(RecurrenceEnd.NumOccurrences);
                }else if (this.week.isSelected()){
                    recurrence.setDay(getDayOfWeek(dw));
                    recurrence.setDays(7);
                    System.out.println(recurrence.getDay());
                    recurrence.setDailyRecurrence(DailyRecurrence.ByDayInterval);
                    recurrence.setNumOccurrences(Integer.parseInt(ric.getText()));
                    recurrence.setRecurrenceEnd(RecurrenceEnd.NumOccurrences);
                } else {
                    recurrence.setDay(getDayOfWeek(dw));
                    recurrence.setMonthlyRecurrence(MonthlyRecurrence.ByDayNumber);
                    recurrence.setNumOccurrences(Integer.parseInt(ric.getText()));
                    recurrence.setRecurrenceEnd(RecurrenceEnd.NumOccurrences);
                }
                item1.setRecurrence(recurrence);
            }
            dispose();
            try {
                InsertEvento(item1,nome.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (e.getSource() == this.delete){
            item1.setHeaderText(titolo.getText());
            item1.setDescriptionText(descrizione.getText());



            Date  str = (Date) inizio.getValue();
            item1.setStartTime(new DateTime(str));

            str = (Date) fine.getValue();

            item1.setEndTime(new DateTime(str));
            dispose();
            try {
                DeleteEvento(item1,nome.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
    /**
     * method that allows to derive the day of the week from a specific integer value and I return it as Dayofweektype
     *
     */
    private DayOfWeekType getDayOfWeek(int i) {
        return switch (i){
            case 1 -> DayOfWeekType.Monday;
            case 2 -> DayOfWeekType.Tuesday;
            case 3 -> DayOfWeekType.Wednesday;
            case 4 -> DayOfWeekType.Thursday;
            case 5 -> DayOfWeekType.Friday;
            case 6 -> DayOfWeekType.Saturday;
            case 0 -> DayOfWeekType.Sunday;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }
    /**
     * method that allows to insert a new appointment or a recurrence of appointments  in the DB
     * @param item the new appointment
     * @param nome_struttura  the structure on which to insert the appointments
     * @throws SQLException if there is no table Calendario or a problem with entering data( es: primary key conflict)
     */
    public void InsertEvento(Item item, Object nome_struttura) throws SQLException {
        if(item.getRecurrenceState() == RecurrenceState.Master){
            System.out.println(item.getRecurrence().getNumOccurrences());
            Statement statement = DBManager.getConnection().createStatement();
            String query = String.format("INSERT INTO CALENDARIO (nome_struttura,info_prenotazione,descrizione_prenotazione,inizio_prenotazione,fine_prenotazione,numero_ricorsioni) values ('%s','%s','%s','%s','%s','%d')",
                    nome_struttura,
                    item.getHeaderText(),
                    item.getDescriptionText(),
                    item.getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                    item.getEndTime().toString("yyyy-MM-dd HH:mm:ss"),
                    item.getRecurrence().getNumOccurrences());
            statement.executeUpdate(query);
            statement.close();
            int i = 1;
            while (item.getRecurrence().getNumOccurrences() > i){
                Statement statement1 = DBManager.getConnection().createStatement();
                String query1 = String.format("INSERT INTO CALENDARIO (nome_struttura,info_prenotazione,descrizione_prenotazione,inizio_prenotazione,fine_prenotazione,numero_ricorsioni) values ('%s','%s','%s','%s','%s','%d')",
                        nome_struttura,
                        item.getRecurrence().getOccurrence(i).getHeaderText(),
                        item.getRecurrence().getOccurrence(i).getDescriptionText(),
                        item.getRecurrence().getOccurrence(i ).getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                        item.getRecurrence().getOccurrence(i).getEndTime().toString("yyyy-MM-dd HH:mm:ss"),
                        item.getRecurrence().getNumOccurrences() - item.getRecurrence().getOccurrence(i).getOccurrenceIndex());
                statement1.executeUpdate(query1);
                statement1.close();
                i++;
            }
        }else {
            Statement statement = DBManager.getConnection().createStatement();
            String query = String.format("INSERT INTO CALENDARIO (nome_struttura,info_prenotazione,descrizione_prenotazione,inizio_prenotazione,fine_prenotazione) values ('%s','%s','%s','%s','%s')",
                    nome_struttura,
                    item.getHeaderText(),
                    item.getDescriptionText(),
                    item.getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                    item.getEndTime().toString("yyyy-MM-dd HH:mm:ss"));
            statement.executeUpdate(query);
            statement.close();
        }
    }

    /**
     * method that allows to delete a single appointment in the DB
     * @param item the appointment that must be cancelled
     * @param nome_struttura the name of the cancelled appointment structure
     * @throws SQLException if there is no table Calendario or a problem with entering data
     */
    public void DeleteEvento(Item item, Object nome_struttura) throws SQLException{
        Statement statement =  DBManager.getConnection().createStatement();
        String query = String.format("DELETE FROM CALENDARIO WHERE info_prenotazione LIKE '%s' and nome_struttura like '%s' and inizio_prenotazione like '%s'",
                item.getHeaderText(),
                nome_struttura,
                item.getStartTime().toString("yyyy-MM-dd HH:mm:ss")
        );
        statement.executeUpdate(query);
        statement.close();
    }
}
