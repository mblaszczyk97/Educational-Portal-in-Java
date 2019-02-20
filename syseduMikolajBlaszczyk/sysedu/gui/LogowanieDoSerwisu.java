package sysedu.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import sysedu.db.DBservice;
import sysedu.domain.Admin;
import sysedu.domain.Nauczyciel;
import sysedu.domain.Uczen;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Toolkit;

class ImgUtils {

public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
    BufferedImage bi = null;
    try {
        ImageIcon ii = new ImageIcon(filename);
        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    return bi;
}
 }

public class LogowanieDoSerwisu extends JFrame {
	
	private String userTable="u";
	private JFrame frame;
	private JTextField loginField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Uruchomienie.
	 */
	public static void main(String[] args) {
		
		NativeSwing.initialize();
		NativeInterface.open();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogowanieDoSerwisu window = new LogowanieDoSerwisu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Stworzenie, inicjalizacja.
	 */
	public LogowanieDoSerwisu() {
		initialize();
	}

	/**
	 * Inicjalizacja ramki.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.PINK);
		frame.getContentPane().setBackground(Color.PINK);
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(LogowanieDoSerwisu.class.getResource("/resources/gdynia-herb.png")));
		frame.setBounds(100, 100, 478, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		loginField = new JTextField();
		loginField.setBounds(206, 212, 143, 20);
		frame.getContentPane().add(loginField);
		loginField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(206, 243, 143, 20);
		frame.getContentPane().add(passwordField);

		/**
		* Tworzymy przycisk do logowania i nas³uchujemy...
		* Jeœli dana osoba kliknie go to wewnêtrzna
		* metoda s³u¿y do zalogowania siê w programie
		* 
		* Jeœli siê uda zamykamy to okno wykonuj¹c frame.dispose()
		* i otwieramy nowe okno z w³aœciwym programem zawarte w klasie Welcome
		* 
		* @param uname - login pobierany z pola LOGIN
		* @param pass - haslo pobierane z pola HAS£O
		*/
		JButton btnLogowanie = new JButton("logowanie");
		btnLogowanie.setBackground(Color.LIGHT_GRAY);
		btnLogowanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uname = loginField.getText();
				@SuppressWarnings("deprecation")
				String pass = passwordField.getText();
				
				if(userTable=="u") {
					Uczen uczen = DBservice.LoginUczen(uname, pass);
					if (uczen != null) {
						JOptionPane.showMessageDialog(null, "Witamy w Portalu Edukacyjnym");
						SerwisEdukacyjny wel = new SerwisEdukacyjny(uczen);
						wel.setVisible(true);
						frame.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "B³êdny LOGIN lub HAS£O");
					}
				
				}else if(userTable=="n") {
					Nauczyciel nauczyciel = DBservice.LoginNauczyciel(uname, pass);
					if (nauczyciel != null) {
						JOptionPane.showMessageDialog(null, "Witamy w Portalu Nauczycielskim");
						PanelNauczyciela pn = new PanelNauczyciela(nauczyciel);
						pn.setNauczyciel(nauczyciel);
						pn.setVisible(true);
						frame.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "B³êdny LOGIN lub HAS£O");
					}
				}else if(userTable=="p") {
					Admin admin = DBservice.LoginAdmin(uname, pass);
					if (admin != null) {
						JOptionPane.showMessageDialog(null, "Witamy w Portalu Administratora");
						PanelAdministratora pn = new PanelAdministratora(admin);
						pn.setAdmin(admin);
						pn.setVisible(true);
						frame.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "B³êdny LOGIN lub HAS£O");
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "B³êdny LOGIN lub HAS£O");
				}
				
				
			  
			}
		});
		
		btnLogowanie.setBounds(116, 352, 255, 49);
		frame.getContentPane().add(btnLogowanie);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(116, 215, 46, 14);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblHaso = new JLabel("Has\u0142o:");
		lblHaso.setBounds(116, 246, 46, 14);
		frame.getContentPane().add(lblHaso);
		
		BufferedImage img=new ImgUtils().scaleImage(256,126,"hat.png");
	    ImageIcon imageIcon = new ImageIcon(img);
		JLabel lblNewLabel = new JLabel(new ImageIcon(LogowanieDoSerwisu.class.getResource("/resources/gdynia-herb.png")));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(116, 75, 255, 126);
		frame.getContentPane().add(lblNewLabel);
		
		/**
		 * Checkboxy s³u¿¹ do sprawdzenia prze program jak¹ mamy rolê i na podstawie
		 * tego dla jakiego u¿ytkownika sprawdzaæ dane i jaki panel nam otworzyæ po
		 * zalogowaniu.
		 * Odci¹¿a to bazê danych z dodatkowych obliczeñ sprawdzania roli na poziomie
		 * serwera baz danych bo robimy to w pow³oce aplikacji.
		 */
		JRadioButton rdbtnUcze = new JRadioButton("Ucze\u0144");
		rdbtnUcze.setBackground(Color.PINK);
		rdbtnUcze.setSelected(true);
		buttonGroup.add(rdbtnUcze);
		rdbtnUcze.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox uczeñ zaznaczony
		            userTable="u";
		        }
			}
		});
		rdbtnUcze.setBounds(206, 270, 143, 23);
		frame.getContentPane().add(rdbtnUcze);
		
		JRadioButton rdbtnNauczyciel = new JRadioButton("Nauczyciel");
		rdbtnNauczyciel.setBackground(Color.PINK);
		buttonGroup.add(rdbtnNauczyciel);
		rdbtnNauczyciel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox nauczyciel zaznaczony
		            userTable="n";
		        }
			}
		});
		rdbtnNauczyciel.setBounds(206, 296, 143, 23);
		frame.getContentPane().add(rdbtnNauczyciel);
		
		JLabel lblZalogujJako = new JLabel("Zaloguj Jako:");
		lblZalogujJako.setBounds(116, 274, 80, 14);
		frame.getContentPane().add(lblZalogujJako);
		
		JRadioButton rdbtnPracownik = new JRadioButton("Pracownik");
		rdbtnPracownik.setBackground(Color.PINK);
		rdbtnPracownik.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {//checkbox pracownik zaznaczony
				userTable="p";
			}
		});
		buttonGroup.add(rdbtnPracownik);
		rdbtnPracownik.setBounds(206, 322, 143, 23);
		frame.getContentPane().add(rdbtnPracownik);
		
		
	}
	
	
}
