package projekt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.MenuDragMouseListener;
import javax.swing.event.MenuDragMouseEvent;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.JProgressBar;

public class Welcome extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Welcome frameTabel = new Welcome();
	}
	JLabel welcome = new JLabel("Wybierz przedmiot");
	JPanel panel = new JPanel();
	
	/**
	 * Create the application.
	 */
	public Welcome() {
		super("Welcome");
		setSize(1280,720);
		setLocation(500,280);
		panel.setLayout(null);
		welcome.setHorizontalAlignment(SwingConstants.LEFT);
		welcome.setBounds(20, 331, 112, 32);

		panel.add(welcome);

		getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("Matematyka");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setBounds(10, 268, 132, 52);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("J\u0119zyk Polski");
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.setBounds(10, 130, 132, 51);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Przyroda");
		btnNewButton_2.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_2.setBounds(10, 222, 132, 52);
		panel.add(btnNewButton_2);
		
		JButton btnJzykAngielski = new JButton("J\u0119zyk Angielski");
		btnJzykAngielski.setHorizontalAlignment(SwingConstants.LEFT);
		btnJzykAngielski.setBounds(10, 174, 132, 52);
		panel.add(btnJzykAngielski);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(163, 133, 1091, 537);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(1074, 0, 17, 537);
		panel_1.add(scrollBar);
		
		BufferedImage img=new ImgUtils().scaleImage(122,81,"blackboard.png");
	    ImageIcon imageIcon = new ImageIcon(img);
		JLabel lblNewLabel = new JLabel(imageIcon);
		lblNewLabel.setBounds(10, 0, 132, 81);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 110, 1244, 2);
		panel.add(separator);
		
		JLabel lblUcze = new JLabel("Ucze\u0144: ");
		lblUcze.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUcze.setBounds(152, 11, 51, 25);
		panel.add(lblUcze);
		
		JLabel lblNewLabel_1 = new JLabel("NazwaUcznia");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(202, 17, 112, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblredniaOcen = new JLabel("\u015Arednia ocen: ");
		lblredniaOcen.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblredniaOcen.setBounds(311, 11, 112, 25);
		panel.add(lblredniaOcen);
		
		JLabel lblredniaucznia = new JLabel("\u015AredniaUcznia");
		lblredniaucznia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblredniaucznia.setBounds(409, 17, 112, 14);
		panel.add(lblredniaucznia);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	}
