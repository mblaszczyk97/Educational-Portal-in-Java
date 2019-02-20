package sysedu.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import sysedu.db.DBservice;
import sysedu.dialogi.JDialodAddOcena;
import sysedu.dialogi.JDialodEditOcena;
import sysedu.dialogi.JDialogAddMaterial;
import sysedu.dialogi.JDialogAddSprawdzian;
import sysedu.domain.Materialy;
import sysedu.domain.Nauczyciel;
import sysedu.domain.Ocena;
import sysedu.domain.Przedmiot;
import sysedu.domain.PrzedmiotOddzial;
import sysedu.domain.Sprawdziany;
import sysedu.domain.Uczen;
import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import java.io.LineNumberInputStream;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JScrollBar;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.JScrollPane;

public class PanelNauczyciela extends JFrame {
	private JPanel contentPane;

	private Nauczyciel nauczyciel = null;
	
	DefaultListModel<Sprawdziany> ListModelSprawdziany = new DefaultListModel<Sprawdziany>();
	DefaultListModel<Materialy> ListModelMaterialy = new DefaultListModel<Materialy>();
	private DefaultListModel<Uczen> listModelUczniowie = new DefaultListModel<Uczen>();
	private DefaultListModel<Ocena> listModelOceny = new DefaultListModel<Ocena>();
	private JList listaPrzedmiotow;
	private JList listaUczniow_1;
	private JList listOceny;
	private JPanel panel_2;
	
	private PrzedmiotOddzial selectedPrzedmiotOddzial;
	private Ocena selectedOcena = null;
	private Uczen selectedUczen = null;
	private Materialy selectedMaterial = null;
	private Sprawdziany selectedSprawdzian = null;
	private JPopupMenu popupMenuOceny;
	private JMenuItem mntmDodajOcene;
	private JMenuItem mntmZmieOcene;
	private JMenuItem mntmUsuOcene;
	private JLabel lblNewLabel;
	private JList listMaterialy;
	private JPopupMenu popupMenuMaterialy;
	private JMenuItem mntmWywietlMaterial;
	private JMenuItem mntmDodajMaterial;
	private JMenuItem mntmUsuMaterial;
	private JList listSprawdziany;
	private JPanel panel_3;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	private JLabel label;
	
	/**
	 * Uruchom.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelNauczyciela frame = new PanelNauczyciela(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public Nauczyciel getNauczyciel() {
		return nauczyciel;
	}


	public void setNauczyciel(Nauczyciel nauczyciel) {
		this.nauczyciel = nauczyciel;
		this.setTitle("Panel Nauczyciela: "+nauczyciel.getNazwisko()+" "+nauczyciel.getImie());
		DBservice.getPrzedmiotOddzial(nauczyciel);
		
	}


	/**
	 * Tworzymy ramkê.
	 */
	public PanelNauczyciela(Nauczyciel nauczyciel) {
		setBackground(Color.PINK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PanelNauczyciela.class.getResource("/resources/gdynia-herb.png")));
		setNauczyciel(nauczyciel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500,280, 1280,720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(15, 15));
		setContentPane(contentPane);
		
		/**
		 * £adujemy do listy wszystkie przedmioty które
		 * naucza nauczyciel uwzglêdniaj¹c przy tym ¿e mo¿e
		 * uczyæ tego samego przedmiotu ró¿nych klas
		 */
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setPreferredSize(new Dimension(10, 120));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PanelNauczyciela.class.getResource("/resources/gdynia-herb.png")));
		lblNewLabel.setPreferredSize(new Dimension(128, 90));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		label = new JLabel("SYSEDU");
		label.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 45));
		panel.add(label, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(15, 15));
		
		/**
		 * Po wyborze przedmiotu ³adujemy wszystkich uczniów
		 * z danej klasy którzy uczêszczaj¹ na ten przedmiot
		 */
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_1.add(panel_2, BorderLayout.CENTER);
		
		
		/**
		 * Po wyborze ucznia wyœwietlamy wszystkie jego oceny które zdoby³
		 */
		panel_2.setLayout(new BorderLayout(10, 10));
		
		/**
		 * Menu typu popup które po klikniêciu prawym przyciskiem myszy
		 * na liste ocen daje mo¿liwoœæ 3 opcji: dodaj, zmieñ, usuñ
		 */
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setPreferredSize(new Dimension(300, 10));
		panel_2.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 10, 10));
		
		
		/**
		 * £adujemy do listy wszystkie materia³y które
		 * doda³ nauczyciel, a pózniej tworzymy przyciski
		 * do otwierania, dodawania lub usuwania materia³ów
		 */
		
		/**
		 * £adujemy do listy wszystkie sprawdziany które
		 * doda³ nauczyciel, a pózniej tworzymy przyciski
		 * do otwierania, dodawania lub usuwania materia³ów
		 */
		
		scrollPane_4 = new JScrollPane();
		panel_3.add(scrollPane_4);
		listMaterialy = new JList(ListModelMaterialy);
		scrollPane_4.setViewportView(listMaterialy);
		listMaterialy.setFixedCellHeight(25);
		listMaterialy.setPreferredSize(new Dimension(0, 200));
		listMaterialy.setBackground(Color.WHITE);
		listMaterialy.setFont(new Font("Tahoma", Font.BOLD, 14));
		listMaterialy.setBorder(new TitledBorder(null, "Materia\u0142y", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		listMaterialy.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedMaterial();
			}
		});
		
		popupMenuMaterialy = new JPopupMenu();
		addPopup(listMaterialy, popupMenuMaterialy);
		
		mntmWywietlMaterial = new JMenuItem("Wy\u015Bwietl");
		mntmWywietlMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wyswietlMaterial();
			}
		});
		popupMenuMaterialy.add(mntmWywietlMaterial);
		
		mntmDodajMaterial = new JMenuItem("Dodaj");
		mntmDodajMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMaterial();
			}
		});
		popupMenuMaterialy.add(mntmDodajMaterial);
		
		mntmUsuMaterial = new JMenuItem("Usu\u0144");
		mntmUsuMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunMaterial();
			}
		});
		popupMenuMaterialy.add(mntmUsuMaterial);
		
		scrollPane_3 = new JScrollPane();
		panel_3.add(scrollPane_3);
		listSprawdziany = new JList(ListModelSprawdziany);
		scrollPane_3.setViewportView(listSprawdziany);
		listSprawdziany.setFixedCellHeight(25);
		listSprawdziany.setPreferredSize(new Dimension(0, 300));
		listSprawdziany.setFont(new Font("Tahoma", Font.BOLD, 14));
		listSprawdziany.setBackground(Color.WHITE);
		listSprawdziany.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Sprawdziany", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		listSprawdziany.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedSprawdzian();
			}
		});
		
		JPopupMenu popupMenuSprawdziany = new JPopupMenu();
		addPopup(listSprawdziany, popupMenuSprawdziany);
		
		JMenuItem mntmWywietlSprawdzian = new JMenuItem("Wy\u015Bwietl");
		mntmWywietlSprawdzian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wyswietlSprawdzian();
			}
		});
		popupMenuSprawdziany.add(mntmWywietlSprawdzian);
		
		JMenuItem mntmDodajSprawdzian = new JMenuItem("Dodaj");
		mntmDodajSprawdzian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSprawdzian();
			}
		});
		popupMenuSprawdziany.add(mntmDodajSprawdzian);
		
		JMenuItem mntmUsuSprawdzian = new JMenuItem("Usu\u0144");
		mntmUsuSprawdzian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunSprawdzian();
			}
		});
		popupMenuSprawdziany.add(mntmUsuSprawdzian);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setPreferredSize(new Dimension(250, 100));
		panel_2.add(scrollPane_2, BorderLayout.WEST);
		listOceny = new JList(listModelOceny);
		listOceny.setFixedCellHeight(25);
		listOceny.setPreferredSize(new Dimension(250, listOceny.getFixedCellHeight()*listModelOceny.size()));
		scrollPane_2.setViewportView(listOceny);
		listOceny.setBackground(Color.WHITE);
		listOceny.setFont(new Font("Tahoma", Font.BOLD, 12));
		listOceny.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedOcena();
			}
		});
		listOceny.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Oceny Ucznia", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		popupMenuOceny = new JPopupMenu();
		addPopup(listOceny, popupMenuOceny);
		
		mntmDodajOcene = new JMenuItem("Dodaj");
		mntmDodajOcene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addOcena();
			}
		});
		popupMenuOceny.add(mntmDodajOcene);
		
		mntmZmieOcene = new JMenuItem("Zmie\u0144");
		mntmZmieOcene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editOcena();
			}
		});
		popupMenuOceny.add(mntmZmieOcene);
		
		mntmUsuOcene = new JMenuItem("Usu\u0144");
		mntmUsuOcene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunOcena();
			}
		});
		popupMenuOceny.add(mntmUsuOcene);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(250, 100));
		panel_1.add(scrollPane_1, BorderLayout.WEST);
		listaUczniow_1 = new JList(listModelUczniowie);
		listaUczniow_1.setFixedCellHeight(25);
		
		scrollPane_1.setViewportView(listaUczniow_1);
		listaUczniow_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		listaUczniow_1.setBackground(Color.WHITE);
		listaUczniow_1.setValueIsAdjusting(true);
		listaUczniow_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		listaUczniow_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedUczen();
			}
		});
		listaUczniow_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Uczniowie", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.WEST);
		listaPrzedmiotow = new JList(nauczyciel.getPrzedmioty().toArray());
		listaPrzedmiotow.setFixedCellHeight(25);

		scrollPane.setPreferredSize(new Dimension(250, listaPrzedmiotow.getFixedCellHeight()*nauczyciel.getPrzedmioty().size()));
		scrollPane.setViewportView(listaPrzedmiotow);
		listaPrzedmiotow.setBackground(Color.WHITE);
		listaPrzedmiotow.setFont(new Font("Tahoma", Font.BOLD, 16));
		listaPrzedmiotow.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedPrzedmiot();
			}
		});
		listaPrzedmiotow.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Przedmioty", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		listaPrzedmiotow.setPreferredSize(new Dimension(250, 300));
		
		
		
	}

	/**
	 * Metoda dodania sprawdzianu
	 * odwo³ujemy siê do JDialogu w którym ju¿ idzie operacja
	 * do bazy danych
	 */
	protected void addSprawdzian() {
		JDialogAddSprawdzian dialog = new JDialogAddSprawdzian();
		dialog.setPrzedmiotID(selectedPrzedmiotOddzial.getPrzedmiotID());
		dialog.setVisible(true);
		if (dialog.getSprawdzian() != null) {
			Sprawdziany sprawdzian = dialog.getSprawdzian();
			ListModelSprawdziany.addElement(sprawdzian);
		}
	}

	/**
	 * Metoda dodania materia³u
	 * odwo³ujemy siê do JDialogu w którym ju¿ idzie operacja
	 * do bazy danych
	 */
	protected void addMaterial() {
		
		JDialogAddMaterial dialog = new JDialogAddMaterial();
		dialog.setPrzedmiotID(selectedPrzedmiotOddzial.getPrzedmiotID());
		dialog.setVisible(true);
		if (dialog.getMaterial() != null) {
			Materialy material = dialog.getMaterial();
			ListModelMaterialy.addElement(material);
		}
		
	}

	/**
	 * Metoda wyœwietlenia materia³u
	 * odwo³ujemy siê do JDialogu w którym ju¿ idzie operacja
	 * do bazy danych
	 */
	protected void wyswietlMaterial() {
		if (getListMaterialy().getSelectedValue() != null) {
			System.out.println("Selected:"+((Materialy)getListMaterialy().getSelectedValue()).getUrl());
			PDFczytaj pdf = new PDFczytaj();
			pdf.setMaterialy(((Materialy)getListMaterialy().getSelectedValue()).getUrl());
			pdf.setVisible(true);
			pdf.dispose();
		}
	}

	/**
	 * Metoda wyœwietlenia sprawdzianu
	 * odwo³ujemy siê do czytnika PDFów który otwiera
	 * nam dany materia³ w nowym oknie
	 */
	protected void wyswietlSprawdzian() {
		if (getListSprawdziany().getSelectedValue() != null) {
			System.out.println("Selected:"+((Sprawdziany)getListSprawdziany().getSelectedValue()).getUrl());
			PDFczytaj pdf = new PDFczytaj();
			pdf.setSprawdziany(((Sprawdziany)getListSprawdziany().getSelectedValue()).getUrl());
			pdf.setVisible(true);
			pdf.dispose();
		}
		
	}

	/**
	 * Metoda usuniêcia materia³u
	 * odwo³ujemy siê do JDialogu w którym ju¿ idzie operacja
	 * do bazy danych usuwamy tutaj tak¿e z listy interrfejsu
	 */
	protected void usunMaterial() {
		if(getListMaterialy().getSelectedValue()!=null)
		{
		if (JOptionPane.showConfirmDialog(this, "Czy napewno usun¹æ Materia³?", "Potwierdzenie", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
			if(DBservice.usunMaterial((Materialy)getListMaterialy().getSelectedValue())) {
				JOptionPane.showMessageDialog(this, "Usuniêto Materia³: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				ListModelMaterialy.removeElementAt(getListMaterialy().getSelectedIndex());
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Materia³u. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Metoda usuniêcia sprawdzianu
	 * odwo³ujemy siê do JDialogu w którym ju¿ idzie operacja
	 * do bazy danych usuwamy tutaj tak¿e z listy interrfejsu
	 */
	protected void usunSprawdzian() {
		if(getListSprawdziany().getSelectedValue()!=null)
		{
		if (JOptionPane.showConfirmDialog(this, "Czy napewno usun¹æ Sprawdzian?", "Potwierdzenie", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
			if(DBservice.usunSprawdzian((Sprawdziany)getListSprawdziany().getSelectedValue())) {
				JOptionPane.showMessageDialog(this, "Usuniêto Sprawdzian: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				ListModelSprawdziany.removeElementAt(getListSprawdziany().getSelectedIndex());
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d usuwania Sprawdzianu. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	/**
	 * Usuwamy dan¹ ocenê z listy i z bazy danych
	 * Zawsze mo¿na siê wycofaæ i anulowaæ akcje
	 * przy braku po³¹czenia lub innym problemie dostajemy okienko b³êdu
	 */
	protected void usunOcena() {
		if(getListOcenyUcznia().getSelectedValue()!=null) {
		if (JOptionPane.showConfirmDialog(this, "Czy napewno usun¹æ ocenê?", "Potwierdzenie", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
		if(DBservice.usunOceneUcznia((Ocena)getListOcenyUcznia().getSelectedValue())) {
			JOptionPane.showMessageDialog(this, "Usuniêto ocenê: ", "Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
			listModelOceny.removeElementAt(getListOcenyUcznia().getSelectedIndex());
		}else {
			JOptionPane.showMessageDialog(this, "B³¹d usuwania oceny. ", "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		}
	}
	
	/**
	 * Edycja oceny dzia³a po zaznaczeniu danej oceny i klikniêciu prawego przycisku na niej
	 * otwiera okno gdzie wpisujemy parametry do zmian i potwierdzamy b¹dz
	 * wycofujemy siê nic nie zmieniaj¹c
	 */
	protected void editOcena() {
		JDialodEditOcena dilogOcena = new JDialodEditOcena();
		dilogOcena.setEdited((Ocena)getListOcenyUcznia().getSelectedValue());
		dilogOcena.setVisible(true);
		if(dilogOcena.getEdited().getOcena()>6 || dilogOcena.getEdited().getOcena()<1)
		{
			JOptionPane.showMessageDialog(this, "Podaj ocenê z zakresu 1-6 ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			selectedUczen();
		}else if (dilogOcena.getEdited() != null) {
			if(DBservice.zamienOceneUcznia(dilogOcena.getEdited())) {
				JOptionPane.showMessageDialog(this, "Zmieniono ocenê: "+ dilogOcena.getEdited().getOcena(),"Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}


	/**
	 * Dzia³a podobnie do Edycji Oceny z tym wyj¹tkiem ¿e bez znaczenia
	 * co zaznaczymy i tak po klikniêciu dodaj nasza ocena zostanie dodana
	 * do listy i bazy danych
	 */
	protected void addOcena() {
		// TODO Auto-generated method stub
		JDialodAddOcena dilogOcena = new JDialodAddOcena();
		dilogOcena.setVisible(true);
		if(dilogOcena.getCreated().getOcena()>6 || dilogOcena.getCreated().getOcena()<1)
		{
			JOptionPane.showMessageDialog(this, "Podaj ocenê z zakresu 1-6 ", "B³¹d", JOptionPane.ERROR_MESSAGE);
		}else if (dilogOcena.getCreated() != null) {
			Ocena ocena = dilogOcena.getCreated();
			ocena.setIdPrzedmiot(selectedPrzedmiotOddzial.getPrzedmiotID());
			ocena.setIdUczen(selectedUczen.getId());
			if(DBservice.dodajOceneUcznia(ocena)) {
				JOptionPane.showMessageDialog(this, "Wstawiono ocenê: "+ ocena.getOcena(),"Potwierdzenie",  JOptionPane.INFORMATION_MESSAGE );
				listModelOceny.addElement(ocena);
			}else {
				JOptionPane.showMessageDialog(this, "B³¹d wstawiania ", "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}

	
	/**
	 * Metody selected s³u¿a do pobrania informacji do list
	 */
	protected void selectedOcena() {
		selectedOcena = (Ocena)getListOcenyUcznia().getSelectedValue();
		
	}

	/**
	 * Metody selected s³u¿a do pobrania informacji do list
	 */
	protected void selectedUczen() {
		selectedUczen = (Uczen) getListaUczniow().getSelectedValue();
		if(selectedUczen!=null) {	
		Przedmiot przedmiot = new Przedmiot(selectedPrzedmiotOddzial.getNazwaOddzialu());
		przedmiot.setId(selectedPrzedmiotOddzial.getPrzedmiotID());
		
		List<Ocena> oceny = DBservice.getOceny(przedmiot, selectedUczen);
		listModelOceny.removeAllElements();
		for (Iterator iterator = oceny.iterator(); iterator.hasNext();) {
			Ocena ocena = (Ocena) iterator.next();
			listModelOceny.addElement(ocena);
			listOceny.setPreferredSize(new Dimension(250, listOceny.getFixedCellHeight()*listModelOceny.size()));
		}
		}else {
			listModelOceny.removeAllElements();
		}
		
	}

	/**
	 * Metody selected s³u¿a do pobrania informacji do list
	 */
	private void selectedPrzedmiot() {
		selectedPrzedmiotOddzial = (PrzedmiotOddzial) getListPrzedmiotyNauczyciela().getSelectedValue();
		List<Uczen> listaUczniow = DBservice.getListaUczniow(selectedPrzedmiotOddzial.getOddzialID());
		listModelUczniowie.removeAllElements();
		for (Iterator iterator = listaUczniow.iterator(); iterator.hasNext();) {
			Uczen uczen = (Uczen) iterator.next();
			listModelUczniowie.addElement(uczen);
			listaUczniow_1.setPreferredSize(new Dimension(250,listaUczniow_1.getFixedCellHeight()*listModelUczniowie.getSize()));
		}

		List<Materialy> listaMaterialow = DBservice.getMaterialy(selectedPrzedmiotOddzial.getNazwaPrzedmiotu());
		ListModelMaterialy.removeAllElements();
		for (Iterator iterator = listaMaterialow.iterator(); iterator.hasNext();) {
			Materialy material = (Materialy) iterator.next();
			ListModelMaterialy.addElement(material);
			listMaterialy.setPreferredSize(new Dimension(400,listMaterialy.getFixedCellHeight()*ListModelMaterialy.getSize()));
		}
		
		List<Sprawdziany> listaSprawdzianow = DBservice.getSprawdziany(selectedPrzedmiotOddzial.getNazwaPrzedmiotu());
		ListModelSprawdziany.removeAllElements();
		for (Iterator iterator = listaSprawdzianow.iterator(); iterator.hasNext();) {
			Sprawdziany sprawdzian = (Sprawdziany) iterator.next();
			ListModelSprawdziany.addElement(sprawdzian);
			listSprawdziany.setPreferredSize(new Dimension(400,listSprawdziany.getFixedCellHeight()*ListModelSprawdziany.getSize()));
		}
		
	}
	
	/**
	 * Metody selected s³u¿¹ do pobrania informacji do list
	 */
	private void selectedMaterial() {
		selectedMaterial = (Materialy) getListMaterialy().getSelectedValue();
	}

	/**
	 * Metody selected s³u¿¹ do pobrania informacji do list
	 */
	protected void selectedSprawdzian() {
		selectedSprawdzian = (Sprawdziany) getListSprawdziany().getSelectedValue();
	}

	private JList getListSprawdziany() {
		return listSprawdziany;
	}
	public JList getListPrzedmiotyNauczyciela() {
		return listaPrzedmiotow;
	}
	public JList getListaUczniow() {
		return listaUczniow_1;
	}
	public JList getListOcenyUcznia() {
		return listOceny;
	}
	public JList getListMaterialy() {
		return listMaterialy;
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
}
