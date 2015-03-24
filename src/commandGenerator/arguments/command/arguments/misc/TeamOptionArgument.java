package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

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
	public String generateCommand()
	{
		return this.panel.generateText();
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
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return data.size() == 2;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setupFrom(data.get(0), data.get(1));
	}

}
