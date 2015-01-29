package commandGenerator.gui.helper.commandSpecific.scoreboard;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TeamsEmptyPanel extends ScoreboardPanel
{

	private CEntry entryName;

	public TeamsEmptyPanel()
	{
		super();

		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.team");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryName);
	}

	@Override
	public String generateText()
	{
		if (entryName.getText().equals("") || entryName.getText().contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}

		return entryName.getText();
	}

}
