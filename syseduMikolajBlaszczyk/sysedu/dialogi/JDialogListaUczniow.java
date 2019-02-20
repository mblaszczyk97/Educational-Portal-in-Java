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

public class JDialogListaUczniow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultListModel<Uczen> listModelUczniowie = null;
	private Uczen selectedUczen = null;
	private JList list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDialogListaUczniow dialog = new JDialogListaUczniow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JDialogListaUczniow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogListaUczniow.class.getResource("/resources/gdynia-herb.png")));
		setBackground(Color.PINK);
		setModal(true);
		setBounds(100, 100, 522, 565);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setPreferredSize(new Dimension(100, 100));
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				list = new JList(getListModelUczniowie());
				list.setFixedCellHeight(25);
				list.setPreferredSize(new Dimension(300, list.getFixedCellHeight()*listModelUczniowie.size()));
				scrollPane.setViewportView(list);
				list.setFont(new Font("Tahoma", Font.BOLD, 16));
				list.setBorder(new TitledBorder(null, "Wybierz ucznia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			}
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
						wybierzUcznia();
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

	protected void wybierzUcznia() {
		selectedUczen=(Uczen)getList().getSelectedValue();
		dispose();
		
	}

	public DefaultListModel<Uczen> getListModelUczniowie() {
		if(listModelUczniowie==null) {
			listModelUczniowie=new DefaultListModel<Uczen>();
			List<Uczen> lista1 = DBservice.getUczniowie();
			listModelUczniowie.removeAllElements();
			for (Iterator iterator = lista1.iterator(); iterator.hasNext();) {
				Uczen uczen = (Uczen) iterator.next();
				listModelUczniowie.addElement(uczen);
			}
		}
		return listModelUczniowie;
	}
	

	public JList getList() {
		return list;
	}

	public Uczen getSelectedUczen() {
		return selectedUczen;
	}
	
}
