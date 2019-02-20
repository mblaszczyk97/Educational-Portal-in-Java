package sysedu.dialogi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sysedu.domain.Oddzial;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;

public class JDialogAddKlasa extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Oddzial created = null;
	private JTextField textFieldNazwaKlasy;
	private JTextField textFieldRokKlasy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDialogAddKlasa dialog = new JDialogAddKlasa();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JDialogAddKlasa() {
		setBackground(Color.PINK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogAddKlasa.class.getResource("/resources/gdynia-herb.png")));
		setModal(true);
		setBounds(100, 100, 251, 157);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textFieldNazwaKlasy = new JTextField();
			textFieldNazwaKlasy.setBounds(66, 11, 156, 20);
			contentPanel.add(textFieldNazwaKlasy);
			textFieldNazwaKlasy.setColumns(10);
		}
		{
			textFieldRokKlasy = new JTextField();
			textFieldRokKlasy.setBounds(66, 42, 156, 20);
			contentPanel.add(textFieldRokKlasy);
			textFieldRokKlasy.setColumns(10);
		}
		{
			JLabel lblNazwa = new JLabel("Nazwa:");
			lblNazwa.setBounds(10, 14, 46, 14);
			contentPanel.add(lblNazwa);
		}
		{
			JLabel lblRok = new JLabel("Rok:");
			lblRok.setBounds(10, 45, 46, 14);
			contentPanel.add(lblRok);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.PINK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addKlasa();
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
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	
	protected void addKlasa() {
		created=new Oddzial(getTextFieldNazwaKlasy().getText(), getTextFieldRokKlasy().getText());
		dispose();
	}

	public JTextField getTextFieldNazwaKlasy() {
		return textFieldNazwaKlasy;
	}
	public JTextField getTextFieldRokKlasy() {
		return textFieldRokKlasy;
	}

	public Oddzial getCreated() {
		return created;
	}
	
}
