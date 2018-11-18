package sysedu.gui;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import sysedu.db.DBservice;
import sysedu.domain.Oddzial;
import sysedu.domain.Przedmiot;
import sysedu.domain.Uczen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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



public class SerwisEdukacyjny extends JFrame {
	Uczen uczen = null;
	Przedmiot selectedPrzedmiot = null;
	
	
	public static void main(String[] args) {
		UIUtils.setPreferredLookAndFeel();
		NativeInterface.open();
		SerwisEdukacyjny frameTabel = new SerwisEdukacyjny(new Uczen());
		frameTabel.setVisible(true);	
	}
	
	 private void inicjalizujMenu() {

	        createMenuBar();

	        setTitle("Portal Edukacyjny");
	        setSize(360, 250);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }
	
	 /**
	  * Tworzymy menu dla przedmiotów
	  */
	 private void createMenuBar() {
	 		
	 		JMenuBar menubar = new JMenuBar();
	 		
	        ImageIcon iconObecnosc = new ImageIcon("notatki.png");
	        ImageIcon iconOcena = new ImageIcon("mat.png");
	        ImageIcon iconTest = new ImageIcon("test.png");
	 		
	 		JMenu mnMenuPolski = new JMenu("Jêzyk Polski");
	 		JMenu mnMenuAngielski = new JMenu("Jêzyk Angielski");
	 		JMenu mnMenuMatematyka = new JMenu("Matematyka");
	 		
	 		
	 		JMenuItem mntmNewMenuItem = new JMenuItem("Oceny", iconOcena);
	 		mntmNewMenuItem.addActionListener(new ActionListener() {
	 			public void actionPerformed(ActionEvent arg0) {
	 				dodajPanelOceny();
	 			}
	 		});
	 		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Sprawdziany", iconTest);
	 		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Materia³y", iconObecnosc);
	 		
	 		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Oceny", iconOcena);
	 		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Sprawdziany", iconTest);
	 		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Materia³y", iconObecnosc);
	 		
	 		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Oceny", iconOcena);
	 		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Sprawdziany", iconTest);
	 		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Materia³y", iconObecnosc);
	 		
	 		//JMenuItem mntmNewMenuItem_9 = new JMenuItem("Wyjœcie");
	 		
	 		JMenuItem exitMenuItem = new JMenuItem("WyjdŸ");
	        exitMenuItem.setToolTipText("Wyjœcie z programu");
	        //exitMenuItem.add(mntmNewMenuItem_9);
	        exitMenuItem.addActionListener((event) -> System.exit(0));
	        
	        JMenuItem mnNic = new JMenuItem("");
	         
	        mnMenuPolski.add(mntmNewMenuItem);
	        mnMenuPolski.add(mntmNewMenuItem_1);
	        mnMenuPolski.add(mntmNewMenuItem_2);
	        
	        mnMenuAngielski.add(mntmNewMenuItem_3);
	        mnMenuAngielski.add(mntmNewMenuItem_4);
	        mnMenuAngielski.add(mntmNewMenuItem_5);
	        
	        mnMenuMatematyka.add(mntmNewMenuItem_6);
	        mnMenuMatematyka.add(mntmNewMenuItem_7);
	        mnMenuMatematyka.add(mntmNewMenuItem_8);
			
			
	        menubar.add(mnMenuPolski);
	        menubar.add(mnMenuAngielski);
	        menubar.add(mnMenuMatematyka);
	        
	        menubar.add(exitMenuItem);
	        //menubar.add(mnNic);

	        setJMenuBar(menubar);
	}

	protected void dodajPanelOceny() {
		getPanel_1().removeAll();
		getPanel_1().add(getOcenyPolski());
		getPanel_1().doLayout();
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	JPanel panel = new JPanel();
	private JPanel contentPane;
	private JPanel panel_1;
	/**
	 * @wbp.nonvisual location=14,-21
	 */
	private final OcenyPolski ocenyPolski = new OcenyPolski();
	private JList listaPrzedmiotow;
	/**
	 * @wbp.nonvisual location=102,-21
	 */
	private final PanelPrzedmiotu panelPrzedmiotu = new PanelPrzedmiotu();
	
	
	/**
	 * Tworzymy obiekty okna.
	 */
	public SerwisEdukacyjny(Uczen uczen) {
		super("SerwisEdukacyjny");
		this.uczen = uczen;
		
		inicjalizujMenu();
		setSize(1280,720);
		setLocation(500,280);

		getContentPane().add(panel);
			
		panel_1 = new JPanel();
		panel_1.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				System.out.println("DOKUMENT WYŒWIETLONY");
				pokazPowitalnyPDF(panel_1, "file:///C:/Users/mikib/Desktop/2018.pdf");
			}
		});
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(1074, 0, 17, 537);
		//panel_1.add(scrollBar);
		
		
		/**
		 * TO DO
		 * Ustawiæ lepsze logo.
		 */
		BufferedImage img=new ImgUtils().scaleImage(122,81,"blackboard.png");
	    ImageIcon imageIcon = new ImageIcon(img);
		
		/**
		 * TO DO
		 * Tu bêdzie pobieraæ z bazy danych imie ucznia.
		 */
		
		/**
		 * TO DO
		 * Tu bêdzie pobieraæ z bazy danych i liczyæ œredni¹ ucznia.
		 */
		
		JPanel panelGora = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelGora.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panelGora, BorderLayout.NORTH);
		JLabel lblNewLabel = new JLabel(imageIcon);
		panelGora.add(lblNewLabel);
		
		JLabel lblUcze = new JLabel("Ucze\u0144: ");
		panelGora.add(lblUcze);
		lblUcze.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		
		JLabel lblNewLabel_1 = new JLabel(uczen.imie);
		panelGora.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblredniaOcen = new JLabel("\u015Arednia ocen: ");
		panelGora.add(lblredniaOcen);
		lblredniaOcen.setFont(new Font("Tahoma", Font.BOLD, 14));
		JLabel lblredniaucznia = new JLabel("\u015AredniaUcznia");
		panelGora.add(lblredniaucznia);
		lblredniaucznia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JPanel panelLewy = new JPanel();
		panelLewy.setPreferredSize(new Dimension(150, 10));
		panel.add(panelLewy, BorderLayout.WEST);
		panelLewy.setLayout(new BorderLayout(0, 0));
		
		Oddzial oddzial = DBservice.getOddzial(uczen);
		DBservice.setPrzedmioty(oddzial);
		listaPrzedmiotow = new JList(oddzial.getPrzedmioty().toArray());
		listaPrzedmiotow.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectPrzedmiot();
			}
		});
		
		listaPrzedmiotow.setBorder(new TitledBorder(null, "Przedmioty", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLewy.add(listaPrzedmiotow);
		
		DefaultListModel<Przedmiot> listmodel = new DefaultListModel<Przedmiot>();		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	protected void selectPrzedmiot() {
		panelPrzedmiotu.setUczenPrzedmiot(uczen, (Przedmiot)getListaPrzedmiotow().getSelectedValue());
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
	public OcenyPolski getOcenyPolski() {
		return ocenyPolski;
	}
	public JList getListaPrzedmiotow() {
		return listaPrzedmiotow;
	}
}
