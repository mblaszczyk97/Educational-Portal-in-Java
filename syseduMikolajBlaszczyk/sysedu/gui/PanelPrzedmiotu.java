package sysedu.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTabbedPane;

import sysedu.db.DBservice;
import sysedu.domain.Materialy;
import sysedu.domain.Ocena;
import sysedu.domain.Przedmiot;
import sysedu.domain.Sprawdziany;
import sysedu.domain.Uczen;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;

public class PanelPrzedmiotu extends JPanel {
	private Uczen uczen;
	private Przedmiot przedmiot;
	private JLabel lblOceny;
	
	DefaultListModel<Materialy> materialyListModel = new DefaultListModel<Materialy>();
	DefaultListModel<Sprawdziany> sprawdzianyListModel = new DefaultListModel<Sprawdziany>();
	private JList listMaterialy;
	private JList listSprawdziany;

	/**
	 * Create the panel.
	 */
	public PanelPrzedmiotu() {
		setBackground(Color.PINK);
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.BLACK);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelOceny = new JPanel();
		panelOceny.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Oceny", null, panelOceny, null);
		panelOceny.setLayout(new BorderLayout(0, 0));
		
		lblOceny = new JLabel("<html><b>pogrubione</b><i> kursywa</i></html>");
		lblOceny.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelOceny.add(lblOceny, BorderLayout.NORTH);
		
		JPanel panelMaterialy = new JPanel();
		panelMaterialy.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Materia\u0142y", null, panelMaterialy, null);
		panelMaterialy.setLayout(new BorderLayout(0, 0));
		
		/**
		 * Zape³niamy listê materia³ami i po klikniêciu
		 * na dany materia³ wykonujemy akcje otwarcia nowego okna
		 * z tym materia³em metoda: selectMaterialy()
		 */
		listMaterialy = new JList(materialyListModel);
		listMaterialy.setBackground(Color.LIGHT_GRAY);
		listMaterialy.setFont(new Font("Tahoma", Font.BOLD, 16));
		listMaterialy.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					selectMaterialy();
			}
		});
		listMaterialy.setPreferredSize(new Dimension(100, 100));
		listMaterialy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelMaterialy.add(listMaterialy);
		
	
		
		JPanel panelSprawdziany = new JPanel();
		panelSprawdziany.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Sprawdziany", null, panelSprawdziany, null);
		panelSprawdziany.setLayout(new BorderLayout(0, 0));
		
		listSprawdziany = new JList(sprawdzianyListModel);
		listSprawdziany.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					selectSprawdziany();
			}
		});
		listSprawdziany.setBackground(Color.LIGHT_GRAY);
		listSprawdziany.setFont(new Font("Tahoma", Font.BOLD, 16));
		listSprawdziany.setSize(new Dimension(300, 0));
		panelSprawdziany.add(listSprawdziany, BorderLayout.CENTER);

	}
	
	/**
	 * S³u¿y do otwarcia okienka z danym sprawdzianem
	 * przekazujemy mu scie¿kê URL dziêki czemu okno wie
	 * czy dokument jest na serwerze czy lokalnie
	 */
	protected void selectSprawdziany() {
		if (getListSprawdziany().getSelectedValue() != null) {
			PDFczytaj pdf = new PDFczytaj();
			pdf.setMaterialy(((Sprawdziany)getListSprawdziany().getSelectedValue()).getUrl());
			pdf.setVisible(true);
			pdf.dispose();
		}
		
	}

	/**
	 * S³u¿y do otwarcia okienka z danym pdfem
	 * przekazujemy mu scie¿kê URL dziêki czemu okno wie
	 * czy dokument jest na serwerze czy lokalnie
	 */
	protected void selectMaterialy() {
		if (getListMaterialy().getSelectedValue() != null) {
			PDFczytaj pdf = new PDFczytaj();
			pdf.setMaterialy(((Materialy)getListMaterialy().getSelectedValue()).getUrl());
			pdf.setVisible(true);
			pdf.dispose();
		}
		
	}

	public Uczen getUczen() {
		return uczen;
	}

	public void setUczen(Uczen uczen) {
		this.uczen = uczen;
	}

	public Przedmiot getPrzedmiot() {
		return przedmiot;
	}

	public void setPrzedmiot(Przedmiot przedmiot) {
		this.przedmiot = przedmiot;
	}
	
	/**
	 * Wypisujemy tu z listy oceny ucznia
	 * @uczen - obiekt ucznia którego oceny wypisujemy
	 * @przedmiot - obiekt przedmiot dla którego wypisujemy
	 */
	public void setUczenPrzedmiot(Uczen uczen, Przedmiot przedmiot) {
		List<Ocena> lista = DBservice.getOceny(przedmiot, uczen);
		StringBuffer napis = new StringBuffer("<html>");
		napis.append("<br>");
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Ocena ocena = (Ocena) iterator.next();
			if(ocena.getTyp().equals("S")) {
				napis.append(" &emsp; ").append("<b>").append("<font color=\"red\">").append(ocena.getOcena()).append("</font>").append("</b>").append(" ").append(" | ").append(ocena.getKomentarz()).append("<br>");
			}else if(ocena.getTyp().equals("P")){
				napis.append(" &emsp; ").append("<b>").append(ocena.getOcena()).append("</b>").append(" ").append(" | ").append(ocena.getKomentarz()).append("<br>");
			}else if(ocena.getTyp().equals("K")){
				napis.append(" &emsp; ").append("<b>").append("<font color=\"blue\">").append(ocena.getOcena()).append("</font>").append("</b>").append(" ").append(" | ").append(ocena.getKomentarz()).append("<br>");

			}
		}
		napis.append("</html>");
		getLblOceny().setText(napis.toString());
		
		
	}
	
	/**
	 * Wypisujemy tu z listy materia³y dla danego przedmiotu
	 * @przedmiot - przedmiot dla którego pobieramy materia³y
	 */
	public void setMaterialy(Przedmiot przedmiot) {
		List<Materialy> lista1 = DBservice.getMaterialy(przedmiot);
		materialyListModel.removeAllElements();
		for (Iterator iterator = lista1.iterator(); iterator.hasNext();) {
			Materialy material1 = (Materialy) iterator.next();
			materialyListModel.addElement(material1);
		}		
	}
	
	public void setSprawdziany(Przedmiot przedmiot) {
		List<Sprawdziany> lista1 = DBservice.getSprawdziany(przedmiot);
		sprawdzianyListModel.removeAllElements();
		for (Iterator iterator = lista1.iterator(); iterator.hasNext();) {
			Sprawdziany material1 = (Sprawdziany) iterator.next();
			sprawdzianyListModel.addElement(material1);
		}	
	}
	
	public JLabel getLblOceny() {
		return lblOceny;
	}
		
	public JList getListMaterialy() {
		return listMaterialy;
	}
	
	public JList getListSprawdziany() {
		return listSprawdziany;
	}
}
