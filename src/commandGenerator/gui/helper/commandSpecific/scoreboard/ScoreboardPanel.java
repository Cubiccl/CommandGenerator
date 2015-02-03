package commandGenerator.gui.helper.commandSpecific.scoreboard;

import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public abstract class ScoreboardPanel extends HelperPanel
{

	public ScoreboardPanel()
	{
		super(CGConstants.PANELID_OPTIONS, "GENERAL:options");
	}

	public abstract String generateText();

}
