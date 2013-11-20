package sesem2013.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import sesem2013.entities.Kunde;
import sesem2013.services.KundenService;
import sesem2013.services.KundenServiceImpl;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public class KundenPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private KundenService ks;
	
	private KundenTableModel ktm;
	
	private Kunde selectedKunde;
	
	private List<Kunde> kunden;
	
	private JLabel infoLabel;
	
	private Validator validator;

	private final JTextField NameTextField;

	public KundenPanel() {
		this.setLayout(null);

		JLabel NameLabel = new JLabel("Name");
		NameLabel.setBounds(12, 45, 70, 15);
		this.add(NameLabel);

		NameTextField = new JTextField();
		NameTextField.setBounds(75, 43, 114, 19);
		this.add(NameTextField);
		NameTextField.setColumns(10);
		
		infoLabel = new JLabel("");
		infoLabel.setBounds(12, 12, 434, 19);
		add(infoLabel);


		JButton btnSuchen = new JButton("Suchen");
		btnSuchen.setBounds(12, 200, 117, 25);
		btnSuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoLabel.setText("");
				
				Kunde k = new Kunde();
				k.setName(NameTextField.getText());
				
				kunden = ks.find(k);
				ktm.setKunden(kunden);
				
				if(kunden.size()==0){
					infoLabel.setText("Keine Kunden gefunden.");
				}else{
					infoLabel.setText(kunden.size()+" Kunde(n) gefunden.");

				}
			}
		});
		this.add(btnSuchen);

		JButton btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoLabel.setText("");
				
				//neuer kunde
				if(selectedKunde==null){

					Kunde k = new Kunde();
					k.setName(NameTextField.getText());
					
					if(isValid(k)){
						ks.create(k);
						infoLabel.setText("Kunde gespeichert");
					}
				}
				//kunde ändern
				else{
					Kunde k = selectedKunde;
					k.setName(NameTextField.getText());
					
					if(isValid(k)){
						ks.update(k);
						infoLabel.setText("Kunde geändert");
					}
					
				}
				
				
			}
		});
		btnSpeichern.setBounds(141, 200, 117, 25);
		this.add(btnSpeichern);

		JButton btnLoeschen = new JButton("Löschen");
		btnLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoLabel.setText("");

				if(selectedKunde==null){
					infoLabel.setText("Bitte Kunden auswählen");
				}else{
					ks.delete(selectedKunde);
					infoLabel.setText("Kunde gelöscht.");
					
					clear();
				}
			}
		});
		btnLoeschen.setBounds(270, 200, 117, 25);
		this.add(btnLoeschen);

		JButton felderloeschenButton = new JButton("Felder löschen");
		felderloeschenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		felderloeschenButton.setBounds(12, 165, 149, 25);
		this.add(felderloeschenButton);
		
		final JTable table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setShowGrid(false);
		table.setGridColor(Color.LIGHT_GRAY);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {				
					int row = table.getSelectedRow();
					if (row > -1) {
						selectKunde(kunden.get(row));
					}				
			}
		});
		
		ktm=new KundenTableModel();
		table.setModel(ktm);		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setPreferredSize(table.getPreferredSize());
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setBounds(0, 240, 651, 368);
		add(scrollPane);	

		
		ks = new KundenServiceImpl();
		
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	private void selectKunde(Kunde k){
		selectedKunde=k;		
		NameTextField.setText(k.getName());
	}	
	
	private void clear(){
		NameTextField.setText("");
		selectedKunde=null;
	}
	
	private boolean isValid(Kunde k){		
		Set<ConstraintViolation<Kunde>> constraintViolations = validator.validate(k);
		if(constraintViolations.size()>0){
			ConstraintViolation<Kunde> cv = constraintViolations.iterator().next();
			infoLabel.setText(cv.getPropertyPath().toString()+": "+cv.getMessage());
			return false;
		}
		return true;
	
	}
}
