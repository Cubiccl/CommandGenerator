package commandGenerator.arguments.command.arguments;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.argumentSelection.TeamsOptionPanel;

public class TeamOptionArgument extends Argument
{
	private TeamsOptionPanel panel;

	public TeamOptionArgument()
	{
		super("scoreboard.teams.option", Argument.MISC, true, 2);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new TeamsOptionPanel();
	}

	@Override
	public String generateCommand()
	{
		return this.panel.generateText();
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}
