package Exam.Utils;
/**
 * Class for build our own Table Model
 *
 *@authors Rossi Nicolò Delsante Laura
 */

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonaTableModel extends AbstractTableModel {

    private final String[] ColumnNames ={"Nome","Cognome","Tipo","Luogo di Nascita"," Data di Nascita","Residenza","CF","Sport","squadra","Telefono"};
    private final List<Persona> personaList;

    /**
     * Constructor to shape our TableModel
     * @param personaList - List<Person> passed to
     */
    public PersonaTableModel(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @Override
    public int getRowCount() {
        return personaList.size();
    }

    @Override
    public int getColumnCount() {
        return ColumnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex){
            case 0 ->personaList.get(rowIndex).getNome();
            case 1 -> personaList.get(rowIndex).getCognome();
            case 2 -> personaList.get(rowIndex).getTipo();
            case 3 -> personaList.get(rowIndex).getLuogo_nascita();
            case 4 -> personaList.get(rowIndex).getData_nascita();
            case 5 -> personaList.get(rowIndex).getresidenza();
            case 6 -> personaList.get(rowIndex).getCF();
            case 7 -> personaList.get(rowIndex).getSport();
            case 8 -> personaList.get(rowIndex).getSquadra();
            case 9 -> personaList.get(rowIndex).getTelefono();
            default -> "-";
        };
    }

    /**
     * To get the column's name
     * @param column - index of column
     * @return - String provides the column's name
     */
    public String getColumnName(int column){
        return  ColumnNames[column];
    }


}

