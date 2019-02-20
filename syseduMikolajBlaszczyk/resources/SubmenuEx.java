package resources;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.EventQueue;

public class SubmenuEx extends JFrame {

    public SubmenuEx() {

        initUI();
    }

    private void initUI() {

        createMenuBar();

        setTitle("Submenu");
        setSize(360, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();

        ImageIcon iconNew = new ImageIcon("src/resources/new.png");
        ImageIcon iconOpen = new ImageIcon("src/resources/open.png");
        ImageIcon iconSave = new ImageIcon("src/resources/save.png");
        ImageIcon iconExit = new ImageIcon("src/resources/exit.png");

        JMenu fileMenu = new JMenu("File");
        JMenu impMenu = new JMenu("Import");

        JMenuItem newsMenuItem = new JMenuItem("Import newsfeed list...");
        JMenuItem bookmarksMenuItem = new JMenuItem("Import bookmarks...");
        JMenuItem importMailMenuItem = new JMenuItem("Import mail...");

        impMenu.add(newsMenuItem);
        impMenu.add(bookmarksMenuItem);
        impMenu.add(importMailMenuItem);

        JMenuItem newMenuItem = new JMenuItem("New", iconNew);
        JMenuItem openMenuItem = new JMenuItem("Open", iconOpen);
        JMenuItem saveMenuItem = new JMenuItem("Save", iconSave);

        JMenuItem exitMenuItem = new JMenuItem("Exit", iconExit);
        exitMenuItem.setToolTipText("Exit application");

        exitMenuItem.addActionListener((event) -> System.exit(0));

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(impMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        menubar.add(fileMenu);

        setJMenuBar(menubar);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SubmenuEx ex = new SubmenuEx();
            ex.setVisible(true);
        });
    }
}
