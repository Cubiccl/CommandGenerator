package generator.gui.panel;

import java.awt.BorderLayout;

/** Contains the Command selection & its details. */
@SuppressWarnings("serial")
public class PanelCommandSetup extends CPanel
{

	private PanelCommandSelection panelSelection;

	public PanelCommandSetup()
	{
		this.setLayout(new BorderLayout());

		this.panelSelection = new PanelCommandSelection();

		this.add(this.panelSelection, BorderLayout.NORTH);
	}

}
