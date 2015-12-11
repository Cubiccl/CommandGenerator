package generator.gui;

import generator.gui.menubar.CGMenuBar;
import generator.gui.panel.CPanel;
import generator.gui.panel.PanelCommand;
import generator.interfaces.ITranslate;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ITranslate
{
	private CGMenuBar menubar;
	/** Contains the Current Panel. */
	private JScrollPane scrollpane;
	/** Displays the command and command-related buttons. */
	private PanelCommand panelCommand;
	private CPanel panelCurrent;
	/** Pane to display the states. */
	private CTextArea textAreaStates;

	public MainWindow()
	{
		super("Command Generator v2.0 Indev");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 400));
		this.setLocationRelativeTo(null);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.getContentPane().setLayout(new BorderLayout());

		this.menubar = new CGMenuBar();
		this.setJMenuBar(this.menubar);

		this.textAreaStates = new CTextArea();
		this.getContentPane().add(this.textAreaStates, BorderLayout.NORTH);

		// Stores the Command Panel and the JScrollPane.
		JPanel panel2 = new JPanel(new BorderLayout());
		this.getContentPane().add(panel2, BorderLayout.CENTER);

		this.panelCommand = new PanelCommand();
		panel2.add(this.panelCommand, BorderLayout.NORTH);

		this.scrollpane = new JScrollPane();
		this.scrollpane.setBorder(null);
		panel2.add(this.scrollpane, BorderLayout.CENTER);
	}

	public CPanel getPanelCurrent()
	{
		return this.panelCurrent;
	}

	/** @return The CTextArea where the States are displayed. */
	public CTextArea getTextAreaStates()
	{
		return this.textAreaStates;
	}

	public void setPanelCurrent(CPanel panelCurrent)
	{
		this.panelCurrent = panelCurrent;
		this.scrollpane.setViewportView(this.panelCurrent);
	}

	@Override
	public void updateLang()
	{
		this.menubar.updateLang();
		if (this.panelCurrent != null) this.panelCurrent.updateLang();
		this.panelCommand.updateLang();
	}

}
