package sysedu.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class OcenyPolski extends JPanel {

	/**
	 * Create the panel.
	 */
	public OcenyPolski() {
		setLayout(null);
		
		JLabel lblJzykPolski = new JLabel("Sprawdziany");
		lblJzykPolski.setBounds(23, 65, 92, 31);
		add(lblJzykPolski);
		
		JLabel lblJzykAngielski = new JLabel("Kartk\u00F3wki");
		lblJzykAngielski.setBounds(23, 107, 92, 31);
		add(lblJzykAngielski);
		
		JLabel lblMatematyka = new JLabel("Prace Domowe");
		lblMatematyka.setBounds(23, 149, 92, 31);
		add(lblMatematyka);
		
		JLabel lblOcena = new JLabel("4, 5, 3, 4");
		lblOcena.setBounds(125, 73, 315, 14);
		add(lblOcena);
		
		JLabel label = new JLabel("2, 2, 6");
		label.setBounds(125, 115, 315, 14);
		add(label);
		
		JLabel label_1 = new JLabel("6, 6, 4, 5, 5");
		label_1.setBounds(125, 157, 315, 14);
		add(label_1);
		
		JLabel lblPrzedmiot = new JLabel("PRZEDMIOT:");
		lblPrzedmiot.setBounds(10, 11, 92, 14);
		add(lblPrzedmiot);
		
		JLabel lblJ = new JLabel("J\u0119zyk Polski");
		lblJ.setBounds(89, 11, 97, 14);
		add(lblJ);

	}
}
