package commandGenerator.gui.helper.components;

import java.awt.Component;
import java.awt.Dimension;

import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public abstract class OptionsPanel extends HelperPanel implements CComponent
{

	private static final int WIDTH = 950;

	/** Creates a new Options Panel. */
	public OptionsPanel()
	{
		super(CGConstants.PANELID_NONE, "GENERAL:options");
		setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
	}

	public abstract String generateCommand();

	@Override
	protected void updateSize(Component component)
	{
		super.updateSize(component);
		setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
	}
}
