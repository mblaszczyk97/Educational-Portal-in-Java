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
import sysedu.domain.Uczen;

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
	

	private JFrame frame;
	private JTextField loginField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
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
	 * Create the application.
	 */
	public LogowanieDoSerwisu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		loginField = new JTextField();
		loginField.setBounds(569, 354, 143, 20);
		frame.getContentPane().add(loginField);
		loginField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(569, 385, 143, 20);
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
		btnLogowanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uname = loginField.getText();
				@SuppressWarnings("deprecation")
				String pass = passwordField.getText();
				
				
				Uczen uczen = DBservice.Login(uname, pass);
				if(uczen!=null)	{
				JOptionPane.showMessageDialog(null,"Witamy w Portalu Edukacyjnym");
				  SerwisEdukacyjny wel = new SerwisEdukacyjny(uczen);
			      wel.setVisible(true);
			      //frame.setVisible(false);
			      frame.dispose();
			      //JLabel label = new JLabel("Witaj Uczniu:"+uname);
			      //wel.getContentPane().add(label);
				}
				else{
				JOptionPane.showMessageDialog(null, "B³êdny LOGIN lub HAS£O");
				}
			  
			}
		});
		
		btnLogowanie.setBounds(569, 416, 143, 23);
		frame.getContentPane().add(btnLogowanie);
		
		JButton btnRejestracja = new JButton("rejestracja");
		btnRejestracja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				  SerwisEdukacyjny wel = new SerwisEdukacyjny(new Uczen());
			      wel.setVisible(true);
			      //frame.setVisible(false);
			      frame.dispose();
			}
		});
		btnRejestracja.setBounds(569, 450, 143, 23);
		frame.getContentPane().add(btnRejestracja);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(513, 357, 46, 14);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblHaso = new JLabel("Has\u0142o");
		lblHaso.setBounds(513, 388, 46, 14);
		frame.getContentPane().add(lblHaso);
		
		BufferedImage img=new ImgUtils().scaleImage(256,126,"hat.png");
	    ImageIcon imageIcon = new ImageIcon(img);
		JLabel lblNewLabel = new JLabel(imageIcon);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(513, 217, 255, 126);
		frame.getContentPane().add(lblNewLabel);
		
		
	}
	
	
}
