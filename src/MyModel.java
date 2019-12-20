import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class MyModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private ResultSet result;	
	private int rowCount;	
	private int columnCount;
	private ArrayList<Object> data=new ArrayList<Object>();
	public static String resultString;
	 
	 public MyModel(ResultSet rs) throws Exception
	 {
		 setRS(rs);
	 }// end constructor
	 
	 public void setRS(ResultSet rs) throws Exception
	 {
		 this.result=rs;
		 ResultSetMetaData metaData=rs.getMetaData();
		 rowCount=0;
		 columnCount=metaData.getColumnCount();
		 while(rs.next()){
			 Object[] row=new Object[columnCount];
			 for(int j=0;j<columnCount;j++){
				 row[j]=rs.getObject(j+1);
			 }
			 resultString = row[0].toString();
			 data.add(row);
			 rowCount++;
		}// while
	 }// end setRS - filling ArrayList with ResultSet values
	 
	 public int getColumnCount(){
		 return columnCount;
	 }
	 
	 public int getRowCount(){
		 return rowCount;
	 }
	 
	 public Object getValueAt(int rowIndex, int columnIndex){
		 Object[] row=(Object[]) data.get(rowIndex);
		 return row[columnIndex];
	 }
	 
	 
	//###########################################################################################
	 String[] studentiColumnNames = {"Фак. №","Име","Фамилия","Телефон","Адрес"};
	 String[] specialnostiColumnNames = {"Специалност","Специалност","","","","Специалност"};
	 String[] predmetiColumnNames = {"Предмет","Предмет"};
	 String[] inspektoriColumnNames = {"Инспектор","Инспектор"};
	 String[] ocenkiColumnNames = {"Студент","","","Предмет","Оценка"};
	//###########################################################################################
	 
	 public String getColumnName(int columnIndex){
		 try{
		 ResultSetMetaData metaData=result.getMetaData();
		 //###########################################################################################
		 switch(metaData.getTableName(columnIndex+1)){
		 	case "STUDENTI" : if (columnIndex<studentiColumnNames.length) return studentiColumnNames[columnIndex];
		 	case "SPECIALNOSTI" : if (columnIndex<specialnostiColumnNames.length) return specialnostiColumnNames[columnIndex];
		 	case "PREDMETI" : if (columnIndex<predmetiColumnNames.length) return predmetiColumnNames[columnIndex];
		 	case "INSPEKTORI" : if (columnIndex<inspektoriColumnNames.length) return inspektoriColumnNames[columnIndex];
		 	case "OCENKI" : if (columnIndex<ocenkiColumnNames.length) return ocenkiColumnNames[columnIndex];
		 	default : return "";
		 }
		 //###########################################################################################
		 
		 // старото
		 //return metaData.getColumnName(columnIndex+1);
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
	 }// end getColumnName
} // end class MyModel 