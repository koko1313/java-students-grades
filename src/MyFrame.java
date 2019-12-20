import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import javax.swing.ListSelectionModel;

public class MyFrame extends Methods {
	private static final long serialVersionUID = 1L;
		
	public MyFrame(){
		this.setVisible(true);
		this.setSize(700, 800);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Табовете
		jtp.setBounds(new Rectangle(0, 0, 977, 977));
		jtp.add(studentiTab, "Студенти");
		jtp.add(specialnostiTab, "Специалности");
		jtp.add(predmetiTab, "Предмети");
		jtp.add(inspektoriTab, "Инспектори");
		jtp.add(ocenkiTab, "Оценки");
		this.add(jtp);
		
		// Studenti Tab #################################################
			//studentiTabTop ---------------------------------------
				studentiTabTop.setLayout(new GridLayout(3, 2));
				studentiTabTop.add(studentiFnLabel);
				studentiTabTop.add(studentiFnTField);
				studentiTabTop.add(studentiImeLabel);
				studentiTabTop.add(studentiImeTField);
				studentiTabTop.add(studentiFamiliqLabel);
				studentiTabTop.add(studentiFamiliqTField);
				studentiTabTop.add(studentiTelLabel);
				studentiTabTop.add(studentiTelTField);
				studentiTabTop.add(studentiAdressLabel);
				studentiTabTop.add(studentiAdressTField);
				studentiTabTop.add(studentiSpecLabel);
				studentiTabTop.add(studentiCombo);
			//studentiTabMid ---------------------------------------
				//studentiTabMid.setLayout(new GridLayout(3, 2));
				studentiTabMid.add(studentiAddButton);
				studentiTabMid.add(studentiSearchButton);
					studentiEditCombo.setPreferredSize(new Dimension(210,27));
				studentiTabMid.add(studentiSpecialnostiFilter);
					studentiSpecialnostiFilter.setPreferredSize(new Dimension(120, 27));
				studentiTabMid.add(studentiEditCombo);
				studentiTabMid.add(studentiDelButton);
				studentiTabMid.add(studentiEditButton);
				studentiTabMid.add(studentiImportButton);
				studentiTabMid.add(studentiExportButton);

				studentiAddButton.addActionListener(this);
				studentiSearchButton.addActionListener(this);
				studentiEditCombo.addActionListener(this);
				studentiDelButton.addActionListener(this);
				studentiEditButton.addActionListener(this);
				studentiSpecialnostiFilter.addActionListener(this);
				studentiImportButton.addActionListener(this);
				studentiExportButton.addActionListener(this);
				
				studentiDelButton.setEnabled(false);
				studentiEditButton.setEnabled(false);
				
				studentiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				studentiTable.getTableHeader().setReorderingAllowed(false);
				studentiEditItem.addActionListener(this);
				studentiDeleteItem.addActionListener(this);
				studentiPopupMenu.add(studentiEditItem);
				studentiPopupMenu.add(studentiDeleteItem);
				studentiTable.setComponentPopupMenu(studentiPopupMenu);
				
			studentiTab.setLayout(new GridLayout(3, 1));
			studentiTab.add(studentiTabTop);
			studentiTab.add(studentiTabMid);
			studentiTab.add(studentiScroller);
			
		// Specialnosti Tab #############################################
			//specialnostiTabTop -------------------------------
				specialnostiTabTop.setLayout(new GridLayout(2, 2));
				specialnostiTabTop.add(specialnostiSpecLabel);
				specialnostiTabTop.add(specialnostiSpecTField);
				specialnostiTabTop.add(specialnostiInspLabel);
				specialnostiTabTop.add(specialnostiCombo);
			//specialnostiTabMid -------------------------------
				specialnostiTabMid.add(specialnostiAddButton);
				specialnostiTabMid.add(specialnostiSearchButton);
					specialnostiEditCombo.setPreferredSize(new Dimension(230,27));
				specialnostiTabMid.add(specialnostiEditCombo);
				specialnostiTabMid.add(specialnostiDelButton);
				specialnostiTabMid.add(specialnostiEditButton);
				specialnostiTabMid.add(specialnostiImportButton);
				specialnostiTabMid.add(specialnostiExportButton);
				
				specialnostiAddButton.addActionListener(this);
				specialnostiSearchButton.addActionListener(this);
				specialnostiEditCombo.addActionListener(this);
				specialnostiDelButton.addActionListener(this);
				specialnostiEditButton.addActionListener(this);
				specialnostiImportButton.addActionListener(this);
				specialnostiExportButton.addActionListener(this);
				
				specialnostiDelButton.setEnabled(false);
				specialnostiEditButton.setEnabled(false);
				
				specialnostiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				specialnostiTable.getTableHeader().setReorderingAllowed(false);
				specialnostiEditItem.addActionListener(this);
				specialnostiDeleteItem.addActionListener(this);
				specialnostiPopupMenu.add(specialnostiEditItem);
				specialnostiPopupMenu.add(specialnostiDeleteItem);
				specialnostiTable.setComponentPopupMenu(specialnostiPopupMenu);
					
			specialnostiTab.setLayout(new GridLayout(3, 1));
			specialnostiTab.add(specialnostiTabTop);
			specialnostiTab.add(specialnostiTabMid);
			specialnostiTab.add(specialnostiScroller);
			
		// Predmeti Tab #################################################
			//predmetiTabTop -----------------------------------
				predmetiTabTop.setLayout(new GridLayout(2, 2));
				predmetiTabTop.add(predmetiPredmetLabel);
				predmetiTabTop.add(predmetiPredmetTField);
				predmetiTabTop.add(predmetiSpecLabel);
				predmetiTabTop.add(predmetiSpecialnostiCombo);
			//predmetiTabMid -----------------------------------
				predmetiTabMid.add(predmetiAddButton);
				predmetiTabMid.add(predmetiSearchButton);	

				predmetiEditCombo.setPreferredSize(new Dimension(210,27));
				predmetiSpecialnostiFilter.setPreferredSize(new Dimension(120, 27));
				
				predmetiTabMid.add(predmetiSpecialnostiFilter);
				predmetiTabMid.add(predmetiEditCombo);
					
				predmetiTabMid.add(predmetiDelButton);
				predmetiTabMid.add(predmetiEditButton);
				predmetiTabMid.add(predmetiImportButton);
				predmetiTabMid.add(predmetiExportButton);

				predmetiAddButton.addActionListener(this);
				predmetiSearchButton.addActionListener(this);
				predmetiEditCombo.addActionListener(this);
				predmetiDelButton.addActionListener(this);
				predmetiEditButton.addActionListener(this);
				predmetiSpecialnostiFilter.addActionListener(this);
				predmetiImportButton.addActionListener(this);
				predmetiExportButton.addActionListener(this);
				
				predmetiDelButton.setEnabled(false);
				predmetiEditButton.setEnabled(false);
				
				predmetiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				predmetiTable.getTableHeader().setReorderingAllowed(false);
				predmetiEditItem.addActionListener(this);
				predmetiDeleteItem.addActionListener(this);
				predmetiPopupMenu.add(predmetiEditItem);
				predmetiPopupMenu.add(predmetiDeleteItem);
				predmetiTable.setComponentPopupMenu(predmetiPopupMenu);
			
			predmetiTab.setLayout(new GridLayout(3, 1));
			predmetiTab.add(predmetiTabTop);
			predmetiTab.add(predmetiTabMid);
			predmetiTab.add(predmetiScroller);
		
		// Inspektori Tab ################################################
			//inspektoriTabTop ---------------------------------
				inspektoriTabTop.setLayout(new GridLayout(1, 2));
				inspektoriTabTop.add(inspektoriImeLabel);
				inspektoriTabTop.add(inspektoriImeTField);
			//inspektoriTabMid ---------------------------------
				inspektoriTabMid.add(inspektoriAddButton);
				inspektoriTabMid.add(inspektoriSearchButton);
					inspektoriEditCombo.setPreferredSize(new Dimension(230,27));
				inspektoriTabMid.add(inspektoriEditCombo);
				inspektoriTabMid.add(inspektoriDelButton);
				inspektoriTabMid.add(inspektoriEditButton);
				inspektoriTabMid.add(inspektoriImportButton);
				inspektoriTabMid.add(inspektoriExportButton);
				
				inspektoriAddButton.addActionListener(this);
				inspektoriSearchButton.addActionListener(this);
				inspektoriEditCombo.addActionListener(this);
				inspektoriDelButton.addActionListener(this);
				inspektoriEditButton.addActionListener(this);
				inspektoriImportButton.addActionListener(this);
				inspektoriExportButton.addActionListener(this);
				
				inspektoriDelButton.setEnabled(false);
				inspektoriEditButton.setEnabled(false);
				
				inspektoriTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				inspektoriTable.getTableHeader().setReorderingAllowed(false);
				inspektoriEditItem.addActionListener(this);
				inspektoriDeleteItem.addActionListener(this);
				inspektoriPopupMenu.add(inspektoriEditItem);
				inspektoriPopupMenu.add(inspektoriDeleteItem);
				inspektoriTable.setComponentPopupMenu(inspektoriPopupMenu);
				
			inspektoriTab.setLayout(new GridLayout(3, 1));
			inspektoriTab.add(inspektoriTabTop);
			inspektoriTab.add(inspektoriTabMid);
			inspektoriTab.add(inspektoriScroller);
		
		// Ocenki Tab ####################################################
			//ocenkiTabTop ---------------------------------
				ocenkiTabTop.setLayout(new GridLayout(2,3));
				ocenkiTabTopTop.add(ocenkiSpecialnostLabel);
				ocenkiTabTopTop.add(ocenkiSpecialnostCombo);
				ocenkiTabTopBot.add(ocenkiFnLabel);
				ocenkiTabTopBot.add(ocenkiPredmetLabel);
				ocenkiTabTopBot.add(ocenkiOcenkaLabel);
				ocenkiTabTopBot.add(ocenkiFnCombo);
				ocenkiTabTopBot.add(ocenkiPredmetCombo);
				ocenkiTabTopBot.add(ocenkiOcenkaCombo);
			//ocenkiTabMid ---------------------------------
				//ocenkiTabMid.setLayout(new GridLayout(2, 4));
				ocenkiTabMid.add(ocenkiAddButton);
				ocenkiTabMid.add(ocenkiSearchButton);				
				ocenkiTabMid.add(ocenkiDelButton);
				ocenkiTabMid.add(ocenkiEditButton);
				ocenkiTabMid.add(deleteAllCheckBox);
				ocenkiTabMid.add(ocenkiImportButton);
				ocenkiTabMid.add(ocenkiExportButton);
				
				deleteAllCheckBox.setEnabled(false);
				
				ocenkiAddButton.addActionListener(this);
				ocenkiSearchButton.addActionListener(this);
				ocenkiDelButton.addActionListener(this);
				ocenkiEditButton.addActionListener(this);
				ocenkiSpecialnostCombo.addActionListener(this);
				deleteAllCheckBox.addActionListener(this);
				ocenkiOcenkaCombo.addActionListener(this);
				ocenkiImportButton.addActionListener(this);
				ocenkiExportButton.addActionListener(this);
				
				ocenkiDelButton.setEnabled(false);
				ocenkiEditButton.setEnabled(false);
				ocenkiFnCombo.addActionListener(this);
				ocenkiPredmetCombo.addActionListener(this);
				
				ocenkiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				ocenkiTable.getTableHeader().setReorderingAllowed(false);
				ocenkiEditItem.addActionListener(this);
				ocenkiDeleteItem.addActionListener(this);
				ocenkiPopupMenu.add(ocenkiEditItem);
				ocenkiPopupMenu.add(ocenkiDeleteItem);
				ocenkiTable.setComponentPopupMenu(ocenkiPopupMenu);
					
			ocenkiTab.setLayout(new GridLayout(3, 2));
			ocenkiTabTopBot.setLayout(new GridLayout(2, 3));
			ocenkiTabTop.add(ocenkiTabTopTop);
			ocenkiTabTop.add(ocenkiTabTopBot);
			ocenkiTab.add(ocenkiTabTop);
			ocenkiTab.add(ocenkiTabMid);
			ocenkiTab.add(ocenkiScroller);
		
		getAllData();
		fillAllCombos();
	}
	
}
