package generator.gui.panel;

import java.awt.Component;

/** Vertical panel : components are placed from top to bottom. */
@SuppressWarnings("serial")
public class CPanelVertical extends CPanel
{

	@Override
	public Component add(Component comp)
	{
		super.add(comp, this.gbc);
		this.gbc.gridy++;
		return comp;
	}

}
