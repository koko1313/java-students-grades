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

	// �������� ���������� ###################################################################
	boolean needToCheck = true;
	boolean needToGetDataFromFields = true;
	JFileChooser fc = new JFileChooser(); // Browser-a
	File file = null; // ���������� �� ���� (������/�������)
	FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text"); // ������ ��� ���������� �� ���� �� ������
	
		// �������� �� ��������
			String studentiInputFn;
			String studentiInputIme;
			String studentiInputFamiliq;
			String studentiInputTel;
			String studentiInputAdress;
			String studentiInputSpecString;
			String studentiId;
			int studentiInputSpec=0;
		// �������� �� ������������
			String specialnostiInputSpec;
			String specialnostiInputInspString;
			int specialnostiInputInsp=0;
		// �������� �� ��������
			String predmetiInputPredmet;
			String predmetiInputSpecString;
			int predmetiInputSpec=0;
		// ���������� �� ������������
			String inspektoriInputIme;
		// ���������� �� ��������
			int studentId;
			int ocenkiPredmet = 0;
			int ocenkiSpecialnost = 0;
			int ocenkiOcenka = 0;
			
	// ���������� �� �������� ################################################################
	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	MyModel model = null;

	// Popup menu
		JTable selectedTable;
		String selectedData = "";
		
		JPopupMenu studentiPopupMenu = new JPopupMenu();
		JMenuItem studentiEditItem = new JMenuItem("����������");
		JMenuItem studentiDeleteItem = new JMenuItem("������");
		
		JPopupMenu specialnostiPopupMenu = new JPopupMenu();
		JMenuItem specialnostiEditItem = new JMenuItem("����������");
		JMenuItem specialnostiDeleteItem = new JMenuItem("������");
		
		JPopupMenu predmetiPopupMenu = new JPopupMenu();
		JMenuItem predmetiEditItem = new JMenuItem("����������");
		JMenuItem predmetiDeleteItem = new JMenuItem("������");
		
		JPopupMenu inspektoriPopupMenu = new JPopupMenu();
		JMenuItem inspektoriEditItem = new JMenuItem("����������");
		JMenuItem inspektoriDeleteItem = new JMenuItem("������");
		
		JPopupMenu ocenkiPopupMenu = new JPopupMenu();
		JMenuItem ocenkiEditItem = new JMenuItem("����������");
		JMenuItem ocenkiDeleteItem = new JMenuItem("������");

	// ���������� �� ��������
		JTabbedPane jtp = new JTabbedPane();
		JPanel studentiTab = new JPanel();
		JPanel specialnostiTab = new JPanel();
		JPanel predmetiTab = new JPanel();
		JPanel inspektoriTab = new JPanel();
		JPanel ocenkiTab = new JPanel();
	
	// ���������� �� ��������� ����� �� ��������
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
	
	// ���������� �� ����������� �� ��������
		JLabel studentiFnLabel = new JLabel("���������� ����� ",SwingConstants.RIGHT);
		JLabel studentiImeLabel = new JLabel("��� ",SwingConstants.RIGHT);
		JLabel studentiFamiliqLabel = new JLabel("������� ",SwingConstants.RIGHT);
		JLabel studentiTelLabel = new JLabel("������� ",SwingConstants.RIGHT);
		JLabel studentiAdressLabel = new JLabel("����� ",SwingConstants.RIGHT);
		JLabel studentiSpecLabel = new JLabel("����������� ",SwingConstants.RIGHT);
		
		JTextField studentiFnTField = new JTextField();
		JTextField studentiImeTField = new JTextField();
		JTextField studentiFamiliqTField = new JTextField();
		JTextField studentiTelTField = new JTextField();
		JTextField studentiAdressTField = new JTextField();
		JTextField studentiSpecTField = new JTextField();
		
		JComboBox<String> studentiCombo = new JComboBox<String>();
		JComboBox<String> studentiEditCombo = new JComboBox<String>();
		JComboBox<String> studentiSpecialnostiFilter = new JComboBox<String>();
		
		JButton studentiAddButton = new JButton("������");
		JButton studentiDelButton = new JButton("������");
		JButton studentiEditButton = new JButton("����������");
		JButton studentiSearchButton = new JButton("�����");
		JButton studentiImportButton = new JButton("�����������");
		JButton studentiExportButton = new JButton("������������");
		
	// ���������� �� ����������� �� ������������
		JLabel specialnostiSpecLabel = new JLabel("����������� ",SwingConstants.RIGHT);
		JLabel specialnostiInspLabel = new JLabel("��������� ",SwingConstants.RIGHT);
		JTextField specialnostiSpecTField = new JTextField();
		
		JComboBox<String> specialnostiCombo = new JComboBox<String>();
		JComboBox<String> specialnostiEditCombo = new JComboBox<String>();
		
		JButton specialnostiAddButton = new JButton("������");
		JButton specialnostiDelButton = new JButton("������");
		JButton specialnostiEditButton = new JButton("����������");
		JButton specialnostiSearchButton = new JButton("�����");
		JButton specialnostiImportButton = new JButton("�����������");
		JButton specialnostiExportButton = new JButton("������������");
	
	// ���������� �� ����������� �� ��������
		JLabel predmetiPredmetLabel = new JLabel("������� ",SwingConstants.RIGHT);
		JTextField predmetiPredmetTField = new JTextField();
		JLabel predmetiSpecLabel = new JLabel("����������� ",SwingConstants.RIGHT);
		
		JComboBox<String> predmetiSpecialnostiCombo = new JComboBox<String>();
		JComboBox<String> predmetiEditCombo = new JComboBox<String>();
		JComboBox<String> predmetiSpecialnostiFilter = new JComboBox<String>();
		
		JButton predmetiAddButton = new JButton("������");
		JButton predmetiDelButton = new JButton("������");
		JButton predmetiEditButton = new JButton("����������");
		JButton predmetiSearchButton = new JButton("�����");
		JButton predmetiImportButton = new JButton("�����������");
		JButton predmetiExportButton = new JButton("������������");
		
	// ���������� �� ����������� �� ����������
		JLabel inspektoriImeLabel = new JLabel("��� ",SwingConstants.RIGHT);
		JTextField inspektoriImeTField = new JTextField();
		
		JComboBox<String> inspektoriEditCombo = new JComboBox<String>();
		
		JButton inspektoriAddButton = new JButton("������");
		JButton inspektoriDelButton = new JButton("������");
		JButton inspektoriEditButton = new JButton("����������");
		JButton inspektoriSearchButton = new JButton("�����");
		JButton inspektoriImportButton = new JButton("�����������");
		JButton inspektoriExportButton = new JButton("������������");
		
	// ���������� �� ����������� �� ������
		JLabel ocenkiSpecialnostLabel = new JLabel("������ �����������");
		JComboBox<String> ocenkiSpecialnostCombo = new JComboBox<String>();
		JLabel ocenkiFnLabel = new JLabel("�������");
		JComboBox<String> ocenkiFnCombo = new JComboBox<String>();
		JLabel ocenkiPredmetLabel = new JLabel("�������");
		JComboBox<String> ocenkiPredmetCombo = new JComboBox<String>();
		JLabel ocenkiOcenkaLabel = new JLabel("������");
		JComboBox<String> ocenkiOcenkaCombo = new JComboBox<String>(new String[] {"","���� 2", "������ 3", "����� 4", "��.����� 5", "������� 6"});
		
		JButton ocenkiAddButton = new JButton("������");
		JButton ocenkiDelButton = new JButton("������");
		JButton ocenkiEditButton = new JButton("����������");
		JButton ocenkiSearchButton = new JButton("�����");
		
		JCheckBox deleteAllCheckBox = new JCheckBox("������ ������ ������ �� ���� �������");
		
		JButton ocenkiImportButton = new JButton("�����������");
		JButton ocenkiExportButton = new JButton("������������");
		
	// ���������� �� ��������� � ����������	
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
