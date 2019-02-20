package sysedu.dialogi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sysedu.db.DBservice;
import sysedu.domain.Nauczyciel;
import sysedu.domain.Oddzial;
import sysedu.domain.Uczen;

import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.UIManager;

public class JDialogListaNauczycieli extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultListModel<Nauczyciel> listModelNauczyciele = null;
	private Nauczyciel selectedNauczyciel = null;
	private JList list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDialogListaNauczycieli dialog = new JDialogListaNauczycieli();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JDialogListaNauczycieli() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogListaNauczycieli.class.getResource("/resources/gdynia-herb.png")));
		setBackground(Color.PINK);
		setModal(true);
		setBounds(100, 100, 522, 565);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.PINK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						wybierzNauczyciela();
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
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setPreferredSize(new Dimension(100, 100));
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				list = new JList(getListModelNauczyciele());
				list.setFixedCellHeight(25);
				list.setPreferredSize(new Dimension(300, list.getFixedCellHeight()*listModelNauczyciele.size()));
				scrollPane.setViewportView(list);
				list.setFont(new Font("Tahoma", Font.BOLD, 16));
				list.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Wybierz nauczyciela", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			}
		}
	}

	protected void wybierzNauczyciela() {
		selectedNauczyciel=(Nauczyciel)getList().getSelectedValue();
		dispose();
		
	}

	public DefaultListModel<Nauczyciel> getListModelNauczyciele() {
		if(listModelNauczyciele==null) {
			listModelNauczyciele=new DefaultListModel<Nauczyciel>();
			List<Nauczyciel> lista1 = DBservice.getListaNauczycieli();
			listModelNauczyciele.removeAllElements();
			for (Iterator iterator = lista1.iterator(); iterator.hasNext();) {
				Nauczyciel nauczyciel = (Nauczyciel) iterator.next();
				listModelNauczyciele.addElement(nauczyciel);
			}
		}
		return listModelNauczyciele;
	}
	

	public JList getList() {
		return list;
	}

	public Nauczyciel getSelectedNauczyciel() {
		return selectedNauczyciel;
	}
	
}
