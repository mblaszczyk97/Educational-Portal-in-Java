package sysedu.dialogi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sysedu.db.DBservice;
import sysedu.domain.Materialy;
import sysedu.domain.Sprawdziany;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class JDialogAddSprawdzian extends JDialog {
	private int przedmiotID; 
	private JTextField nazwa;
	private JTextField zrodlo;
	private Sprawdziany sprawdzian;

	public Sprawdziany getSprawdzian() {
		return sprawdzian;
	}

	public void setPrzedmiotID(int przedmiotID) {
		this.przedmiotID = przedmiotID;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDialogAddSprawdzian dialog = new JDialogAddSprawdzian();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JDialogAddSprawdzian() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogAddSprawdzian.class.getResource("/resources/gdynia-herb.png")));
		setModal(true);
		setBounds(100, 100, 344, 200);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBackground(Color.PINK);
		panel.setBounds(0, 0, 329, 126);
		getContentPane().add(panel);
		
		JLabel label = new JLabel("Nazwa: ");
		label.setBounds(33, 36, 46, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u0179r\u00F3d\u0142o:");
		label_1.setBounds(33, 72, 46, 14);
		panel.add(label_1);
		
		nazwa = new JTextField();
		nazwa.setColumns(10);
		nazwa.setBounds(89, 33, 230, 20);
		panel.add(nazwa);
		
		zrodlo = new JTextField();
		zrodlo.setColumns(10);
		zrodlo.setBounds(89, 69, 230, 20);
		panel.add(zrodlo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.PINK);
		panel_1.setBounds(0, 122, 329, 39);
		getContentPane().add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSprawdzian();
			}
		});
		button.setActionCommand("OK");
		panel_1.add(button);
		
		JButton button_1 = new JButton("Anuluj");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sprawdzian=null;
				dispose();
			}
		});
		button_1.setActionCommand("Cancel");
		panel_1.add(button_1);
	}

	protected void createSprawdzian() {
		sprawdzian = new Sprawdziany(getZrodloText().getText(), getNazwaText().getText(), przedmiotID);
		
		if(DBservice.dodajSprawdzian(sprawdzian)) {
			JOptionPane.showMessageDialog(this,"Dodano", "Dodano materia³", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this,"B³¹d", "B³¹d dodania", JOptionPane.ERROR_MESSAGE);
		}
		dispose();
		
	}

	public JTextField getNazwaText() {
		return nazwa;
	}
	public JTextField getZrodloText() {
		return zrodlo;
	}
}
