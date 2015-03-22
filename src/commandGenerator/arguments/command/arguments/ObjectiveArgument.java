package commandGenerator.arguments.command.arguments;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.argumentSelection.ObjectiveSelectionPanel;

public class ObjectiveArgument extends Argument
{

	private ObjectiveSelectionPanel panel;

	public ObjectiveArgument()
	{
		super("scoreboard.objective.type", Argument.MISC, true, 1);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new ObjectiveSelectionPanel("GUI:" + this.getId());
	}

	@Override
	public String generateCommand()
	{
		return this.panel.generateObjective();
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}
