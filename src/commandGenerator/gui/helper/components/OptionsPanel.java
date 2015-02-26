package commandGenerator.gui.helper.components;

import java.awt.Dimension;

import commandGenerator.main.Constants;

@SuppressWarnings("serial")
public abstract class OptionsPanel extends HelperPanel implements CComponent
{

	/** The default width for an Option Panel. */
	private static final int WIDTH = 950;

	/** Creates a new Options Panel. */
	public OptionsPanel(Object... details)
	{
		super(Constants.PANELID_NONE, "GENERAL:options", details);
		setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
	}

	/** Generates the command corresponding to the arguments selected by the user. */
	public abstract String generateCommand();

	@Override
	protected void setupSize()
	{
		super.setupSize();
		setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
	}
}
