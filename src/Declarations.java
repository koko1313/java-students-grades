import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Declarations extends JFrame {
	private static final long serialVersionUID = 1L;

	// Глобални променливи ###################################################################
	boolean needToCheck = true;
	boolean needToGetDataFromFields = true;
	JFileChooser fc = new JFileChooser(); // Browser-a
	File file = null; // Променлива за файл (импорт/експорт)
	FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text"); // Филтър при избирането на фаил за импорт
	
		// Полетата от Студенти
			String studentiInputFn;
			String studentiInputIme;
			String studentiInputFamiliq;
			String studentiInputTel;
			String studentiInputAdress;
			String studentiInputSpecString;
			String studentiId;
			int studentiInputSpec=0;
		// Полетата от Специалности
			String specialnostiInputSpec;
			String specialnostiInputInspString;
			int specialnostiInputInsp=0;
		// Полетата от Предмети
			String predmetiInputPredmet;
			String predmetiInputSpecString;
			int predmetiInputSpec=0;
		// Декларация за инспекторите
			String inspektoriInputIme;
		// Декларация за оценките
			int studentId;
			int ocenkiPredmet = 0;
			int ocenkiSpecialnost = 0;
			int ocenkiOcenka = 0;
			
	// Декларации на обектите ################################################################
	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	MyModel model = null;

	// Popup menu
		JTable selectedTable;
		String selectedData = "";
		
		JPopupMenu studentiPopupMenu = new JPopupMenu();
		JMenuItem studentiEditItem = new JMenuItem("Редактирай");
		JMenuItem studentiDeleteItem = new JMenuItem("Изтрий");
		
		JPopupMenu specialnostiPopupMenu = new JPopupMenu();
		JMenuItem specialnostiEditItem = new JMenuItem("Редактирай");
		JMenuItem specialnostiDeleteItem = new JMenuItem("Изтрий");
		
		JPopupMenu predmetiPopupMenu = new JPopupMenu();
		JMenuItem predmetiEditItem = new JMenuItem("Редактирай");
		JMenuItem predmetiDeleteItem = new JMenuItem("Изтрий");
		
		JPopupMenu inspektoriPopupMenu = new JPopupMenu();
		JMenuItem inspektoriEditItem = new JMenuItem("Редактирай");
		JMenuItem inspektoriDeleteItem = new JMenuItem("Изтрий");
		
		JPopupMenu ocenkiPopupMenu = new JPopupMenu();
		JMenuItem ocenkiEditItem = new JMenuItem("Редактирай");
		JMenuItem ocenkiDeleteItem = new JMenuItem("Изтрий");

	// Декларация на табовете
		JTabbedPane jtp = new JTabbedPane();
		JPanel studentiTab = new JPanel();
		JPanel specialnostiTab = new JPanel();
		JPanel predmetiTab = new JPanel();
		JPanel inspektoriTab = new JPanel();
		JPanel ocenkiTab = new JPanel();
	
	// Делкарация на отделните части на табовете
		JPanel studentiTabTop = new JPanel();
		JPanel studentiTabMid = new JPanel();
		JPanel specialnostiTabTop = new JPanel();
		JPanel specialnostiTabMid = new JPanel();
		JPanel predmetiTabTop = new JPanel();
		JPanel predmetiTabMid = new JPanel();
		JPanel inspektoriTabTop = new JPanel();
		JPanel inspektoriTabMid = new JPanel();
		JPanel ocenkiTabTop = new JPanel();
		JPanel ocenkiTabMid = new JPanel();
		JPanel ocenkiTabTopTop = new JPanel();
		JPanel ocenkiTabTopBot = new JPanel();
	
	// Декларация на въвеждането на студенти
		JLabel studentiFnLabel = new JLabel("Факултетен номер ",SwingConstants.RIGHT);
		JLabel studentiImeLabel = new JLabel("Име ",SwingConstants.RIGHT);
		JLabel studentiFamiliqLabel = new JLabel("Фамилия ",SwingConstants.RIGHT);
		JLabel studentiTelLabel = new JLabel("Телефон ",SwingConstants.RIGHT);
		JLabel studentiAdressLabel = new JLabel("Адрес ",SwingConstants.RIGHT);
		JLabel studentiSpecLabel = new JLabel("Специалност ",SwingConstants.RIGHT);
		
		JTextField studentiFnTField = new JTextField();
		JTextField studentiImeTField = new JTextField();
		JTextField studentiFamiliqTField = new JTextField();
		JTextField studentiTelTField = new JTextField();
		JTextField studentiAdressTField = new JTextField();
		JTextField studentiSpecTField = new JTextField();
		
		JComboBox<String> studentiCombo = new JComboBox<String>();
		JComboBox<String> studentiEditCombo = new JComboBox<String>();
		JComboBox<String> studentiSpecialnostiFilter = new JComboBox<String>();
		
		JButton studentiAddButton = new JButton("Добави");
		JButton studentiDelButton = new JButton("Изтрий");
		JButton studentiEditButton = new JButton("Редактирай");
		JButton studentiSearchButton = new JButton("Търси");
		JButton studentiImportButton = new JButton("Импортиране");
		JButton studentiExportButton = new JButton("Експортиране");
		
	// Делкарация на въвеждането на специалности
		JLabel specialnostiSpecLabel = new JLabel("Специалност ",SwingConstants.RIGHT);
		JLabel specialnostiInspLabel = new JLabel("Инспектор ",SwingConstants.RIGHT);
		JTextField specialnostiSpecTField = new JTextField();
		
		JComboBox<String> specialnostiCombo = new JComboBox<String>();
		JComboBox<String> specialnostiEditCombo = new JComboBox<String>();
		
		JButton specialnostiAddButton = new JButton("Добави");
		JButton specialnostiDelButton = new JButton("Изтрий");
		JButton specialnostiEditButton = new JButton("Редактирай");
		JButton specialnostiSearchButton = new JButton("Търси");
		JButton specialnostiImportButton = new JButton("Импортиране");
		JButton specialnostiExportButton = new JButton("Експортиране");
	
	// Делкарация на въвеждането на предмети
		JLabel predmetiPredmetLabel = new JLabel("Предмет ",SwingConstants.RIGHT);
		JTextField predmetiPredmetTField = new JTextField();
		JLabel predmetiSpecLabel = new JLabel("Специалност ",SwingConstants.RIGHT);
		
		JComboBox<String> predmetiSpecialnostiCombo = new JComboBox<String>();
		JComboBox<String> predmetiEditCombo = new JComboBox<String>();
		JComboBox<String> predmetiSpecialnostiFilter = new JComboBox<String>();
		
		JButton predmetiAddButton = new JButton("Добави");
		JButton predmetiDelButton = new JButton("Изтрий");
		JButton predmetiEditButton = new JButton("Редактирай");
		JButton predmetiSearchButton = new JButton("Търси");
		JButton predmetiImportButton = new JButton("Импортиране");
		JButton predmetiExportButton = new JButton("Експортиране");
		
	// Декларация на въвеждането на инспектори
		JLabel inspektoriImeLabel = new JLabel("Име ",SwingConstants.RIGHT);
		JTextField inspektoriImeTField = new JTextField();
		
		JComboBox<String> inspektoriEditCombo = new JComboBox<String>();
		
		JButton inspektoriAddButton = new JButton("Добави");
		JButton inspektoriDelButton = new JButton("Изтрий");
		JButton inspektoriEditButton = new JButton("Редактирай");
		JButton inspektoriSearchButton = new JButton("Търси");
		JButton inspektoriImportButton = new JButton("Импортиране");
		JButton inspektoriExportButton = new JButton("Експортиране");
		
	// Декларация на въвеждането на оценки
		JLabel ocenkiSpecialnostLabel = new JLabel("Покажи специалност");
		JComboBox<String> ocenkiSpecialnostCombo = new JComboBox<String>();
		JLabel ocenkiFnLabel = new JLabel("Студент");
		JComboBox<String> ocenkiFnCombo = new JComboBox<String>();
		JLabel ocenkiPredmetLabel = new JLabel("Предмет");
		JComboBox<String> ocenkiPredmetCombo = new JComboBox<String>();
		JLabel ocenkiOcenkaLabel = new JLabel("Оценка");
		JComboBox<String> ocenkiOcenkaCombo = new JComboBox<String>(new String[] {"","Слаб 2", "Среден 3", "Добър 4", "Мн.Добър 5", "Опличен 6"});
		
		JButton ocenkiAddButton = new JButton("Добави");
		JButton ocenkiDelButton = new JButton("Изтрий");
		JButton ocenkiEditButton = new JButton("Редактирай");
		JButton ocenkiSearchButton = new JButton("Търси");
		
		JCheckBox deleteAllCheckBox = new JCheckBox("Изтрии всички оценки за този студент");
		
		JButton ocenkiImportButton = new JButton("Импортиране");
		JButton ocenkiExportButton = new JButton("Експортиране");
		
	// Декларация на таблиците и скролерите	
		static JTable studentiTable = new JTable();
		JScrollPane studentiScroller = new JScrollPane(studentiTable);
		static JTable specialnostiTable = new JTable();
		JScrollPane specialnostiScroller = new JScrollPane(specialnostiTable);
		static JTable predmetiTable = new JTable();
		JScrollPane predmetiScroller = new JScrollPane(predmetiTable);
		static JTable inspektoriTable = new JTable();
		JScrollPane inspektoriScroller = new JScrollPane(inspektoriTable);
		static JTable ocenkiTable = new JTable();
		JScrollPane ocenkiScroller = new JScrollPane(ocenkiTable);
	
}
