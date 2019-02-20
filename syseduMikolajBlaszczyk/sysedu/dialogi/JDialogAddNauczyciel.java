package sysedu.dialogi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sysedu.domain.Nauczyciel;
import sysedu.domain.Oddzial;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JDialogAddNauczyciel extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Nauczyciel created = null;
	private JTextField textFieldImie;
	private JTextField textFieldNazwisko;

	public Nauczyciel getCreated() {
		return created;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDialogAddNauczyciel dialog = new JDialogAddNauczyciel();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JDialogAddNauczyciel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogAddNauczyciel.class.getResource("/resources/gdynia-herb.png")));
		setModal(true);
		setBounds(100, 100, 348, 219);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textFieldImie = new JTextField();
		textFieldImie.setBounds(100, 8, 169, 20);
		contentPanel.add(textFieldImie);
		textFieldImie.setColumns(10);
		
		textFieldNazwisko = new JTextField();
		textFieldNazwisko.setBounds(100, 39, 169, 20);
		contentPanel.add(textFieldNazwisko);
		textFieldNazwisko.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Imie: ");
		lblNewLabel.setBounds(10, 11, 80, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nazwisko:");
		lblNewLabel_1.setBounds(10, 42, 80, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblHaso = new JLabel("Has\u0142o: ");
		lblHaso.setBounds(10, 75, 46, 14);
		contentPanel.add(lblHaso);
		
		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setBounds(10, 104, 46, 14);
		contentPanel.add(lblLogin);
		
		JLabel lblToPierwszaLitera = new JLabel("To pierwsza litera imienia + nazwisko");
		lblToPierwszaLitera.setBounds(101, 104, 221, 14);
		contentPanel.add(lblToPierwszaLitera);
		
		JLabel lblJestGenerowaneAutomatycznie = new JLabel("Jest generowane automatycznie");
		lblJestGenerowaneAutomatycznie.setBounds(100, 75, 222, 14);
		contentPanel.add(lblJestGenerowaneAutomatycznie);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.PINK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addNauczyciel();
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
	protected void addNauczyciel() {
		created=new Nauczyciel(getTextFieldImie().getText(), getTextFieldNazwisko().getText());
		dispose();
	}
	
	public JTextField getTextFieldImie() {
		return textFieldImie;
	}
	public JTextField getTextFieldNazwisko() {
		return textFieldNazwisko;
	}

}
