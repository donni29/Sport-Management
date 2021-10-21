package Exam;

import Exam.Utils.Calendario;
import Exam.Utils.DBManager;
import com.mindfusion.common.DateTime;
import com.mindfusion.common.DayOfWeek;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;
import com.mindfusion.scheduling.standardforms.AppointmentForm;
import com.mindfusion.scheduling.standardforms.DialogResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class  CreateCalendar extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private Calendar calendar;
    private Boolean Stato;
    public static java.util.Calendar calen_i = null;
    public static java.util.Calendar calen_f = null;


    public CreateCalendar( Object nome_struttura) throws SQLException, ClassNotFoundException {
        setLayout(new BorderLayout(10,10));
        setSize(600,400);

        calendar = new Calendar();

        calendar.beginInit();
        calendar.setCurrentView(CalendarView.Timetable);
        calendar.getTimetableSettings().setTwelveHourFormat(false);
        calendar.setTheme(ThemeType.Windows2003);

        for (int i= 1 ; i < 7; i++){
            calendar.getTimetableSettings().getDates().add(DateTime.today().addDays(i));
        }
        //calendar.getTimetableSettings().setItemOffset(30);
        //calendar.getTimetableSettings().setShowItemSpans(false);
        //calendar.getTimetableSettings().setSnapInterval(Duration.fromMinutes(1));
        calendar.getTimetableSettings().setStartTime(510);
        calendar.getTimetableSettings().setEndTime(1020);
        calendar.getTimetableSettings().setVisibleColumns(8);
        calendar.getTimetableSettings().setShowNavigationButtons(true);
        calendar.getTimetableSettings().setScrollStep(7);
        calendar.getTimetableSettings().setCellSize(32);
        calendar.setEnableDragCreate(true);

        calendar.endInit();

        calendar.addCalendarListener(new CalendarAdapter(){
            private void showForm(Item item){
                AppointmentForm form = new AppointmentForm(calendar.getSchedule());
                form.setAppointment((Appointment)item );

                form.setVisible(true);
                form.addWindowListener(new WindowAdapter()
                {


                    @Override
                    public void windowClosed(WindowEvent we)
                    {
                        if (form.getDialogResult() == DialogResult.Remove)
                        {
                            try {
                                if (item.getRecurrenceState() == RecurrenceState.Occurrence ||
                                        item.getRecurrenceState() == RecurrenceState.Exception) {
                                    item.getRecurrence().markException(item, true);
                                    DeleteEvento(item,nome_struttura);
                                } else {
                                    calendar.getSchedule().getItems().remove(item);
                                    DeleteEvento(item, nome_struttura);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }else{
                            try {
                                InsertEvento(item, nome_struttura);

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                });

            }

            @Override
            public void itemModified(ItemModifiedEvent itemModifiedEvent) {
                try {
                    UpdateEvento(itemModifiedEvent,nome_struttura);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void itemClick(ItemMouseEvent e) {
                showForm(e.getItem());
            }

            @Override
            public void itemCreated(ItemEvent e) {
                calendar.getSelection().reset();
                calendar.getSelection().add(e.getItem().getStartTime(),
                        e.getItem().getEndTime());
                showForm(e.getItem());
            }
        });
        try {
            Calendario PC  =  establishConnection(nome_struttura);
            if (PC == null) {
                JOptionPane.showMessageDialog(this," errore nel database ! ");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        add(calendar,BorderLayout.CENTER);
        setVisible(true);
    }

    private Calendario establishConnection(Object nome_struttura) throws ClassNotFoundException, SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        Calendario Cal = null;
        try {
            String query = String.format("SELECT * FROM CALENDARIO WHERE NOME_STRUTTURA LIKE '%s' ",nome_struttura);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("nome_struttura"));
                System.out.println(rs.getString("inizio_prenotazione"));
                System.out.println(rs.getString("fine_prenotazione"));
                System.out.println(rs.getString("info_prenotazione"));

                new Swap(rs.getString("inizio_prenotazione"));
                System.out.println(calen_i.get(java.util.Calendar.YEAR));
                System.out.println(calen_i.get(java.util.Calendar.MONTH));
                System.out.println(calen_i.get(java.util.Calendar.DAY_OF_MONTH));
                new Swap(rs.getString("fine_prenotazione"));

                Cal = new Calendario(rs.getString("nome_struttura"),
                        rs.getString("info_prenotazione"),
                        new DateTime( calen_i.get(java.util.Calendar.YEAR),
                                calen_i.get(java.util.Calendar.MONTH) + 1,
                                calen_i.get(java.util.Calendar.DAY_OF_MONTH),
                                calen_i.get(java.util.Calendar.HOUR_OF_DAY),
                                calen_i.get(java.util.Calendar.MINUTE),
                                calen_i.get(java.util.Calendar.SECOND)),
                        new DateTime(calen_f.get(java.util.Calendar.YEAR),
                                calen_f.get(java.util.Calendar.MONTH) + 1,
                                calen_f.get(java.util.Calendar.DAY_OF_MONTH),
                                calen_f.get(java.util.Calendar.HOUR_OF_DAY),
                                calen_f.get(java.util.Calendar.MINUTE),
                                calen_f.get(java.util.Calendar.SECOND)),
                        rs.getInt("numero_ricursioni"));

                Appointment a = new Appointment();
                a.setId(Cal.getNome_struttura());
                a.setHeaderText(Cal.getInfo_struttura());
                //a.setDescriptionText(Cal.getInfo_struttura());
                a.setStartTime(Cal.getInizio_prenotazione());
                a.setEndTime(Cal.getFine_prenotazione());
                //a.setRecurrence(new Recurrence().setInterval(new Duration()));
                a.setAllowChangeEnd(true);
                a.setAllowChangeStart(true);
                if (Cal.getNumero_ricursioni() > 1){
                    int DayIndex = calen_i.get(java.util.Calendar.DAY_OF_WEEK);
                    Recurrence recurrence = new Recurrence();
                    //recurrence.setPattern(RecurrencePattern.Weekly);
                    recurrence.setDay(getDayOfWeek(DayIndex));
                    recurrence.setStartDate(Cal.getInizio_prenotazione());
                    recurrence.setNumOccurrences(Cal.getNumero_ricursioni());
                    recurrence.setRecurrenceEnd(RecurrenceEnd.NumOccurrences);
                    a.setRecurrence(recurrence);
                }


                calen_f = null;
                calen_i = null;
                calendar.getSchedule().getItems().add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Cal;
    }

    private DayOfWeekType getDayOfWeek(int i) {
        switch (i) {
            case 1:
                return DayOfWeekType.Monday;
            case 2:
                return DayOfWeekType.Tuesday;
            case 3:
                return DayOfWeekType.Wednesday;
            case 4:
                return DayOfWeekType.Thursday;
            case 5:
                return DayOfWeekType.Friday;
            case 6:
                return DayOfWeekType.Saturday;
        }

        return DayOfWeekType.Sunday;
    }


    public class Swap extends java.util.Calendar {
        public Swap(String date) {
            try {
                if (calen_i == null) {
                    calen_i = new GregorianCalendar();

                    calen_i.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));

                } else {
                    calen_f = new GregorianCalendar();

                    calen_f.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void computeTime() {

        }

        @Override
        protected void computeFields() {

        }

        @Override
        public void add(int field, int amount) {

        }

        @Override
        public void roll(int field, boolean up) {

        }

        @Override
        public int getMinimum(int field) {
            return 0;
        }

        @Override
        public int getMaximum(int field) {
            return 0;
        }

        @Override
        public int getGreatestMinimum(int field) {
            return 0;
        }

        @Override
        public int getLeastMaximum(int field) {
            return 0;
        }
    }
    public void InsertEvento(Item item, Object nome_struttura) throws SQLException {
        if(item.getRecurrenceState() == RecurrenceState.Master || item.getRecurrenceState() == RecurrenceState.Occurrence ||
                item.getRecurrenceState() == RecurrenceState.Exception ){
            System.out.println(item.getRecurrence().getNumOccurrences());
            int i = 1;
            while (item.getRecurrence().getNumOccurrences() > i){
                Statement statement = DBManager.getConnection().createStatement();
                String query = String.format("INSERT INTO CALENDARIO (nome_struttura,info_prenotazione,inizio_prenotazione,fine_prenotazione,numero_ricursioni) values ('%s','%s','%s','%s','%d')",
                        nome_struttura,
                        item.getRecurrence().getOccurrence(i).getHeaderText(),
                        item.getRecurrence().getOccurrence(i ).getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                        item.getRecurrence().getOccurrence(i).getEndTime().toString("yyyy-MM-dd HH:mm:ss"),
                        item.getRecurrence().getNumOccurrences());
                statement.executeUpdate(query);
                statement.close();
                i++;
            }
        }else {
            Statement statement = DBManager.getConnection().createStatement();
            String query = String.format("INSERT INTO CALENDARIO (nome_struttura,info_prenotazione,inizio_prenotazione,fine_prenotazione) values ('%s','%s','%s','%s')",
                    nome_struttura,
                    item.getHeaderText(),
                    item.getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                    item.getEndTime().toString("yyyy-MM-dd HH:mm:ss"));
            statement.executeUpdate(query);
            statement.close();
        }
    }
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
    public void UpdateEvento(ItemModifiedEvent itemModifiedEvent, Object nome_struttura)  throws  SQLException{
        Statement statement =  DBManager.getConnection().createStatement();
        String query = String.format("UPDATE CALENDARIO SET inizio_prenotazione = '%s' ,fine_prenotazione = '%s'  WHERE nome_struttura LIKE '%s' and inizio_prenotazione like '%s'",
                itemModifiedEvent.getItem().getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                itemModifiedEvent.getItem().getEndTime().toString("yyyy-MM-dd HH:mm:ss"),
                nome_struttura,
                itemModifiedEvent.getOldStartTime().toString("yyyy-MM-dd HH:mm:ss"));
        statement.executeUpdate(query);
        statement.close();
    }
}

