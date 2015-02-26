package commandGenerator.gui.helper.commandSpecific.scoreboard;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TeamsEmptyPanel extends ScoreboardPanel
{

	private CEntry entryName;

	public TeamsEmptyPanel()
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
		entryName = new CEntry(Constants.DATAID_NAME, "GUI:scoreboard.team", "");
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
