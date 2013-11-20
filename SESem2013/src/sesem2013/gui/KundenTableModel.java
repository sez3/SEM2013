package sesem2013.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sesem2013.entities.Kunde;

public class KundenTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnName = { "Id", "Name" };
	
	private List<Kunde> kunden;
	
	public void setKunden(List<Kunde> kunden) {
		this.kunden = kunden;
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		if(kunden==null){
			return 0;
		}else{
			return kunden.size();
		}
	}

	@Override
	public String getColumnName(int c) {
		return columnName[c];
	}
	
	@Override
	public int getColumnCount() {
		return columnName.length;
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int col) {
		//log.debug("getValue row = " + row + " col = " + col);
		Kunde k = kunden.get(row);
		switch (col) {
		case 0:
			return k.getId();
		case 1:
			return k.getName();
		default:
			return null;
		}
	}

}
