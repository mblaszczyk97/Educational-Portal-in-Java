package projekt;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;

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

public class ProjektClass extends JFrame {
	

	private JFrame frame;
	private JTextField loginField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjektClass window = new ProjektClass();
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
	public ProjektClass() {
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

		
		JButton btnLogowanie = new JButton("logowanie");
		btnLogowanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uname = loginField.getText();
				@SuppressWarnings("deprecation")
				String pass = passwordField.getText();
			   if(uname.equals("abc") && pass.equals("123"))
			   {
			      Welcome wel = new Welcome();
			      wel.setVisible(true);
			      //frame.setVisible(false);
			      frame.dispose();
			      //JLabel label = new JLabel("Witaj Uczniu:"+uname);
			      //wel.getContentPane().add(label);
			    }
			    else
			    {
			      
			    }
			}
		});
		btnLogowanie.setBounds(569, 416, 143, 23);
		frame.getContentPane().add(btnLogowanie);
		
		JButton btnRejestracja = new JButton("rejestracja");
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
