package commandGenerator.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class MainWindow extends JFrame
{

	public static CGMenubar menubar;
	public static PanelCommandSelection panelGeneral;

	/** The window of the program. */
	public MainWindow()
	{

		String title = Lang.get("GUI:main.title");
		title = title.replaceAll("<version>", Resources.versions[Resources.versions.length - 1]);
		title = title.replaceAll("<minecraft>", Resources.versionMinecraft);

		setTitle(title);
		setSize(new Dimension(1000, 700));
		setVisible(true);
		setResizable(true);
		setLayout(new GridLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		panelGeneral = new PanelCommandSelection(true);
		JScrollPane scrollpane = new JScrollPane(panelGeneral);
		scrollpane.getVerticalScrollBar().setUnitIncrement(20);
		menubar = new CGMenubar();

		add(scrollpane);
		setJMenuBar(menubar);

	}

	public void updateLang()
	{

		String title = Lang.get("GUI:main.title");
		title = title.replaceAll("<version>", Resources.versions[Resources.versions.length - 1]);
		title = title.replaceAll("<minecraft>", Resources.versionMinecraft);

		setTitle(title);
		menubar.updateLang();
		panelGeneral.updateLang();
	}

}
