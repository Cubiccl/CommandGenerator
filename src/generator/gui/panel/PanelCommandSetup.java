package generator.gui.panel;

import generator.gui.CTabbedPane;

import java.awt.BorderLayout;

/** Contains the Command selection & its details. */
@SuppressWarnings("serial")
public class PanelCommandSetup extends CPanel
{

	private PanelCommandSelection panelSelection;
	private CTabbedPane tabbedPaneStructure;

	public PanelCommandSetup()
	{
		this.setLayout(new BorderLayout());

		this.panelSelection = new PanelCommandSelection();
		this.tabbedPaneStructure = new CTabbedPane();

		this.add(this.panelSelection, BorderLayout.NORTH);
		this.add(this.tabbedPaneStructure, BorderLayout.CENTER);
	}

}
