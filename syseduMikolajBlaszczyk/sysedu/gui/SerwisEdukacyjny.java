package sysedu.gui;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import sysedu.db.DBservice;
import sysedu.domain.Materialy;
import sysedu.domain.Oddzial;
import sysedu.domain.Przedmiot;
import sysedu.domain.Uczen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuListener;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.Color;
import javax.swing.JEditorPane;
import java.awt.Dimension;
import chrriis.dj.nativeswing.NSOption;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Toolkit;
import javax.swing.UIManager;



public class SerwisEdukacyjny extends JFrame {
	Uczen uczen = null;
	Przedmiot selectedPrzedmiot = null;
	
	public static void main(String[] args) {
		UIUtils.setPreferredLookAndFeel();
		NativeInterface.open();
		SerwisEdukacyjny frameTabel = new SerwisEdukacyjny(new Uczen());
		frameTabel.setVisible(true);	
	}
	
	protected void dodajPanelOceny() {
		getPanel_1().removeAll();
		getPanel_1().doLayout();	
	}

	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel();
	private JPanel panel_1;
	private JList listaPrzedmiotow;
	private final PanelPrzedmiotu panelPrzedmiotu = new PanelPrzedmiotu();
	
	
	/**
	 * Tworzymy obiekty okna.
	 */
	public SerwisEdukacyjny(Uczen uczen) {
		super("SerwisEdukacyjny");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SerwisEdukacyjny.class.getResource("/resources/gdynia-herb.png")));
		this.uczen = uczen;
		setSize(1280,720);
		setLocation(500,280);
		getContentPane().add(panel);	
		panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.addComponentListener(new ComponentAdapter() {
			/**
			 * Po zalogowaniu zawsze wyœwietlamy PDFa z list¹ ostatnich zmian lub informacji
			 * które chce wyœwietliæ szko³a
			 */
			@Override
			public void componentMoved(ComponentEvent arg0) {
				pokazPowitalnyPDF(panel_1, "https://sysedupdf.blob.core.windows.net/pdf/Ostatnie zmiany.pdf");
			}
		});
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(1074, 0, 17, 537);
		//panel_1.add(scrollBar);
		
		BufferedImage img=new ImgUtils().scaleImage(122,81,"blackboard.png");
		JPanel panelGora = new JPanel();
		panelGora.setPreferredSize(new Dimension(10, 120));
		panelGora.setBackground(Color.PINK);
		panel.add(panelGora, BorderLayout.NORTH);
		panelGora.setLayout(null);
		JLabel lblNewLabel = new JLabel(new ImageIcon("C:\\Users\\mikib\\Downloads\\gdynia-herb.png"));
		lblNewLabel.setBounds(0, 0, 149, 120);
		lblNewLabel.setPreferredSize(new Dimension(128, 120));
		panelGora.add(lblNewLabel);
		
		JLabel lblUcze = new JLabel("Ucze\u0144: ");
		lblUcze.setBounds(158, 51, 90, 27);
		panelGora.add(lblUcze);
		lblUcze.setFont(new Font("Tahoma", Font.BOLD, 22));
		
		
		JLabel lblNewLabel_1 = new JLabel(uczen.imie);
		lblNewLabel_1.setBounds(246, 51, 197, 27);
		panelGora.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		JLabel lblSysedu = new JLabel("SYSEDU");
		lblSysedu.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 45));
		lblSysedu.setBounds(1130, 37, 134, 44);
		panelGora.add(lblSysedu);
		
		JPanel panelLewy = new JPanel();
		panelLewy.setPreferredSize(new Dimension(150, 10));
		panel.add(panelLewy, BorderLayout.WEST);
		panelLewy.setLayout(new BorderLayout(0, 0));
			
		/**
		 * Lista Przedmiotów przypisanych do klasy do której uczêszcza uczeñ
		 */
		Oddzial oddzial = DBservice.getOddzial(uczen);
		DBservice.setPrzedmioty(oddzial);
		listaPrzedmiotow = new JList(oddzial.getPrzedmioty().toArray());
		listaPrzedmiotow.setBackground(Color.PINK);
		listaPrzedmiotow.setFont(new Font("Tahoma", Font.BOLD, 18));
		listaPrzedmiotow.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectPrzedmiot();
			}
		});
		
		listaPrzedmiotow.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Przedmioty", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panelLewy.add(listaPrzedmiotow);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	/**
	 * Akcja przedstawiaj¹ca co siê dzieje po wyborze przedmiotu
	 * dla optymalizacji ³adujemy od razu materia³y i oceny
	 * dziêki czemu potem program nie wczytuje ich za ka¿dym razem
	 * gdy zmienimy zak³adkê
	 */
	protected void selectPrzedmiot() {
		panelPrzedmiotu.setUczenPrzedmiot(uczen, (Przedmiot)getListaPrzedmiotow().getSelectedValue());
		panelPrzedmiotu.setMaterialy((Przedmiot)getListaPrzedmiotow().getSelectedValue());
		panelPrzedmiotu.setSprawdziany((Przedmiot)getListaPrzedmiotow().getSelectedValue());
		if(selectedPrzedmiot==null) {
			getPanel_1().removeAll();
			getPanel_1().add(panelPrzedmiotu);
			getPanel_1().doLayout();
			getPanel_1().validate();
		}

		
	}

	/**
	 * Panel otwierania PDFów
	 * mo¿na wyœwietlaæ filmy, strony i PDFy
	 * mo¿na otwieraæ PDFy na wybranej stronie
	 */
	protected void pokazPowitalnyPDF(JPanel panel, String zrodlo) {
		final JWebBrowser brow = new JWebBrowser();
		brow.setLocationBarVisible(false);
		brow.setButtonBarVisible(false);
		brow.setMenuBarVisible(false);
		brow.setStatusBarVisible(true);
		brow.navigate(zrodlo);
		panel.add(brow, BorderLayout.CENTER);
		
	}
	
	public JPanel getPanel_1() {
		return panel_1;
	}

	public JList getListaPrzedmiotow() {
		return listaPrzedmiotow;
	}
}
