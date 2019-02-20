package sysedu.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.NSOption;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;

public class PDFczytaj extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JWebBrowser webBrowser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PDFczytaj dialog = new PDFczytaj();
			dialog.initialize();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		getWebBrowser().setLocationBarVisible(false);
		getWebBrowser().setButtonBarVisible(false);
		getWebBrowser().setMenuBarVisible(false);
		getWebBrowser().setStatusBarVisible(true);
	}
	
	public void setMaterialy(String zrodlo) {
		getWebBrowser().setLocationBarVisible(false);
		getWebBrowser().setButtonBarVisible(false);
		getWebBrowser().setMenuBarVisible(false);
		getWebBrowser().setStatusBarVisible(true);
		getWebBrowser().navigate(zrodlo);
	}

	public void setSprawdziany(String url) {
		getWebBrowser().setLocationBarVisible(false);
		getWebBrowser().setButtonBarVisible(false);
		getWebBrowser().setMenuBarVisible(false);
		getWebBrowser().setStatusBarVisible(true);
		getWebBrowser().navigate(url);
	}

	/**
	 * Nasze okno inicjalizuje siê i dodaje do panelu przegl¹darkê materia³ów
	 */
	public PDFczytaj() {
		getContentPane().setBackground(Color.PINK);
		getContentPane().setForeground(Color.PINK);
		setBackground(Color.PINK);
		setForeground(Color.RED);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PDFczytaj.class.getResource("/resources/gdynia-herb.png")));
		setModal(true);
		setBounds(100, 100, 1085, 909);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setForeground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			webBrowser = new JWebBrowser();
			webBrowser.setBackground(Color.PINK);
			contentPanel.add(webBrowser);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.PINK);
			buttonPane.setForeground(Color.PINK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Zamknij");
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	protected void closeDialog() {
		this.dispose();
		
	}

	public JWebBrowser getWebBrowser() {
		
		return webBrowser;
	}
}
