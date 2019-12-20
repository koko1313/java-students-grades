import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class Methods extends Declarations implements ActionListener {
	private static final long serialVersionUID = 1L;
		
	public void getDataFromAllFields(String tab) {
		if(tab=="studenti"){ // Полетата от Студенти
			studentiInputFn = studentiFnTField.getText().trim();
			studentiInputIme = studentiImeTField.getText().trim();
			studentiInputFamiliq = studentiFamiliqTField.getText().trim();
			studentiInputTel = studentiTelTField.getText().trim();
			studentiInputAdress = studentiAdressTField.getText().trim();
			studentiInputSpecString = studentiCombo.getSelectedItem().toString();
			studentiInputSpec=0;
			if(studentiInputSpecString!="") studentiInputSpec = Integer.parseInt(getData("id", "specialnosti where specialnost='" + studentiInputSpecString + "';")); // търсим специалността по низа и взимаме нейното ID
			studentiId = getData("id","studenti where fn='"+studentiEditCombo.getSelectedItem().toString().split(" ")[0]+"'");
		} else
		if(tab=="specialnosti"){ // Полетата от Специалности
			specialnostiInputSpec = specialnostiSpecTField.getText().trim();
			specialnostiInputInspString = specialnostiCombo.getSelectedItem().toString();
			specialnostiInputInsp=0;
			if (specialnostiInputInspString!="") specialnostiInputInsp = Integer.parseInt(getData("id", "inspektori where ime = '"+specialnostiInputInspString+"'"));
		} else
		if(tab=="predmeti"){ // Полетата от Предмети
			predmetiInputPredmet = predmetiPredmetTField.getText().trim();
			predmetiInputSpecString = predmetiSpecialnostiCombo.getSelectedItem().toString();
			predmetiInputSpec = 0;
			if(predmetiInputSpecString!="") predmetiInputSpec = Integer.parseInt(getData("id", "specialnosti where specialnost = '"+predmetiInputSpecString+"'"));
		} else
		if(tab=="inspektori"){ // Полетата от Инспектори
			inspektoriInputIme = inspektoriImeTField.getText().trim();
		} else
		if(tab=="ocenki"){ // Полетата от Оценки
			studentId = 0;
			ocenkiPredmet = 0;
			ocenkiSpecialnost = 0;
			ocenkiOcenka = 0;
			String ocenkiFn = ocenkiFnCombo.getSelectedItem().toString();
			String ocenkiPredmetString = ocenkiPredmetCombo.getSelectedItem().toString();
			if(ocenkiOcenkaCombo.getSelectedItem().toString()!="") // ако е избрана оценка
				ocenkiOcenka = Integer.parseInt(ocenkiOcenkaCombo.getSelectedItem().toString().split(" ")[1]); // я всимаме и я кастваме в int
			if(ocenkiPredmetString!="" && ocenkiFn!="") 
				ocenkiPredmet = Integer.parseInt(getData("id","predmeti where predmet='"+ocenkiPredmetString+"' and specialnost_id="+Integer.parseInt(getData("specialnost_id","studenti where fn='"+ocenkiFn+"'"))));
			String ocenkiStudent[] = ocenkiFn.split(" "); // сплитваме, за да вземем
			ocenkiFn = ocenkiStudent[0]; // за да вземем нулевия елемент (фак. номер)
			if(ocenkiFn!="") studentId = Integer.parseInt(getData("id", "studenti where fn='"+ocenkiFn+"'"));
			if(!ocenkiFn.equals(""))
				ocenkiSpecialnost = Integer.parseInt(getData("specialnost_id", "predmeti where id=" + Integer.parseInt(getData("id","predmeti where predmet='Математика' and specialnost_id="+Integer.parseInt(getData("specialnost_id","studenti where fn='"+ocenkiFn+"'"))))));
		}
	}
	
	public boolean isInputDataCorrect(String tab, boolean showAlert){
		String invalid = "";
		if(tab.equals("studenti")){
			if(needToGetDataFromFields) getDataFromAllFields("studenti");
			if(studentiInputFn.length()>0 && 
			studentiInputTel.length()>0 && 
			studentiInputIme.length()>0 && 
			studentiInputFamiliq.length()>0 &&
			studentiInputSpec!=0) 
			{
				if(studentiInputFn.length() != 10 || !studentiInputFn.matches("^[0-9]+")) invalid += "Невалиден факултетен номер!\n";
				if(!studentiInputIme.matches("[а-яА-Я]+") || studentiInputIme.length()>25) invalid += "Невалидно име!\n";
				if(!studentiInputFamiliq.matches("[а-яА-Я]+") || studentiInputFamiliq.length()>25) invalid += "Невалидна фамилия!\n";
				if(studentiInputAdress.length()>50) invalid += "Адреса е пракалено дълъг!\n";
				if(studentiInputTel.length() != 10 || !studentiInputTel.matches("^[0-9]+")) invalid += "Невалиден телефонен номер!\n";
			} else invalid += "Не всички полета са попълнени!\n";
		} else
		if(tab.equals("specialnosti")){
			if(needToGetDataFromFields) getDataFromAllFields("specialnosti");
			if(specialnostiInputSpec.length()>0 &&
			specialnostiInputInsp!=0)
			{
				if(!specialnostiInputSpec.matches("[а-яА-Я ()-]+") || specialnostiInputSpec.length()>50) invalid += "Невалидно наименование на специалност (само букви на кирилица, скоби и тире)!\n";
			} else invalid += "Не всички полета са попълнени!\n";
		} else
		if(tab.equals("predmeti")){
			if(needToGetDataFromFields) getDataFromAllFields("predmeti");
			if(predmetiInputPredmet.length()>0 &&
			predmetiInputSpec!=0) {
				if(predmetiInputPredmet.length()>50) invalid += "Наименованието на предмета е прекалено дълго!";
			}
			else invalid += "Не всички полета са попълнени!\n";
		} else
		if(tab.equals("inspektori")){
			if(needToGetDataFromFields) getDataFromAllFields("inspektori");
			if(inspektoriInputIme.length()>0){
				if(!inspektoriInputIme.matches("[а-яА-Я ]+") || inspektoriInputIme.length()>50) invalid += "Невалидно име на иснпектор!\n";
			} else invalid += "Не всички полета са попълнени!\n";
		} else
		if(tab.equals("ocenki")){
			if(needToGetDataFromFields) getDataFromAllFields("ocenki");
			if(studentId==0 || ocenkiPredmet==0 || ocenkiOcenka==0) invalid += "Не всички полета са попълнени!\n";
		}
		
		if(invalid!="") {
			if(showAlert) JOptionPane.showMessageDialog(null, invalid, "Невалидни стойности", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}
	
	public void fillCombo(JComboBox<String> combo, String columns, String table){
		String sql = "select "+ columns +" from " + table;
		
		String[] column = columns.split(","); // ако искаме резултат от повече от една колона - изреждаме ги със запетая, а след това ги сплитваме	
		
		combo.removeAllItems();
		if(combo==ocenkiSpecialnostCombo || combo==studentiSpecialnostiFilter) combo.addItem("Всички"); else
		combo.addItem("");
		combo.setSelectedIndex(0);

		try {
			conn = DBConnector.getConnected();
			state = conn.prepareStatement(sql);			
			result = state.executeQuery();
			
			while(result.next()){
				String allColumnsQuery="";
				for(int i=0; i<column.length; i++){
					
					if(combo==predmetiEditCombo){
						if(i==0) {
							allColumnsQuery += result.getString(column[i]) + " ";
							allColumnsQuery += "[" + getData("specialnost", "specialnosti where id="+result.getString(column[1])) + "] ";
						}
					} else
					allColumnsQuery += result.getString(column[i]) + " ";
				}
				allColumnsQuery = allColumnsQuery.substring(0, allColumnsQuery.length()-1); // премахване интервала накрая
				
				// понеже при оценките - предметите могат да се повтарят - добавяме ги само по веднъж
				if(combo == ocenkiPredmetCombo){
					combo.setSelectedItem(allColumnsQuery);
					if(combo.getSelectedIndex()==0){ 
						combo.addItem(allColumnsQuery); 
					}
					combo.setSelectedIndex(0);
				} else combo.addItem(allColumnsQuery); // иначе
			}
		} catch (SQLException e1) {e1.printStackTrace();}
		finally {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}	
	}
	
	public void fillAllCombos(){
		ocenkiSpecialnostCombo.removeActionListener(this);
		ocenkiFnCombo.removeActionListener(this); // премахваме ActionListener-a защото друго комбо трябва да се пълни спрямо това и гърми
		studentiSpecialnostiFilter.removeActionListener(this);
		predmetiSpecialnostiFilter.removeActionListener(this);
		
		fillCombo(studentiCombo, "specialnost", "specialnosti");
		fillCombo(studentiSpecialnostiFilter, "specialnost", "specialnosti");
		fillCombo(studentiEditCombo, "fn,ime,familiq", "studenti");
		fillCombo(specialnostiCombo, "ime", "inspektori");
		fillCombo(specialnostiEditCombo, "specialnost", "specialnosti");
		fillCombo(predmetiSpecialnostiCombo, "specialnost", "specialnosti");
		fillCombo(predmetiSpecialnostiFilter, "specialnost", "specialnosti");
		fillCombo(predmetiEditCombo, "predmet,specialnost_id", "predmeti");
		fillCombo(inspektoriEditCombo, "ime", "inspektori");
		fillCombo(ocenkiSpecialnostCombo, "specialnost", "specialnosti");	
		fillCombo(ocenkiFnCombo, "fn,ime,familiq", "studenti");
		fillCombo(ocenkiPredmetCombo, "predmet", "predmeti");
		ocenkiOcenkaCombo.setSelectedIndex(0); // "нулираме" и комбото на оценките (няма смисъл всеки път да го пълним)
		
		ocenkiSpecialnostCombo.addActionListener(this);
		ocenkiFnCombo.addActionListener(this);
		studentiSpecialnostiFilter.addActionListener(this);
		predmetiSpecialnostiFilter.addActionListener(this);
	}
	
	public void getData(String column, String table, JTable resultTableName){
		conn = DBConnector.getConnected();
		String sql = "select " + column + " from " + table;
		try {
			state = conn.prepareStatement(sql);		
			result = state.executeQuery();
			model = new MyModel(result);
			resultTableName.setModel(model);
		} catch (SQLException e1) {e1.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
		finally {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
	}

	public String getData(String column, String table){
		String sql = "select " + column + " from " + table;
		try {
			// Локални променливи, защото ако извикваме от друг метод, където ги има същите се презаписват :/
			PreparedStatement state = conn.prepareStatement(sql);
			ResultSet result = state.executeQuery();
			model = new MyModel(result);
			return MyModel.resultString;
		} catch (SQLException e1) {e1.printStackTrace();} catch (Exception e) {e.printStackTrace();}
		return "";
	}

	public void getAllData(){
		getData("a.fn, a.ime, a.familiq, a.tel, a.address, b.specialnost", "studenti a join specialnosti b on a.specialnost_id = b.id", studentiTable);
		getData("a.specialnost, b.ime", "specialnosti a join inspektori b on a.inspektor_id = b.id", specialnostiTable);
		getData("a.predmet, b.specialnost", "predmeti a join specialnosti b on a.specialnost_id = b.id", predmetiTable);
		getData("ime", "inspektori", inspektoriTable);
		getData("b.fn, b.ime,b.familiq,c.predmet,a.ocenka", "ocenki a join studenti b on a.student_id = b.id join predmeti c on a.predmet_id = c.id", ocenkiTable);
	}
	
	public void exportData(String table){
		fc.setFileFilter(filter);
		int returnVal = fc.showSaveDialog(fc);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile(); // Ако е избран файл го взимаме
		} else return; // Иначе излизаме от метода
	   
		if(table.equals("inspektori")) getData("ime", "inspektori", inspektoriTable); else
		if(table.equals("specialnosti")) getData("a.specialnost, b.ime", "specialnosti a join inspektori b on a.inspektor_id = b.id"); else
		if(table.equals("predmeti")) getData("a.predmet, b.specialnost", "predmeti a join specialnosti b on a.specialnost_id = b.id"); else
		if(table.equals("studenti")) getData("a.fn, a.ime, a.familiq, a.tel, a.address, b.specialnost", "studenti a join specialnosti b on a.specialnost_id = b.id");
		if(table.equals("ocenki")) getData("b.fn, b.ime,b.familiq,c.predmet,a.ocenka", "ocenki a join studenti b on a.student_id = b.id join predmeti c on a.predmet_id = c.id");
		boolean isThereExeptions = false;
		
		try{
			PrintWriter writer = new PrintWriter(file.getAbsolutePath(), StandardCharsets.UTF_8);
			for(int i = 0; i<model.getRowCount(); i++){
			    for(int j=0; j<model.getColumnCount(); j++){
					writer.print(model.getValueAt(i, j));
					if(table.equals("ocenki") && j<=2) {
						writer.print(" " + model.getValueAt(i, ++j));
						writer.print(" " + model.getValueAt(i, ++j));
					}
					if(j+1!=model.getColumnCount()) writer.print(", ");
			    }
			    writer.println();
			}
			writer.close();
		} catch (IOException e) {
			isThereExeptions = true;
			e.printStackTrace();
		}
		
		if(!isThereExeptions) JOptionPane.showMessageDialog(null, "Експортирането беше завършено успешно!", "Експорт", JOptionPane.WARNING_MESSAGE);
	}
	
	public void importData(String table){
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(fc);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) file = fc.getSelectedFile(); // Ако е избран файл го взимаме
		else return; // Иначе излизаме от метода
				
		boolean isThereExeptions = false; // флаг
		needToCheck = false; // да не извежда съобщения, когато вече съществуват такива записи
		String lineFromFile;
		try(
			InputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);
		) {
			while ((lineFromFile = br.readLine()) != null){
				conn = DBConnector.getConnected();

				String[] splitLineFromFile = lineFromFile.split(",");
				
				if(table.equals("inspektori")){
					inspektoriInputIme = lineFromFile;
				} else 
				if(table.equals("specialnosti")){
					specialnostiInputSpec = splitLineFromFile[0].trim();
					specialnostiInputInsp=0;
					try{
						specialnostiInputInsp = Integer.parseInt(getData("id","inspektori where ime='"+splitLineFromFile[1].trim()+"'")); // ако е намерен такъв инспектори
					} catch (NumberFormatException e) {}
				} else
				if(table.equals("predmeti")){	
					predmetiInputPredmet = splitLineFromFile[0].trim();
					predmetiInputSpec=0;
					try{
						predmetiInputSpec = Integer.parseInt(getData("id","specialnosti where specialnost='"+splitLineFromFile[1].trim()+"'")); // ако е намерен такъв инспектори
					} catch (NumberFormatException e) {}
				} else
				if(table.equals("studenti")){
					studentiInputFn = splitLineFromFile[0].trim();
					studentiInputIme = splitLineFromFile[1].trim();
					studentiInputFamiliq = splitLineFromFile[2].trim();
					studentiInputTel = splitLineFromFile[3].trim();
					studentiInputAdress = splitLineFromFile[4].trim();
					studentiInputSpec = 0;
					try{
						studentiInputSpec = Integer.parseInt(getData("id","specialnosti where specialnost='"+splitLineFromFile[5].trim()+"'")); // ако е намерен такъв инспектори
					} catch (NumberFormatException e) {}
				} else
				if(table.equals("ocenki")){
					String studentIdString = getData("id", "studenti where fn='"+splitLineFromFile[0].trim().split(" ")[0]+"'");
					studentId = 0;
					ocenkiPredmet = 0;
					ocenkiOcenka = 0;
					ocenkiSpecialnost = 0;
					try{
						studentId = Integer.parseInt(studentIdString);
						ocenkiPredmet = Integer.parseInt(getData("id","predmeti where predmet='"+splitLineFromFile[1].trim()+"' and specialnost_id="+Integer.parseInt(getData("specialnost_id","studenti where id='"+studentIdString+"'"))));
						ocenkiOcenka = Integer.parseInt(splitLineFromFile[2].trim());
						ocenkiSpecialnost = Integer.parseInt(getData("specialnost_id", "predmeti where id=" + Integer.parseInt(getData("id","predmeti where predmet='Математика' and specialnost_id="+Integer.parseInt(getData("specialnost_id","studenti where id='"+studentIdString+"'"))))));
					} catch (NumberFormatException e) {}
				}
				
				needToGetDataFromFields = false;
				if(isInputDataCorrect(table, false)) insertData(table);
				needToGetDataFromFields = true;
				
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
				
				fillAllCombos();
				getAllData();
			}
		} 
		catch (FileNotFoundException e) {
			isThereExeptions = true;
			JOptionPane.showMessageDialog(null, "Файлът не е намерен!", "Файлът не е намерен", JOptionPane.WARNING_MESSAGE);
		}
		catch (ArrayIndexOutOfBoundsException e){
			isThereExeptions = true;
			JOptionPane.showMessageDialog(null, "Избраният файл не е в нужния формат!", "Грешен формат", JOptionPane.WARNING_MESSAGE);
		}
		catch (IOException e) {
			isThereExeptions = true;
			e.printStackTrace();
		}
		
		if(!isThereExeptions) JOptionPane.showMessageDialog(null, "Импортирането беше завършено успешно!", "Импорт", JOptionPane.WARNING_MESSAGE);
		else JOptionPane.showMessageDialog(null, "Неуспешно импортиране!", "Импорт", JOptionPane.WARNING_MESSAGE);
		needToCheck = true; // връщаме си проверката за съществуващи записи
	}
	
	public void insertData(String table){
		if(needToGetDataFromFields) getDataFromAllFields(table);
		
		if(!isInputDataCorrect(table, true)) return;
		
		String values="";
		switch (table) { // зависи от таблицата имаме различни стойности, които ще подаваме
			case "studenti" : 		values = "null,?,?,?,?,?,?"; 	break;
			case "specialnosti" : 	values = "null,?,?"; 			break;
			case "predmeti" : 		values = "null,?,?";			break;
			case "inspektori" : 	values = "null,?"; 				break;
			case "ocenki" : 		values = "null,?,?,?,?";		break;
		}
		
		String sql = "insert into " + table + " values (" + values + ");";
		Boolean isThereExeption; // флаг, ще проверяваме дали има повтарящи се
		try {
			state = conn.prepareStatement(sql);
			if(table == "studenti"){
				state.setString(1, studentiInputFn);
				state.setString(2, studentiInputIme);
				state.setString(3, studentiInputFamiliq);
				state.setString(4, studentiInputTel);
				state.setString(5, studentiInputAdress);
				state.setInt(6, studentiInputSpec);
			} else
			if(table == "specialnosti"){
				state.setString(1, specialnostiInputSpec);
				state.setInt(2, specialnostiInputInsp);
			} else
			if(table == "predmeti"){
				state.setString(1, predmetiInputPredmet);
				state.setInt(2, predmetiInputSpec);
			} else
			if(table == "inspektori"){
				state.setString(1, inspektoriInputIme);
			} else
			if(table == "ocenki"){
				state.setInt(1, studentId);
				state.setInt(2, ocenkiPredmet);
				state.setInt(3, ocenkiSpecialnost);
				if (ocenkiOcenka!=0) state.setInt(4, ocenkiOcenka);
			}
			isThereExeption = false;
			state.execute();
			
		} catch (SQLException e) {
			isThereExeption = true;
			if(e.getErrorCode()==23505 && needToCheck){ // Ако се въведе нещо повтарящо (ФН, тел и т.н.)
				int openBracket = e.getLocalizedMessage().indexOf("(");
				int closeBracket = e.getLocalizedMessage().indexOf(")");
				String repeatColumn = e.getLocalizedMessage().substring(openBracket+1, closeBracket);
				switch(repeatColumn) {
					case "FN" : JOptionPane.showMessageDialog(null, "Вече съществува студент с такъв факултетен номер!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "TEL" : JOptionPane.showMessageDialog(null, "Този телефонен номер е вече въведен на друг студент!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "SPECIALNOST" : JOptionPane.showMessageDialog(null, "Вече съществува такава специалност!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "SPECIALNOST_ID, PREDMET" : JOptionPane.showMessageDialog(null, "Вече съществува такъв предмет!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "IME" : JOptionPane.showMessageDialog(null, "Вече съществува такъв инспектор!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "STUDENT_ID, PREDMET_ID" : JOptionPane.showMessageDialog(null, "Вече е въведена оценка за този предмет този студент!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
				}
			}
		}
		
		if(!isThereExeption){ // ако не се повтаря нищо - "обновяваме", за да не се изпразват полетата когато се повтарят :D
			fillAllCombos();
			getAllData();
		}
	}
	
	public void deleteData(String table){
		String studentiFn="";
		if(studentiEditCombo.getItemCount()>0) studentiFn = studentiEditCombo.getSelectedItem().toString().split(" ")[0];
		String specialnostiSpec = specialnostiEditCombo.getSelectedItem().toString();
		
		String predmetiPredmet = predmetiEditCombo.getSelectedItem().toString().split(" \\[")[0];
		String specialnostString = "";
		int specialnostId = 0;
		if(predmetiEditCombo.getSelectedIndex()!=0){
			specialnostString = predmetiEditCombo.getSelectedItem().toString().split(" \\[")[1];
			specialnostString = specialnostString.substring(0, specialnostString.length()-1); // на края е останала скоба - махаме я
			specialnostId = Integer.parseInt(getData("id", "specialnosti where specialnost='"+specialnostString+"'"));
		}
		
		String inspektoriInspektor = inspektoriEditCombo.getSelectedItem().toString();
		
		// За оценките
			String ocenkiStudentFn = ocenkiFnCombo.getSelectedItem().toString();
			String[] ocenkiFn = ocenkiStudentFn.split(" ");
			ocenkiStudentFn = ocenkiFn[0];
			String ocenkiPredmet = ocenkiPredmetCombo.getSelectedItem().toString();
			int studentId = 0;
			int predmetId = 0;
			
		String where="";
		switch(table) {
			case "studenti" : where = "fn"; break;
			case "specialnosti" : where = "specialnost"; break;
			case "predmeti" : where = "predmet=? and specialnost_id"; break;
			case "inspektori" : where = "ime"; break;
			case "ocenki" : 
				if(ocenkiStudentFn!="") studentId = Integer.parseInt(getData("id","studenti where fn='"+ocenkiStudentFn+"'"));
				if(ocenkiPredmet!="" && studentId!=0) predmetId = Integer.parseInt(getData("id", "predmeti where predmet='"+ocenkiPredmet+"' and specialnost_id="+getData("specialnost_id","studenti where id='"+studentId+"'")));
				
				where += "student_id=? and ";
				if(!deleteAllCheckBox.isSelected()) where += "predmet_id=? and ";

				if(where.length()>6) where = where.substring(0, where.length()-7); // махаме последното "=? and ";
				if(where=="") where="id";
				break;
		}
		
		String sql = "";
		sql= "delete from " + table + " where " + where + "=?";
		try {
			// Диалог box-а
			Object[] options = {"Изтриване","Отказ"};
			String message = "Сигурни ли сте, че искате да изтриете данните? \nПри изтриване на тези данни е възможно да се изтрие и нещо от друга таблица.";
			int confirmDelete = JOptionPane.showOptionDialog(null,message,"Изтриване",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
			if(confirmDelete == 0){
				state = conn.prepareStatement(sql);
				switch(table) {
					case "studenti" : state.setString(1, studentiFn); break;
					case "specialnosti" : state.setString(1, specialnostiSpec); break;
					case "predmeti" : 
						state.setString(1, predmetiPredmet);
						state.setInt(2, specialnostId);
						break;
					case "inspektori" : state.setString(1, inspektoriInspektor); break;
					case "ocenki" : 
						state.setInt(1, studentId);
						if(!deleteAllCheckBox.isSelected()) state.setInt(2, predmetId);
						deleteAllCheckBox.setSelected(false);
						break;
				}
				state.execute(); // ако е подтвърдено изтриването
			}
			
			getAllData();
			fillAllCombos();
		} catch (SQLException e) {e.printStackTrace();}
	}
		
	public void editData(String table){
		if(!isInputDataCorrect(table, true)) return;
		
		getDataFromAllFields(table);
		// Полетата на Оценки
			String ocenkiInputFn = ocenkiFnCombo.getSelectedItem().toString();
				String ocenkiInputStudent[] = ocenkiInputFn.split(" ");
				ocenkiInputFn = ocenkiInputStudent[0]; // взимаме само ФН
			String ocenkiPredmetString = ocenkiPredmetCombo.getSelectedItem().toString();
			int ocenkiInputOcenka = 0;
			if(ocenkiOcenkaCombo.getSelectedIndex()!=0) ocenkiInputOcenka = Integer.parseInt(ocenkiOcenkaCombo.getSelectedItem().toString().split(" ")[1]);
			
			String ocenkiStudentId = "";
				if(ocenkiInputFn!="") ocenkiStudentId = getData("id", "studenti where fn='"+ocenkiInputFn+"'"); // спрямо въведения ФН взимаме ИД на студента
			
			String ocenkiPredmetId = "";
				if(ocenkiPredmetString!="") ocenkiPredmetId = getData("id", "predmeti where predmet='"+ocenkiPredmetString+"' and specialnost_id="+getData("specialnost_id","studenti where fn='"+ocenkiInputFn+"'")); // спрямо Името на предмети взимаме неговото ИД
				
			if(ocenkiStudentId!="" && ocenkiPredmetId!="") ocenkiStudentId = getData("id", "ocenki where (student_id="+ocenkiStudentId+"and predmet_id="+ocenkiPredmetId+")"); // спрямо ИД на стуента и ИД на предмета търсим ИД на оценката
			
		// ComboBox-a от предмети
			String predmetiEditSpecialnostString = "";
			int predmetiEditSelectedSpecialnostId = 0;
			if(predmetiEditCombo.getSelectedIndex()!=0){
				predmetiEditSpecialnostString = predmetiEditCombo.getSelectedItem().toString().split(" \\[")[1];
				predmetiEditSpecialnostString = predmetiEditSpecialnostString.substring(0, predmetiEditSpecialnostString.length()-1); // на края е останала скоба - махаме я
				predmetiEditSelectedSpecialnostId = Integer.parseInt(getData("id", "specialnosti where specialnost='"+predmetiEditSpecialnostString+"'"));
			}
			
		String sql="";
		String set="";
		String what="";
		String to="";
		String where="";		
		
		if(table == "studenti") {
			if (!studentiInputFn.equals("")) { 
				what += "fn,";
				to += "'"+studentiInputFn+"',";
			}
			if (!studentiInputIme.equals("")) { 
				what += "ime,";
				to += "'"+studentiInputIme+"',";
			}
			if(!studentiInputFamiliq.equals("")) { 
				what += "familiq,";
				to += "'"+studentiInputFamiliq+"',";
			}
			if(!studentiInputTel.equals("")) { 
				what += "tel,";
				to += "'"+studentiInputTel+"',";
			}
			// няма проверка за адреса, защото той може да е и ""
				what += "address,";
				to += "'"+studentiInputAdress+"',";
			if(studentiInputSpec!=0) { 
				what += "specialnost_id,";
				to += "'"+studentiInputSpec+"',";
			}
			where = "id";
		} else
		if(table == "specialnosti") {
			if (!specialnostiInputSpec.equals("")) { 
				what += "specialnost,";
				to += "'"+specialnostiInputSpec+"',";
			}
			if(specialnostiInputInsp!=0) { 
				what += "inspektor_id,";
				to += "'"+specialnostiInputInsp+"',";
			}
			where = "specialnost";
		} else
		if(table == "predmeti") {
			if (!predmetiInputPredmet.equals("")){
				what += "predmet,";
				to += "'"+predmetiInputPredmet+"',";
			}
			if (predmetiInputSpec!=0){
				what += "specialnost_id,";
				to += "'"+predmetiInputSpec+"',";
			}
			where = "predmet=? and specialnost_id";
		} else
		if(table.equals("inspektori")) {
			what += "ime,";
			to += "'"+inspektoriInputIme+"',";
			where = "ime";
		} else
		if(table == "ocenki") {
			if(!ocenkiInputFn.equals("")){
				what += "ocenka,";
				to += "'"+ocenkiInputOcenka+"',";
			}
			where = "id";
		}
		
		// премахваме последните запетаи
		if (what.length()>0) what = what.substring(0, what.length()-1);
		if (to.length()>0) to = to.substring(0, to.length()-1);
		
		set = "("+what+")=("+to+")";
		
		sql = "update "+table+" set "+set+" where "+where+"=?;";
		
		Boolean isThereExeption; // флаг, ще проверяваме дали има повтарящи се
		try {
			state = conn.prepareStatement(sql);
			switch(table){
				case "studenti" : state.setString(1, studentiId); break;
				case "specialnosti" : state.setString(1, specialnostiEditCombo.getSelectedItem().toString()); break;
				case "predmeti" : 
					state.setString(1, predmetiEditCombo.getSelectedItem().toString().split(" \\[")[0]);
					state.setInt(2, predmetiEditSelectedSpecialnostId);
					break;
				case "inspektori" : state.setString(1, inspektoriEditCombo.getSelectedItem().toString()); break;
				case "ocenki" : state.setString(1, ocenkiStudentId); break;
			}
			
		state.execute();
		isThereExeption = false;
		state.execute();
			
		} catch (SQLException e) {
			isThereExeption = true;
			if(e.getErrorCode()==23505){ // Ако се въведе нещо повтарящо (ФН, тел и т.н.)
				int openBracket = e.getLocalizedMessage().indexOf("(");
				int closeBracket = e.getLocalizedMessage().indexOf(")");
				String repeatColumn = e.getLocalizedMessage().substring(openBracket+1, closeBracket);
				switch(repeatColumn) {
					case "FN" : JOptionPane.showMessageDialog(null, "Вече съществува студент с такъв факултетен номер!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "TEL" : JOptionPane.showMessageDialog(null, "Този телефонен номер е вече въведен на друг студент!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "SPECIALNOST" : JOptionPane.showMessageDialog(null, "Вече съществува такава специалност!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "SPECIALNOST_ID, PREDMET" : JOptionPane.showMessageDialog(null, "Вече съществува такъв предмет!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "IME" : JOptionPane.showMessageDialog(null, "Вече съществува такъв инспектор!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
					case "STUDENT_ID, PREDMET_ID" : JOptionPane.showMessageDialog(null, "Вече е въведена оценка за този предмет този студент!", "Вече съществуващи данни", JOptionPane.WARNING_MESSAGE); break;
				}
			}
		}
		
		if(!isThereExeption){ // ако не се повтаря нищо - "обновяваме", за да не се изпразват полетата когато се повтарят :D
			fillAllCombos();
			getAllData();
		}
	}
		
	public void searchData(String searchIn){
		getDataFromAllFields(searchIn);
		// Полетата на Оценки
			String ocenkiInputFn = ocenkiFnCombo.getSelectedItem().toString();
				String ocenkiInputStudent[] = ocenkiInputFn.split(" ");
				ocenkiInputFn = ocenkiInputStudent[0]; // Взимаме само ФН
			String ocenkiPredmetString = ocenkiPredmetCombo.getSelectedItem().toString();
			int ocenkiInputOcenka = 0;
			if(ocenkiOcenkaCombo.getSelectedIndex()!=0) ocenkiInputOcenka = Integer.parseInt(ocenkiOcenkaCombo.getSelectedItem().toString().split(" ")[1]);
			
			String ocenkiStudentId = getData("id", "studenti where fn='"+ocenkiInputFn+"'"); // спрямо въведения ФН взимаме ИД на студента
			
			String ocenkiInputSpecialnostString = "";
			if(ocenkiSpecialnostCombo.getSelectedIndex()!=0) ocenkiInputSpecialnostString = ocenkiSpecialnostCombo.getSelectedItem().toString();
			int ocenkiInputSpecialnost = 0;
			if(ocenkiInputSpecialnostString!="") ocenkiInputSpecialnost = Integer.parseInt(getData("id", "specialnosti where specialnost='"+ocenkiInputSpecialnostString+"'"));

		String where="";
		
		if (searchIn == "studenti") {
			if (!studentiInputFn.equals("")) { where += "fn='"+studentiInputFn+"' and "; }
			if (!studentiInputIme.equals("")) { where += "LOWER(IME) LIKE LOWER('"+studentiInputIme+"') and "; }
			if (!studentiInputFamiliq.equals("")) { where += "LOWER(familiq) LIKE LOWER('"+studentiInputFamiliq+"') and "; }
			if (!studentiInputTel.equals("")) { where += "tel='"+studentiInputTel+"' and "; }
			if (!studentiInputAdress.equals("")) { where += "LOWER(address) LIKE LOWER('"+studentiInputAdress+"') and "; }
			if (studentiInputSpec!=0) { where += "specialnost_id ='"+studentiInputSpec+"' and "; }
		} else
		if (searchIn == "specialnosti") {
			if (!specialnostiInputSpec.equals("")) { where += "LOWER(specialnost) LIKE LOWER('"+specialnostiInputSpec+"') and "; }
			if (specialnostiInputInsp!=0) { where += "inspektor_id ='"+specialnostiInputInsp+"' and "; }
		} else
		if (searchIn == "predmeti") {
			if (!predmetiInputPredmet.equals("")) { where += "LOWER(predmet) LIKE LOWER('"+predmetiInputPredmet+"') and "; }
			if (predmetiInputSpec!=0) { where += "specialnost_id='"+predmetiInputSpec+"' and "; }
		} else
		if (searchIn == "inspektori") {
			if (!inspektoriInputIme.equals("")) { where += "LOWER(IME) LIKE LOWER('"+inspektoriInputIme+"') and "; }
		} else
		if (searchIn == "ocenki") {
			if (!ocenkiInputFn.equals("")) { where += "student_id='"+ocenkiStudentId+"' and "; }
			if (!ocenkiPredmetString.equals("")) { 
				where += "pr.predmet='"+ocenkiPredmetString+"' and "; 
			}
			if (ocenkiInputOcenka!=0) { where += "ocenka='"+ocenkiInputOcenka+"' and "; }
			if (ocenkiInputSpecialnost!=0) { where += "oc.specialnost_id='"+ocenkiInputSpecialnost+"' and "; }
		}

		if(where!="") { where = where.substring(0,where.length()-5); // изрязваме " and " от края
			if(searchIn == "studenti") getData("a.fn, a.ime, a.familiq, a.tel, a.address, b.specialnost", "studenti a join specialnosti b on a.specialnost_id = b.id where ("+where+")", studentiTable); else
			if(searchIn == "specialnosti") getData("a.specialnost,b.ime", "specialnosti a join inspektori b on a.inspektor_id = b.id where ("+where+")", specialnostiTable); else
			if(searchIn == "predmeti") getData("a.predmet, b.specialnost", "predmeti a join specialnosti b on a.specialnost_id = b.id where ("+where+")", predmetiTable); else
			if(searchIn == "inspektori") getData("ime", "inspektori where ("+where+")", inspektoriTable); else
			if(searchIn == "ocenki") getData("st.fn, st.ime,st.familiq,c.predmet,oc.ocenka", "ocenki oc join predmeti pr on oc.predmet_id=pr.id join studenti st on oc.student_id = st.id join predmeti c on oc.predmet_id = c.id where ("+where+")", ocenkiTable);
		} else {
			if(searchIn == "studenti") getData("a.fn, a.ime, a.familiq, a.tel, a.address, b.specialnost", "studenti a join specialnosti b on a.specialnost_id = b.id", studentiTable); else
			if(searchIn == "specialnosti") getData("a.specialnost,b.ime", "specialnosti a join inspektori b on a.inspektor_id = b.id", specialnostiTable); else
			if(searchIn == "predmeti") getData("a.predmet, b.specialnost", "predmeti a join specialnosti b on a.specialnost_id = b.id", predmetiTable); else
			if(searchIn == "inspektori") getData("ime", "inspektori", inspektoriTable); else
			if(searchIn == "ocenki") getData("b.fn, b.ime,b.familiq,c.predmet,a.ocenka", "ocenki a join studenti b on a.student_id = b.id join predmeti c on a.predmet_id = c.id", ocenkiTable);
		}
	}
	
	public void editComboAction(String combo){
		// Декларация за Студенти
			String studentiFn="";
			if(studentiEditCombo.getItemCount()>0) studentiFn = studentiEditCombo.getSelectedItem().toString().split(" ")[0];
			studentiFnTField.setText(studentiFn);
		
		// Декларация за Специалности
			String specialnostiSpec="";
			if(specialnostiEditCombo.getItemCount()>0) specialnostiSpec = specialnostiEditCombo.getSelectedItem().toString();
			specialnostiSpecTField.setText(specialnostiSpec);
			
		// Декларация за Предмети
			String predmetiPredmet="";
			if(predmetiEditCombo.getItemCount()>0) predmetiPredmet = predmetiEditCombo.getSelectedItem().toString().split(" \\[")[0]; // разделяме там, където е скобата
			predmetiPredmetTField.setText(predmetiPredmet);
			
		// Декларация за Инспектори
			String inspektoriIme="";
			if(inspektoriEditCombo.getItemCount()>0) inspektoriIme = inspektoriEditCombo.getSelectedItem().toString();
			inspektoriImeTField.setText(inspektoriIme);
		
		String sql = "select *";
		
		if(combo == "studenti"){
			if(studentiFn=="") {
				studentiImeTField.setText("");
				studentiFamiliqTField.setText("");
				studentiTelTField.setText("");
				studentiAdressTField.setText("");
				studentiSpecTField.setText("");
				studentiCombo.setSelectedIndex(0);
				studentiDelButton.setEnabled(false);
				studentiEditButton.setEnabled(false);
			}
			sql = "select * from studenti where fn='"+studentiFn+"';";
		} else 
		if(combo == "specialnosti"){
			if(specialnostiSpec=="") {
				specialnostiSpecTField.setText("");
				specialnostiCombo.setSelectedIndex(0);
				specialnostiDelButton.setEnabled(false);
				specialnostiEditButton.setEnabled(false);
			}
			sql = "select * from specialnosti where specialnost='"+specialnostiSpec+"';";
		} else
		if(combo == "predmeti"){
			if(predmetiPredmet==""){
				predmetiSpecialnostiCombo.setSelectedIndex(0);
				predmetiDelButton.setEnabled(false);
				predmetiEditButton.setEnabled(false);
			}
			sql = "select * from predmeti where predmet='"+predmetiPredmet+"'";
		} else
		if(combo == "inspektori"){
			if(inspektoriIme==""){
				inspektoriDelButton.setEnabled(false);
				inspektoriEditButton.setEnabled(false);
			} else {
				inspektoriDelButton.setEnabled(true);
				inspektoriEditButton.setEnabled(true);
			}
		}

		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			while(result.next()){
				if(combo == "studenti"){
					studentiImeTField.setText(result.getString("ime"));
					studentiFamiliqTField.setText(result.getString("familiq"));
					studentiTelTField.setText(result.getString("tel"));
					studentiAdressTField.setText(result.getString("address"));
					studentiCombo.setSelectedItem(getData("specialnost", "specialnosti where id='"+result.getString("specialnost_id")+"'"));
					studentiDelButton.setEnabled(true);
					studentiEditButton.setEnabled(true);
				} else
				if(combo == "specialnosti"){
					specialnostiCombo.setSelectedItem(getData("ime","inspektori where id='"+result.getString("inspektor_id")+"'"));
					specialnostiDelButton.setEnabled(true);
					specialnostiEditButton.setEnabled(true);
				} else
				
				if(combo == "predmeti"){
					String predmetiEditSpecialnostString = "";
					int predmetiEditSelectedSpecialnostId = 0;
					if(predmetiEditCombo.getSelectedIndex()!=0){
						predmetiEditSpecialnostString = predmetiEditCombo.getSelectedItem().toString().split(" \\[")[1];
						predmetiEditSpecialnostString = predmetiEditSpecialnostString.substring(0, predmetiEditSpecialnostString.length()-1); // на края е останала скоба - махаме я
						predmetiEditSelectedSpecialnostId = Integer.parseInt(getData("id", "specialnosti where specialnost='"+predmetiEditSpecialnostString+"'"));
						predmetiDelButton.setEnabled(true);
						predmetiEditButton.setEnabled(true);
					}
					predmetiSpecialnostiCombo.setSelectedItem(getData("specialnost", "specialnosti where id='"+predmetiEditSelectedSpecialnostId+"'"));
				}
			}
		} catch (SQLException e1) {e1.printStackTrace();}
	}
	
	public void fillComboDynamic(String changedCombo){
		if(changedCombo.equals("ocenkiFnCombo")){
			String ocenkiStudentFn = ocenkiFnCombo.getSelectedItem().toString();
			String[] ocenkiStudent = ocenkiStudentFn.split(" ");
			ocenkiStudentFn = ocenkiStudent[0];
			
			int specialnostId = 0;
			if(ocenkiStudentFn!="") {
				specialnostId = Integer.parseInt(getData("specialnost_id", "studenti where fn='"+ocenkiStudentFn+"'"));
				fillCombo(ocenkiPredmetCombo, "predmet", "predmeti where specialnost_id="+specialnostId);
			}
			else fillCombo(ocenkiPredmetCombo, "predmet", "predmeti");
		} else
			
		if(changedCombo.equals("ocenkiSpecialnostCombo")){
			ocenkiFnCombo.removeActionListener(this);
			String ocenkiSpecialnost = "";
			if(ocenkiSpecialnostCombo.getSelectedIndex()!=0) ocenkiSpecialnost = ocenkiSpecialnostCombo.getSelectedItem().toString();
			
			int specialnostId = 0;
			if(ocenkiSpecialnost!=""){
				specialnostId = Integer.parseInt(getData("id", "specialnosti where specialnost='"+ocenkiSpecialnost+"'"));
				fillCombo(ocenkiFnCombo, "fn,ime,familiq", "studenti where specialnost_id="+specialnostId);
				fillCombo(ocenkiPredmetCombo, "predmet", "predmeti where specialnost_id="+specialnostId);
			}
			else {
				fillCombo(ocenkiFnCombo, "fn,ime,familiq", "studenti");
				fillCombo(ocenkiPredmetCombo, "predmet", "predmeti");
			}
			ocenkiFnCombo.addActionListener(this);
		} else
		
		if(changedCombo.equals("studentiSpecialnostiFilter")){
			String studentiSpecialnost = "";
			if(studentiSpecialnostiFilter.getSelectedIndex()!=0) studentiSpecialnost = studentiSpecialnostiFilter.getSelectedItem().toString();
			
			int specialnostId = 0;
			if(studentiSpecialnost!="") {
				specialnostId = Integer.parseInt(getData("id", "specialnosti where specialnost='"+studentiSpecialnost+"'"));
				fillCombo(studentiEditCombo, "fn,ime,familiq", "studenti where specialnost_id="+specialnostId);
			}
			else fillCombo(studentiEditCombo, "fn,ime,familiq", "studenti");
		} else
			
		if(changedCombo.equals("predmetiSpecialnostiFilter")){
			String predmetiSpecialnost = predmetiSpecialnostiFilter.getSelectedItem().toString();
			int specialnostId = 0;
			if(predmetiSpecialnost!="") {
				specialnostId = Integer.parseInt(getData("id", "specialnosti where specialnost='"+predmetiSpecialnost+"'"));
				fillCombo(predmetiEditCombo, "predmet,specialnost_id", "predmeti where specialnost_id="+specialnostId);
			}
			else fillCombo(predmetiEditCombo, "predmet,specialnost_id", "predmeti");
		}
	}
	
	public void actionPerformed(ActionEvent pressedButton){
		conn = DBConnector.getConnected();
		
		if (pressedButton.getSource() == studentiAddButton)			insertData("studenti"); else 			// Сруденти - Добави
		if (pressedButton.getSource() == studentiDelButton) 		deleteData("studenti"); else 			// Студенти - Изтрий
		if (pressedButton.getSource() == studentiEditButton) 		editData("studenti"); else 				// Студенти - Редактирай
		if (pressedButton.getSource() == studentiSearchButton) 		searchData("studenti"); else			// Студенти - Търсене
		if (pressedButton.getSource() == studentiEditCombo) 		editComboAction("studenti"); else		// Студенти - EditCombo
		if (pressedButton.getSource() == specialnostiAddButton)		insertData("specialnosti"); else 		// Специалности - Добави
		if (pressedButton.getSource() == specialnostiDelButton) 	deleteData("specialnosti"); else 		// Специалности - Изтрий
		if (pressedButton.getSource() == specialnostiEditButton) 	editData("specialnosti"); else 			// Специалности - Редактирай
		if (pressedButton.getSource() == specialnostiSearchButton) 	searchData("specialnosti"); else		// Специалности - Търсене
		if (pressedButton.getSource() == specialnostiEditCombo)		editComboAction("specialnosti"); else 	// Специалности - EditCombo
		if (pressedButton.getSource() == predmetiAddButton)			insertData("predmeti"); else			// Предмети - Добави
		if (pressedButton.getSource() == predmetiDelButton) 		deleteData("predmeti"); else			// Предмети - Изтрий
		if (pressedButton.getSource() == predmetiEditButton)		editData("predmeti"); else				// Предмети - Редактирай
		if (pressedButton.getSource() == predmetiSearchButton)		searchData("predmeti"); else			// Предмети - Търсене
		if (pressedButton.getSource() == predmetiEditCombo)			editComboAction("predmeti"); else		// Предмери - EditCombo
		if (pressedButton.getSource() == inspektoriAddButton)		insertData("inspektori"); else 			// Инспектори - Добави
		if (pressedButton.getSource() == inspektoriDelButton) 		deleteData("inspektori"); else			// Инспектори - Изтрий
		if (pressedButton.getSource() == inspektoriEditButton)		editData("inspektori"); else			// Инспектори - Редактирай
		if (pressedButton.getSource() == inspektoriSearchButton)	searchData("inspektori"); else			// Инспектори - Търсене
		if (pressedButton.getSource() == inspektoriEditCombo)		editComboAction("inspektori"); else		// Инспектори - EditCombo
		if (pressedButton.getSource() == ocenkiAddButton)			insertData("ocenki"); else				// Оценки - Добави
		if (pressedButton.getSource() == ocenkiDelButton) 			deleteData("ocenki"); else				// Оценки - Изтрий
		if (pressedButton.getSource() == ocenkiEditButton)			editData("ocenki"); else				// Оценки - Редактирай
		if (pressedButton.getSource() == ocenkiSearchButton)		searchData("ocenki"); else				// Оценки - Търсене
		if (pressedButton.getSource() == inspektoriExportButton)	exportData("inspektori"); else			// Инспектори - Експорт
		if (pressedButton.getSource() == inspektoriImportButton)	importData("inspektori"); else			// Инспектори - Импорт
		if (pressedButton.getSource() == specialnostiImportButton)	importData("specialnosti"); else		// Специалности - Импорт
		if (pressedButton.getSource() == specialnostiExportButton)	exportData("specialnosti"); else		// Специалности - Експорт			
		if (pressedButton.getSource() == predmetiImportButton)		importData("predmeti"); else			// Предмети - Импорт			
		if (pressedButton.getSource() == predmetiExportButton)		exportData("predmeti"); else			// Предмети - Експорт			
		if (pressedButton.getSource() == studentiImportButton)		importData("studenti"); else			// Студенти - Импорт			
		if (pressedButton.getSource() == studentiExportButton)		exportData("studenti"); else			// Студенти - Експорт				
		if (pressedButton.getSource() == ocenkiImportButton)		importData("ocenki"); else				// Оценки - Импорт		
		if (pressedButton.getSource() == ocenkiExportButton)		exportData("ocenki"); else				// Оценки - Експорт
			
		// Оценки - ФН комбото
		if (pressedButton.getSource() == ocenkiFnCombo) {
			fillComboDynamic("ocenkiFnCombo"); //Динамично полълване на предметите при смяна на ФН комбото	
		} else 
			
		// Оценки - Специалност комбото
		if (pressedButton.getSource() == ocenkiSpecialnostCombo) {
			fillComboDynamic("ocenkiSpecialnostCombo"); //Динамично попълване на другите комбота при смяна на Специалност комботи
		} else
		
		// Оценки - Предмет комбото
		if (pressedButton.getSource() == ocenkiPredmetCombo) {
			ocenkiOcenkaCombo.setSelectedIndex(0);
			if(ocenkiFnCombo.getSelectedIndex()!=0 && ocenkiPredmetCombo.getSelectedIndex()==0) deleteAllCheckBox.setEnabled(true);
			else {
				deleteAllCheckBox.setEnabled(false);
				deleteAllCheckBox.setSelected(false);
			}
			
			if(ocenkiFnCombo.getSelectedIndex()!=0 && ocenkiPredmetCombo.getSelectedIndex()!=0) ocenkiDelButton.setEnabled(true);
			else ocenkiDelButton.setEnabled(false);
		} else
		
		// Checkbox-a за изтриване на всички оценки на даден студент
		if (pressedButton.getSource() == deleteAllCheckBox){
			if(deleteAllCheckBox.isSelected()) ocenkiDelButton.setEnabled(true);
			else ocenkiDelButton.setEnabled(false);
		} else	
		
		// Комбото на студентите за специалността (филтъра)
		if (pressedButton.getSource() == studentiSpecialnostiFilter){
			fillComboDynamic("studentiSpecialnostiFilter");
		} else	
			
		// Комбото на предметите за специалността (филтъра)
		if (pressedButton.getSource() == predmetiSpecialnostiFilter){
			fillComboDynamic("predmetiSpecialnostiFilter");
		} else
			
		// Комбото на предметите за оценките
		if (pressedButton.getSource() == ocenkiOcenkaCombo){
			if(ocenkiFnCombo.getSelectedIndex()!=0 && ocenkiPredmetCombo.getSelectedIndex()!=0 && ocenkiOcenkaCombo.getSelectedIndex()!=0) ocenkiEditButton.setEnabled(true);
			else ocenkiEditButton.setEnabled(false);
		} else
			
			
			
		// Popup менюто - Редактиране
		if (pressedButton.getSource() == studentiEditItem) {
			selectedTable = studentiTable;
			int[] selectedRow = selectedTable.getSelectedRows();
			if(selectedRow.length!=0){
				selectedData = (String) selectedTable.getValueAt(selectedRow[0], 0) + " " + (String) selectedTable.getValueAt(selectedRow[0], 1) + " " + (String) selectedTable.getValueAt(selectedRow[0], 2);
				studentiEditCombo.setSelectedItem(selectedData);
			}
		} else
		if (pressedButton.getSource() == specialnostiEditItem) {
			selectedTable = specialnostiTable;
			int[] selectedRow = selectedTable.getSelectedRows();
			if(selectedRow.length!=0){
				selectedData = (String) selectedTable.getValueAt(selectedRow[0], 0);
				specialnostiEditCombo.setSelectedItem(selectedData);
			}
		} else
		if (pressedButton.getSource() == predmetiEditItem) {
			selectedTable = predmetiTable;
			int[] selectedRow = selectedTable.getSelectedRows();
			if(selectedRow.length!=0){
				selectedData = (String) selectedTable.getValueAt(selectedRow[0], 0) + " [" + selectedTable.getValueAt(selectedRow[0], 1) + "]";
				predmetiEditCombo.setSelectedItem(selectedData);
			}
		} else
		if (pressedButton.getSource() == inspektoriEditItem) {
			selectedTable = inspektoriTable;
			int[] selectedRow = selectedTable.getSelectedRows();
			if(selectedRow.length!=0){
				selectedData = (String) selectedTable.getValueAt(selectedRow[0], 0);
				inspektoriEditCombo.setSelectedItem(selectedData);
			}
		} else
		if (pressedButton.getSource() == ocenkiEditItem) {
			selectedTable = ocenkiTable;
			int[] selectedRow = selectedTable.getSelectedRows();
			if(selectedRow.length!=0){
				selectedData = (String) selectedTable.getValueAt(selectedRow[0], 0) + " " + (String) selectedTable.getValueAt(selectedRow[0], 1) + " " + (String) selectedTable.getValueAt(selectedRow[0], 2);
				ocenkiFnCombo.setSelectedItem(selectedData);
				selectedData = (String) selectedTable.getValueAt(selectedRow[0], 3);
				ocenkiPredmetCombo.setSelectedItem(selectedData);
				ocenkiOcenkaCombo.setSelectedIndex(((Byte)selectedTable.getValueAt(selectedRow[0], 4)).intValue() - 1);
			}
		} else
			
		
		// Popup менюто - Изтриване
		if (pressedButton.getSource() == studentiDeleteItem) {
			studentiEditItem.doClick();
			conn = DBConnector.getConnected();
			deleteData("studenti");
			try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		} else
		if (pressedButton.getSource() == specialnostiDeleteItem) {
			specialnostiEditItem.doClick();
			conn = DBConnector.getConnected();
			deleteData("specialnosti");
			try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		} else
		if (pressedButton.getSource() == predmetiDeleteItem) {
			predmetiEditItem.doClick();
			conn = DBConnector.getConnected();
			deleteData("predmeti");
			try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		} else
		if (pressedButton.getSource() == inspektoriDeleteItem) {
			inspektoriEditItem.doClick();
			conn = DBConnector.getConnected();
			deleteData("inspektori");
			try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		} else
		if (pressedButton.getSource() == ocenkiDeleteItem) {
			ocenkiEditItem.doClick();
			conn = DBConnector.getConnected();
			deleteData("ocenki");
			try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		}
		
		try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
	}
	
}
