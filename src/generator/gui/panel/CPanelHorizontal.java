package generator.gui.panel;

import java.awt.Component;

/** Horizontal panel : components are placed from left to right. */
@SuppressWarnings("serial")
public class CPanelHorizontal extends CPanel
{

	@Override
	public Component add(Component comp)
	{
		super.add(comp, this.gbc);
		this.gbc.gridx++;
		return comp;
	}

}
