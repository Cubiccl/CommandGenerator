package commandGenerator.gui.helper.commandSpecific.scoreboard;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TeamsRemovePanel extends ScoreboardPanel
{

	private CEntry entryName;

	public TeamsRemovePanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(entryName);
	}

	@Override
	protected void createComponents()
	{
		entryName = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.teams.remove.label");
	}

	@Override
	protected void createListeners()
	{}

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
