package sysedu.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTabbedPane;

import sysedu.db.DBservice;
import sysedu.domain.Ocena;
import sysedu.domain.Przedmiot;
import sysedu.domain.Uczen;
import javax.swing.JLabel;

public class PanelPrzedmiotu extends JPanel {
	private Uczen uczen;
	private Przedmiot przedmiot;
	private JLabel lblOceny;

	/**
	 * Create the panel.
	 */
	public PanelPrzedmiotu() {
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelOceny = new JPanel();
		tabbedPane.addTab("Oceny", null, panelOceny, null);
		panelOceny.setLayout(new BorderLayout(0, 0));
		
		lblOceny = new JLabel("<html><b>pogrubione</b><i> kursywa</i></html>");
		panelOceny.add(lblOceny);
		
		JPanel panelMaterialy = new JPanel();
		tabbedPane.addTab("Materia\u0142y", null, panelMaterialy, null);
		
		JPanel panelSprawdziany = new JPanel();
		tabbedPane.addTab("Sprawdziany", null, panelSprawdziany, null);

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
	
	public void setUczenPrzedmiot(Uczen uczen, Przedmiot przedmiot) {
		List<Ocena> lista = DBservice.getOceny(przedmiot, uczen);
		StringBuffer napis = new StringBuffer("<html>");
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Ocena ocena = (Ocena) iterator.next();
			napis.append(ocena.getOcena()).append(" ").append(ocena.getTyp()).append(" komentarz: ").append(ocena.getKomentarz()).append("<br>");
		}
		napis.append("</html>");
		getLblOceny().setText(napis.toString());
		
	}

	public JLabel getLblOceny() {
		return lblOceny;
	}
}
