package sysedu.dialogi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;

import sysedu.domain.Ocena;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;

public class JDialodAddOcena extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldOcena;
	private JTextField textFieldTyp;
	private JTextArea textAreaKomentarz;
	
	private Ocena created = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDialodAddOcena dialog = new JDialodAddOcena();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JDialodAddOcena() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialodAddOcena.class.getResource("/resources/gdynia-herb.png")));
		getContentPane().setBackground(Color.PINK);
		setBackground(Color.PINK);
		setModal(true);
		setTitle("Dodawanie oceny");
		setBounds(100, 100, 168, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			textAreaKomentarz = new JTextArea();
			textAreaKomentarz.setBorder(new TitledBorder(null, "Komentarz", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			textAreaKomentarz.setColumns(60);
			contentPanel.add(textAreaKomentarz, BorderLayout.CENTER);
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.PINK);
			panel.setPreferredSize(new Dimension(10, 60));
			contentPanel.add(panel, BorderLayout.NORTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{40, 86, 0};
			gbl_panel.rowHeights = new int[]{25, 25, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblOcena = new JLabel("Ocena:");
				lblOcena.setPreferredSize(new Dimension(40, 25));
				GridBagConstraints gbc_lblOcena = new GridBagConstraints();
				gbc_lblOcena.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblOcena.insets = new Insets(0, 0, 5, 5);
				gbc_lblOcena.gridx = 0;
				gbc_lblOcena.gridy = 0;
				panel.add(lblOcena, gbc_lblOcena);
			}
			{
				textFieldOcena = new JTextField();
				GridBagConstraints gbc_textFieldOcena = new GridBagConstraints();
				gbc_textFieldOcena.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldOcena.anchor = GridBagConstraints.WEST;
				gbc_textFieldOcena.insets = new Insets(0, 0, 5, 0);
				gbc_textFieldOcena.gridx = 1;
				gbc_textFieldOcena.gridy = 0;
				panel.add(textFieldOcena, gbc_textFieldOcena);
				textFieldOcena.setColumns(10);
			}
			{
				JLabel lblTyp = new JLabel("Typ: ");
				lblTyp.setPreferredSize(new Dimension(40, 25));
				GridBagConstraints gbc_lblTyp = new GridBagConstraints();
				gbc_lblTyp.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblTyp.insets = new Insets(0, 0, 0, 5);
				gbc_lblTyp.gridx = 0;
				gbc_lblTyp.gridy = 1;
				panel.add(lblTyp, gbc_lblTyp);
			}
			{
				textFieldTyp = new JTextField();
				GridBagConstraints gbc_textFieldTyp = new GridBagConstraints();
				gbc_textFieldTyp.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldTyp.anchor = GridBagConstraints.WEST;
				gbc_textFieldTyp.gridx = 1;
				gbc_textFieldTyp.gridy = 1;
				panel.add(textFieldTyp, gbc_textFieldTyp);
				textFieldTyp.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.PINK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						createOcena();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Anuluj");
				cancelButton.setBackground(Color.LIGHT_GRAY);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						created = null;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public static boolean isNumeric(String strNum) {
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}

	protected void createOcena() {

		if(isNumeric(getTextFieldOcena().getText())) {
			created = new Ocena((Math.round(Float.valueOf(getTextFieldOcena().getText()))), getTextFieldTyp().getText(), getTextAreaKomentarz().getText());
		}else {
			created = new Ocena(24, getTextFieldTyp().getText(), getTextAreaKomentarz().getText());
		}
		dispose();

	}

	public JTextField getTextFieldOcena() {
		return textFieldOcena;
	}
	public JTextField getTextFieldTyp() {
		return textFieldTyp;
	}
	public JTextArea getTextAreaKomentarz() {
		return textAreaKomentarz;
	}

	public Ocena getCreated() {
		return created;
	}
	
	
}
