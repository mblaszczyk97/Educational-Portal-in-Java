package sysedu.dialogi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sysedu.db.DBservice;
import sysedu.domain.Materialy;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;

public class JDialogAddMaterial extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nazwaText;
	private JTextField zrodloText;
	private int przedmiotID; 
	private Materialy material;

	public Materialy getMaterial() {
		return material;
	}

	public void setPrzedmiotID(int przedmiotID) {
		this.przedmiotID = przedmiotID;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDialogAddMaterial dialog = new JDialogAddMaterial();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JDialogAddMaterial() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogAddMaterial.class.getResource("/resources/gdynia-herb.png")));
		setForeground(Color.PINK);
		setModal(true);
		setBounds(100, 100, 345, 198);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNazwa = new JLabel("Nazwa: ");
		lblNazwa.setBounds(33, 36, 46, 14);
		contentPanel.add(lblNazwa);
		
		JLabel lblrdo = new JLabel("\u0179r\u00F3d\u0142o:");
		lblrdo.setBounds(33, 72, 46, 14);
		contentPanel.add(lblrdo);
		
		nazwaText = new JTextField();
		nazwaText.setBounds(89, 33, 230, 20);
		contentPanel.add(nazwaText);
		nazwaText.setColumns(10);
		
		zrodloText = new JTextField();
		zrodloText.setBounds(89, 69, 230, 20);
		contentPanel.add(zrodloText);
		zrodloText.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.PINK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						createMaterial();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Anuluj");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						material=null;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void createMaterial() {
		material = new Materialy(getZrodloText().getText(), getNazwaText().getText(), przedmiotID);
		
		if(DBservice.dodajMaterial(material)) {
			JOptionPane.showMessageDialog(this,"Dodano", "Dodano materia³", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this,"B³¹d", "B³¹d dodania", JOptionPane.ERROR_MESSAGE);
		}
		dispose();
		
	}
	public JTextField getNazwaText() {
		return nazwaText;
	}
	public JTextField getZrodloText() {
		return zrodloText;
	}
}
