package sysedu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sysedu.db.DBservice;
import sysedu.dialogi.JDialogAddKlasa;
import sysedu.dialogi.JDialogAddNauczyciel;
import sysedu.dialogi.JDialogAddPrzedmiot;
import sysedu.dialogi.JDialogAddUczen;
import sysedu.dialogi.JDialogEditKlasa;
import sysedu.dialogi.JDialogEditNauczyciel;
import sysedu.dialogi.JDialogEditPrzedmiot;
import sysedu.dialogi.JDialogEditUczen;
import sysedu.dialogi.JDialogListaNauczycieli;
import sysedu.dialogi.JDialogListaPrzedmiotow;
import sysedu.dialogi.JDialogListaUczniow;
import sysedu.domain.Admin;
import sysedu.domain.Materialy;
import sysedu.domain.Nauczyciel;
import sysedu.domain.Ocena;
import sysedu.domain.Oddzial;
import sysedu.domain.Przedmiot;
import sysedu.domain.Uczen;

import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.LineNumberInputStream;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.ComponentOrientation;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class PanelAdministratora extends JFrame {

	private JPanel contentPane;
	
	private DefaultListModel<Oddzial> listModelOddzialy = new DefaultListModel<Oddzial>();
	private DefaultListModel<Uczen> listModelUczniowieKlasy = new DefaultListModel<Uczen>();
	private DefaultListModel<Przedmiot> listModelPrzedmiotyKlasy = new DefaultListModel<Przedmiot>();
	private DefaultListModel<Nauczyciel> listModelNauczyciele = new DefaultListModel<Nauczyciel>();
	private DefaultListModel<Uczen> listModelUczniowie = new DefaultListModel<Uczen>();
	private DefaultListModel<Przedmiot> listModelPrzedmioty = new DefaultListModel<Przedmiot>();
	private DefaultListModel<Nauczyciel> listModelNauczycielePrzedmiotuKlasy = new DefaultListModel<Nauczyciel>();
	private Oddzial selectedOddzial = null;
	private Uczen selectedUczen = null;
	private Uczen selectedUczen1 = null;
	private Przedmiot selectedPrzedmiot = null;
	private Przedmiot selectedPrzedmiotKlasy = null;
	private Nauczyciel selectedNauczyciel = null;
	private Nauczyciel selectedNauczycielPrzedmiotuKlasy = null;

	private Admin admin = null;
	private JList listKlas;
	private JList listUczniowKlasy;
	private JList listNauczycieli;
	private JList listUczniow;
	private JList listPrzedmioty;
	private JList listPrzedmiotyKlasy;
	private JList listNauczycielePrzedmiotuKlasy;
	private JScrollPane scrollPane;
	
	/**
	 * Uruchom.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelAdministratora frame = new PanelAdministratora(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Admin getAdmin() {
		return admin;
	}


	public void setAdmin(Admin admin) {
		this.admin = admin;
		this.setTitle("Panel Admina: "+admin.getNazwisko()+" "+admin.getImie());
		
	}
	
	/**
	 * Stwórz ramkê.
	 */
	public PanelAdministratora(Admin admin) {
		setBackground(Color.PINK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PanelAdministratora.class.getResource("/resources/gdynia-herb.png")));
		setAdmin(admin);
		readOddzialy();
		readNauczyciele();
		readUczniowie();
		readPzedmioty();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1275, 826);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		/**
		 * Stwórz interfejs zak³adek .
		 */
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setBackground(Color.BLACK);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Klasy", null, panel, null);
		
		/**
		 * przyciski Dodaj, usuñ i edytuj oraz akcje po ich klikniêciu
		 */
		
		/**
		 * zape³niamy listê uczniów nale¿¹cych do danej klasy
		 */
		listKlas = new JList(listModelOddzialy);
		listKlas.setFont(new Font("Tahoma", Font.BOLD, 20));
		listKlas.setBorder(new TitledBorder(null, "Klasy", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		listKlas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedOddzial();
			}
		});
		panel.setLayout(new BorderLayout(10, 10));
		listKlas.setPreferredSize(new Dimension(100, 700));
		panel.add(listKlas, BorderLayout.WEST);
		
		JPopupMenu popupMenuKlasy = new JPopupMenu();
		addPopup(listKlas, popupMenuKlasy);
		JMenuItem mntmDodajKlasy = new JMenuItem("Dodaj");
		mntmDodajKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodajKlase();
			}
		});
		popupMenuKlasy.add(mntmDodajKlasy);
		
		JMenuItem mntmUsuKlasy = new JMenuItem("Usu\u0144");
		mntmUsuKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunKlase();
			}
		});
		
		JMenuItem mntmEdytujKlasy = new JMenuItem("Edytuj");
		mntmEdytujKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edytujKlase();
			}
		});
		popupMenuKlasy.add(mntmEdytujKlasy);
		popupMenuKlasy.add(mntmUsuKlasy);
		
		/**
		 * przyciski Dodaj, usuñ i edytuj oraz akcje po ich klikniêciu
		 */
		
		/**
		 * Zape³niamy listê przedmiotów które posiada dana klasa
		 */
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 3, 10, 10));
		
		scrollPane = new JScrollPane();
		panel_4.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(300, 700));
		listUczniowKlasy = new JList(listModelUczniowieKlasy);
		listUczniowKlasy.setFixedCellHeight(25);
		scrollPane.setViewportView(listUczniowKlasy);
		listUczniowKlasy.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		listUczniowKlasy.setFont(new Font("Tahoma", Font.BOLD, 16));
		listUczniowKlasy.setBorder(new TitledBorder(null, "Uczniowie w Klasie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listUczniowKlasy.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectUczen();
			}
		});
		
		JPopupMenu popupMenuUczniaKlasy = new JPopupMenu();
		addPopup(listUczniowKlasy, popupMenuUczniaKlasy);
		JMenuItem mntmDodajUczniaKlasy = new JMenuItem("Dodaj");
		mntmDodajUczniaKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodajUczniaKlasy();
			}
		});
		popupMenuUczniaKlasy.add(mntmDodajUczniaKlasy);
		
		JMenuItem mntmUsuUczniaKlasy = new JMenuItem("Usu\u0144");
		mntmUsuUczniaKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunUczenKlasa();
			}
		});
		popupMenuUczniaKlasy.add(mntmUsuUczniaKlasy);
		listPrzedmiotyKlasy = new JList(listModelPrzedmiotyKlasy);
		panel_4.add(listPrzedmiotyKlasy);
		listPrzedmiotyKlasy.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedPrzedmiotKlasy();
			}
		});
		listPrzedmiotyKlasy.setFont(new Font("Tahoma", Font.BOLD, 16));
		listPrzedmiotyKlasy.setBorder(new TitledBorder(null, "Przedmioty Klasy", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listPrzedmiotyKlasy.setPreferredSize(new Dimension(300, 700));
		
		JPopupMenu popupMenu_2 = new JPopupMenu();
		addPopup(listPrzedmiotyKlasy, popupMenu_2);
		JMenuItem mntmDodajPrzedmiotKlasy = new JMenuItem("Dodaj");
		mntmDodajPrzedmiotKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodajPrzedmiotKlasy();
			}
		});
		popupMenu_2.add(mntmDodajPrzedmiotKlasy);
		
		JMenuItem mntmUsuPrzedmiotKlasy = new JMenuItem("Usu\u0144");
		mntmUsuPrzedmiotKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunPrzedmiotKlasy();
			}
		});
		popupMenu_2.add(mntmUsuPrzedmiotKlasy);
		listNauczycielePrzedmiotuKlasy = new JList(listModelNauczycielePrzedmiotuKlasy);
		panel_4.add(listNauczycielePrzedmiotuKlasy);
		listNauczycielePrzedmiotuKlasy.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedNauczycielPrzedmiotuKlasy();
			}
		});
		listNauczycielePrzedmiotuKlasy.setFont(new Font("Tahoma", Font.BOLD, 16));
		listNauczycielePrzedmiotuKlasy.setBorder(new TitledBorder(null, "Nauczyciele ucz\u0105cy przedmioty w tej klasie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listNauczycielePrzedmiotuKlasy.setPreferredSize(new Dimension(300, 700));
		
		JPopupMenu popupMenu_3 = new JPopupMenu();
		addPopup(listNauczycielePrzedmiotuKlasy, popupMenu_3);
		
		JMenuItem mntmDodajNauczycielaPrzedmiotuKlasy = new JMenuItem("Dodaj");
		mntmDodajNauczycielaPrzedmiotuKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodajNauczycielaPrzedmiotuKlasy();
			}
		});
		popupMenu_3.add(mntmDodajNauczycielaPrzedmiotuKlasy);
		
		JMenuItem mntmUsuNauczycielaPrzedmiotuKlasy = new JMenuItem("Usu\u0144");
		mntmUsuNauczycielaPrzedmiotuKlasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunNauczycielaPrzedmiotuKlasy();
			}
		});
		popupMenu_3.add(mntmUsuNauczycielaPrzedmiotuKlasy);
		
		/**
		 * przyciski Dodaj, usuñ i edytuj oraz akcje po ich klikniêciu
		 */
		
		/**
		 * zape³niamy listê Nauczycieli ucz¹cych danego przedmiotu w zaznaczonej klasie
		 */
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Nauczyciele", null, panel_1, null);
		
		/**
		 * zape³niamy listê nauczycieli
		 */
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(300, 100));
		panel_1.add(scrollPane_1, BorderLayout.WEST);
		listNauczycieli = new JList(listModelNauczyciele);
		listNauczycieli.setFixedCellHeight(25);
		listNauczycieli.setPreferredSize(new Dimension(300, listNauczycieli.getFixedCellHeight()*listModelNauczyciele.size()));
		scrollPane_1.setViewportView(listNauczycieli);
		listNauczycieli.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedNauczyciel();
			}
		});
		listNauczycieli.setFont(new Font("Tahoma", Font.BOLD, 16));
		listNauczycieli.setBorder(new TitledBorder(null, "Nauczyciele", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listNauczycieli.setPreferredSize(new Dimension(300, 600));
		
		JPopupMenu popupMenuNauczyciela = new JPopupMenu();
		addPopup(listNauczycieli, popupMenuNauczyciela);
		
		JMenuItem mntmDodajNauczyciela = new JMenuItem("Dodaj");
		mntmDodajNauczyciela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajNauczyciela();
			}
		});
		popupMenuNauczyciela.add(mntmDodajNauczyciela);
		
		JMenuItem mntmZmieNauczyciela = new JMenuItem("Edytuj");
		mntmZmieNauczyciela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edytujNauczyciela();
			}
		});
		popupMenuNauczyciela.add(mntmZmieNauczyciela);
		
		JMenuItem mntmUsuNauczyciela = new JMenuItem("Usu\u0144");
		mntmUsuNauczyciela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunNauczyciela();
			}
		});
		popupMenuNauczyciela.add(mntmUsuNauczyciela);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Uczniowie", null, panel_2, null);
		
		/**
		 * zape³niamy listê uczniów
		 */
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setPreferredSize(new Dimension(300, 100));
		panel_2.add(scrollPane_2, BorderLayout.WEST);
		listUczniow = new JList(listModelUczniowie);
		listUczniow.setFixedCellHeight(25);
		listUczniow.setPreferredSize(new Dimension(300, listUczniow.getFixedCellHeight()*listModelUczniowie.size()));
		scrollPane_2.setViewportView(listUczniow);
		listUczniow.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedUczen();
			}
		});
		listUczniow.setFont(new Font("Tahoma", Font.BOLD, 16));
		listUczniow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		listUczniow.setBorder(new TitledBorder(null, "Uczniowie", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listUczniow.setPreferredSize(new Dimension(300, 600));
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(listUczniow, popupMenu);
		
		JMenuItem mntmDodaj = new JMenuItem("Dodaj");
		mntmDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodajUcznia();
			}
		});
		popupMenu.add(mntmDodaj);
		
		JMenuItem mntmEdytuj = new JMenuItem("Edytuj");
		mntmEdytuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edytujUcznia();
			}
		});
		popupMenu.add(mntmEdytuj);
		
		JMenuItem mntmUsu = new JMenuItem("Usu\u0144");
		mntmUsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunUcznia();
			}
		});
		popupMenu.add(mntmUsu);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Przedmioty", null, panel_3, null);
		
		/**
		 * zape³niamy listê przedmiotó
		 */
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setPreferredSize(new Dimension(300, 100));
		panel_3.add(scrollPane_3, BorderLayout.WEST);
		listPrzedmioty = new JList(listModelPrzedmioty);
		listPrzedmioty.setFixedCellHeight(25);
		listPrzedmioty.setPreferredSize(new Dimension(300, listPrzedmioty.getFixedCellHeight()*listModelPrzedmioty.size()));
		scrollPane_3.setViewportView(listPrzedmioty);
		listPrzedmioty.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedPrzedmiot();
			}
		});
		listPrzedmioty.setPreferredSize(new Dimension(300, 600));
		listPrzedmioty.setBorder(new TitledBorder(null, "Przedmioty", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listPrzedmioty.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JPopupMenu popupMenu_1 = new JPopupMenu();
		addPopup(listPrzedmioty, popupMenu_1);
		
		JMenuItem mntmDodaj_1 = new JMenuItem("Dodaj");
		mntmDodaj_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodajPrzedmiot();
			}
		});
		popupMenu_1.add(mntmDodaj_1);
		
		JMenuItem mntmEdytuj_1 = new JMenuItem("Edytuj");
		mntmEdytuj_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edytujPrzedmiot();
			}
		});
		popupMenu_1.add(mntmEdytuj_1);
		
		JMenuItem mntmZmie = new JMenuItem("Usu\u0144");
		mntmZmie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunPrzedmiot();
			}
		});
		popupMenu_1.add(mntmZmie);
		
		JLabel label = new JLabel("SYSEDU");
		label.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 45));
		contentPane.add(label, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PanelAdministratora.class.getResource("/resources/gdynia-herb.png")));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
	}

	/**
	 * metoda odpowiedzialna za edytowanie odnosi siê do
	 * JDialogu gdzie wybieramy odpowiednie w³aœciwoœci i potwierdzamy
	 * po czym nasz zamieniony obiekt jest przekazywany do zapytania bazodanowego
	 * w DBservice
	 */
	protected void edytujKlase() {
		JDialogEditKlasa edycja = new JDialogEditKlasa();
		edycja.setEditted(selectedOddzial);
		edycja.setVisible(true);
		if(selectedOddzial!=null) {
			if(DBservice.zmienKlasa(selectedOddzial)){
				JOptionPane.showMessageDialog(this, "Zmieniono Klasê", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d zmiany Klasy. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	protected void edytujPrzedmiot() {
		JDialogEditPrzedmiot edycja = new JDialogEditPrzedmiot();
		edycja.setEditted(selectedPrzedmiot);
		edycja.setVisible(true);
		if(selectedPrzedmiot!=null) {
			if(DBservice.zmienPrzedmiot(selectedPrzedmiot)){
				JOptionPane.showMessageDialog(this, "Zmieniono Przedmiot", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d zmiany Przedmiot. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void edytujNauczyciela() {
		JDialogEditNauczyciel edycja = new JDialogEditNauczyciel();
		edycja.setEditted(selectedNauczyciel);
		edycja.setVisible(true);
		if(selectedNauczyciel!=null) {
			if(DBservice.zmienNauczyciela(selectedNauczyciel)){
				JOptionPane.showMessageDialog(this, "Zmieniono Nauczyciela", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d zmiany Nauczyciela. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	

	protected void edytujUcznia() {
		JDialogEditUczen edycja = new JDialogEditUczen();
		edycja.setEditted(selectedUczen1);
		edycja.setVisible(true);
		if(selectedUczen1!=null) {
			if(DBservice.zmienUcznia(selectedUczen1)){
				JOptionPane.showMessageDialog(this, "Zmieniono Ucznia", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d zmiany Ucznia. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	/**
	 * metoda odpowiedzialna za usuwanie bierze zaznaczony obiekt
	 * z listy i wykonuje zapytanie usuniêcia z bazy danych
	 */
	protected void usunUczenKlasa() {
		if(selectedUczen!=null) {
			if(DBservice.usunUczenKlasa(selectedUczen.getId())) {
				JOptionPane.showMessageDialog(this, "Usuniêto ucznia z klasy ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelUczniowieKlasy.remove(getListUczniowKlasy().getSelectedIndex());
				//getListUczniowKlasy().setModel(listModelUczniowieKlasy);
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Ucznia. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void usunPrzedmiotKlasy() {
		if(selectedPrzedmiotKlasy!=null) {
			if(DBservice.usunPrzedmiotKlasa(selectedPrzedmiotKlasy.getId(), selectedOddzial.getId())) {
				JOptionPane.showMessageDialog(this, "Usuniêto Przedmiot z klasy ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelPrzedmiotyKlasy.remove(getListPrzedmiotyKlasy().getSelectedIndex());
				//getListUczniowKlasy().setModel(listModelUczniowieKlasy);
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Przedmiotu. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void usunNauczycielaPrzedmiotuKlasy() {
		if(selectedNauczycielPrzedmiotuKlasy!=null) {
			if(DBservice.usunNauczycielPrzedmiotuKlasy(selectedPrzedmiotKlasy.getId(), selectedOddzial.getId(), selectedNauczycielPrzedmiotuKlasy.getId())) {
				JOptionPane.showMessageDialog(this, "Usuniêto Przedmiot z klasy ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelNauczycielePrzedmiotuKlasy.remove(getListNauczycielePrzedmiotuKlasy().getSelectedIndex());
				//getListUczniowKlasy().setModel(listModelUczniowieKlasy);
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Przedmiotu. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void usunKlase() {
		if(selectedOddzial!=null) {
			if(DBservice.usunKlase(selectedOddzial.getId())) {
				JOptionPane.showMessageDialog(this, "Usuniêto Klasê ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelOddzialy.remove(getListKlas().getSelectedIndex());
				//getListUczniowKlasy().setModel(listModelUczniowieKlasy);
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Klasy. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void usunPrzedmiot() {
		if(selectedPrzedmiot!=null) {
			if(DBservice.usunPrzedmiot(selectedPrzedmiot.getId())) {
				JOptionPane.showMessageDialog(this, "Usuniêto Przedmiot ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelPrzedmioty.remove(getListPrzedmioty().getSelectedIndex());
				//getListUczniowKlasy().setModel(listModelUczniowieKlasy);
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Przedmiotu. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void usunNauczyciela() {
		if(selectedNauczyciel!=null) {
			if(DBservice.usunNauczyciela(selectedNauczyciel.getId())) {
				JOptionPane.showMessageDialog(this, "Usuniêto Nauczyciela ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelNauczyciele.remove(getListNauczycieli().getSelectedIndex());
				//getListUczniowKlasy().setModel(listModelUczniowieKlasy);
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Nauczyciela. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void usunUcznia() {
		if(selectedUczen1!=null) {
			if(DBservice.usunUcznia(selectedUczen1.getId())) {
				JOptionPane.showMessageDialog(this, "Usuniêto Ucznia ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelUczniowie.remove(getListUczniow().getSelectedIndex());
				//getListUczniowKlasy().setModel(listModelUczniowieKlasy);
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Ucznia. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	/**
	 * metoda odpowiedzialna za dodawanie odnosi siê do
	 * JDialogu gdzie wybieramy odpowiednie w³aœciwoœci i potwierdzamy
	 * po czym nasz zamieniony obiekt jest przekazywany do zapytania bazodanowego
	 * w DBservice
	 */
	protected void dodajKlase() {
		JDialogAddKlasa dodajKlase = new JDialogAddKlasa();
		dodajKlase.setVisible(true);
		if(dodajKlase.getCreated()!=null) {
			if(DBservice.dodajKlase(dodajKlase.getCreated())) {
				JOptionPane.showMessageDialog(this, "Dodano Klasê: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				//listModelOddzialy.addElement(dodajKlase.getCreated());
				readOddzialy();
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d dodania Klasy. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected void dodajNauczyciela() {
		JDialogAddNauczyciel dodajNauczyciela = new JDialogAddNauczyciel();
		dodajNauczyciela.setVisible(true);
		if(dodajNauczyciela.getCreated()!=null) {
			if(DBservice.dodajNauczyciela(dodajNauczyciela.getCreated())) {
				JOptionPane.showMessageDialog(this, "Dodano Nauczyciela: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				//listModelOddzialy.addElement(dodajKlase.getCreated());
				readNauczyciele();
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d dodania Nauczyciela. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void dodajUcznia() {
		JDialogAddUczen dodajUcznia = new JDialogAddUczen();
		dodajUcznia.setVisible(true);
		if(dodajUcznia.getCreated()!=null) {
			if(DBservice.dodajUcznia(dodajUcznia.getCreated())) {
				JOptionPane.showMessageDialog(this, "Dodano Ucznia: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				//listModelOddzialy.addElement(dodajKlase.getCreated());
				readUczniowie();
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d dodania Ucznia. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void dodajPrzedmiot() {
		JDialogAddPrzedmiot dodajPrzedmiot = new JDialogAddPrzedmiot();
		dodajPrzedmiot.setVisible(true);
		if(dodajPrzedmiot.getCreated()!=null) {
			if(DBservice.dodajPrzedmiot(dodajPrzedmiot.getCreated())) {
				JOptionPane.showMessageDialog(this, "Dodano Przedmiot: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				//listModelOddzialy.addElement(dodajKlase.getCreated());
				readPzedmioty();
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d dodania Przedmiotu. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	protected void dodajUczniaKlasy() {
		JDialogListaUczniow listaUczniow = new JDialogListaUczniow();
		listaUczniow.setVisible(true);
		if(listaUczniow.getSelectedUczen()!=null) {
			if(DBservice.dodajUczniaKlasy(listaUczniow.getSelectedUczen(), selectedOddzial.getId())) {
				JOptionPane.showMessageDialog(this, "Dodano ucznia: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelUczniowieKlasy.addElement(listaUczniow.getSelectedUczen());
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d dodania Ucznia. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}

	protected void dodajPrzedmiotKlasy() {
		JDialogListaPrzedmiotow listaPrzedmiotow = new JDialogListaPrzedmiotow();
		listaPrzedmiotow.setVisible(true);
		if(listaPrzedmiotow.getSelectedPrzedmiot()!=null) {
			if(DBservice.dodajPrzedmiotKlasy(listaPrzedmiotow.getSelectedPrzedmiot(), selectedOddzial.getId())) {
				JOptionPane.showMessageDialog(this, "Dodano Przedmiot do kalsy: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelPrzedmiotyKlasy.addElement(listaPrzedmiotow.getSelectedPrzedmiot());
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d dodania Przedmiotu do Klasy. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	protected void dodajNauczycielaPrzedmiotuKlasy() {
		JDialogListaNauczycieli listaNauczycieli = new JDialogListaNauczycieli();
		listaNauczycieli.setVisible(true);
		if(listaNauczycieli.getSelectedNauczyciel()!=null) {
			if(DBservice.dodajNaczycielaPrzedmiotuKlasy(listaNauczycieli.getSelectedNauczyciel(), selectedPrzedmiotKlasy.getId(), selectedOddzial.getId())) {
				JOptionPane.showMessageDialog(this, "Dodano Nauczyciela do Klasy: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelNauczycielePrzedmiotuKlasy.removeAllElements();
				listModelNauczycielePrzedmiotuKlasy.addElement(listaNauczycieli.getSelectedNauczyciel());
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d dodania Przedmiotu do Klasy. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}

	/**
	 * dziêki tej metodzie wiemy jaki obiekt z listy
	 * zosta³ zaznaczony, a na podstawie tego mo¿emy wype³niaæ pozosta³e
	 * listy lub wykonywaæ akcje na danym obiekcie takie jak usuniêcie lub edycja
	 */
	protected void selectedOddzial() {
		selectedOddzial=(Oddzial)getListKlas().getSelectedValue();
		if(selectedOddzial!=null) {
			listModelUczniowieKlasy.removeAllElements();
			List<Uczen> uczniowieKlasy = DBservice.getListaUczniow(selectedOddzial.getId());
			for (Iterator iterator = uczniowieKlasy.iterator(); iterator.hasNext();) {
				Uczen uczen = (Uczen) iterator.next();
				listModelUczniowieKlasy.addElement(uczen);
			}
			listUczniowKlasy.setPreferredSize(new Dimension(300,listUczniowKlasy.getFixedCellHeight()*listModelUczniowieKlasy.size()));
			
			listModelPrzedmiotyKlasy.removeAllElements();
			List<Przedmiot> przedmiotyKlasy = DBservice.getPrzedmiotyOddzialu(selectedOddzial);
			for (Iterator iterator = przedmiotyKlasy.iterator(); iterator.hasNext();) {
				Przedmiot przedmiot = (Przedmiot) iterator.next();
				listModelPrzedmiotyKlasy.addElement(przedmiot);
			}
		}
		
	}

	/**
	 * Dziêki tej metodzie wiemy jaki obiekt z listy
	 * zosta³ zaznaczony, a na podstawie tego mo¿emy wype³niaæ pozosta³e.
	 * W tym przypadku sprawdzamy jaki przedmiot po wyborze klasy zosta³ wybrany
	 * aby na podstawie tego wyœwietliæ nauczycieli zajmuj¹cych siê nim w danej klasie.
	 */
	protected void selectedPrzedmiotKlasy() {
		selectedPrzedmiotKlasy=(Przedmiot)getListPrzedmiotyKlasy().getSelectedValue();
		if(selectedPrzedmiotKlasy!=null) {
			listModelNauczycielePrzedmiotuKlasy.removeAllElements();
			List<Nauczyciel> nauczycielePrzedmiotuKlas = DBservice.getListaNauczycieliKlasyPrzedmiotu(selectedOddzial.getId(), selectedPrzedmiotKlasy.getId());
			for (Iterator iterator = nauczycielePrzedmiotuKlas.iterator(); iterator.hasNext();) {
				Nauczyciel nauczyciel = (Nauczyciel) iterator.next();
				listModelNauczycielePrzedmiotuKlasy.addElement(nauczyciel);
			}
		}
		
	}

	/**
	 * Je¿eli od naszego zaznaczenia nie jest zale¿na inna lista
	 * to wystarczy ¿e przeka¿emy do obiektu typu danego wybran¹
	 * wartoœæ z listy
	 */
	protected void selectedNauczyciel() {
		selectedNauczyciel=(Nauczyciel)getListNauczycieli().getSelectedValue();
	}
	
	protected void selectedPrzedmiot() {
		selectedPrzedmiot=(Przedmiot)getListPrzedmioty().getSelectedValue();	
	}
	
	protected void selectedNauczycielPrzedmiotuKlasy() {
		selectedNauczycielPrzedmiotuKlasy=(Nauczyciel)getListNauczycielePrzedmiotuKlasy().getSelectedValue();
	}

	protected void selectedUczen() {
		selectedUczen1=(Uczen)getListUczniow().getSelectedValue();
	}

	protected void selectUczen() {
		selectedUczen=(Uczen)getListUczniowKlasy().getSelectedValue();	
	}

	/**
	 * metody read wczytuj¹ dane do listy dodaj¹c wszystkie elementy
	 * do DefaultListModel. S³u¿¹ one te¿ do odœwie¿enia w niektórych przypadkach
	 */
	private void readOddzialy() {
		List<Oddzial> lista1 = DBservice.getListaOddzialow();
		listModelOddzialy.removeAllElements();
		for (Iterator iterator = lista1.iterator(); iterator.hasNext();) {
			Oddzial material1 = (Oddzial) iterator.next();
			listModelOddzialy.addElement(material1);
		}
		
	}
	private void readNauczyciele() {
		List<Nauczyciel> lista1 = DBservice.getListaNauczycieli();
		listModelNauczyciele.removeAllElements();
		for (Iterator iterator = lista1.iterator(); iterator.hasNext();) {
			Nauczyciel material1 = (Nauczyciel) iterator.next();
			listModelNauczyciele.addElement(material1);
		}
		if(listNauczycieli!=null)
			listNauczycieli.setPreferredSize(new Dimension(300, listNauczycieli.getFixedCellHeight()*listModelNauczyciele.size()));
		
	}
	private void readUczniowie(){
		List<Uczen> lista1 = DBservice.getUczniowie();
		listModelUczniowie.removeAllElements();
		for (Iterator iterator = lista1.iterator(); iterator.hasNext();) {
			Uczen material1 = (Uczen) iterator.next();
			listModelUczniowie.addElement(material1);
		}
		if(listUczniow!=null)
			listUczniow.setPreferredSize(new Dimension(300, listUczniow.getFixedCellHeight()*listModelUczniowie.size()));
	}
	private void readPzedmioty(){
		List<Przedmiot> lista1 = DBservice.getListaPrzedmiotow();
		listModelPrzedmioty.removeAllElements();
		for (Iterator iterator = lista1.iterator(); iterator.hasNext();) {
			Przedmiot material1 = (Przedmiot) iterator.next();
			listModelPrzedmioty.addElement(material1);
		}
		if(listPrzedmioty!=null)
			listPrzedmioty.setPreferredSize(new Dimension(300, listPrzedmioty.getFixedCellHeight()*listModelPrzedmioty.size()));

	}

	public JList getListKlas() {
		return listKlas;
	}
	public JList getListUczniowKlasy() {
		return listUczniowKlasy;
	}
	public JList getListNauczycieli() {
		return listNauczycieli;
	}

	public JList getListUczniow() {
		return listUczniow;
	}

	public JList getListPrzedmioty() {
		return listPrzedmioty;
	}

	public JList getListPrzedmiotyKlasy() {
		return listPrzedmiotyKlasy;
	}

	public JList getListNauczycielePrzedmiotuKlasy() {
		return listNauczycielePrzedmiotuKlasy;
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	public JScrollPane getScrollPaneUczniowieKlasy() {
		return scrollPane;
	}
}
