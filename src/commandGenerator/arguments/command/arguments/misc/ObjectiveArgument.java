package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.argumentSelection.ObjectiveSelectionPanel;

public class ObjectiveArgument extends Argument
{

	private ObjectiveSelectionPanel panel;

	public ObjectiveArgument()
	{
		super("scoreboard.objective.type");
	}

	@Override
	public String generateCommand()
	{
		return this.panel.generateObjective();
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
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return true;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setSelected(data.get(0));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof ObjectiveArgument)) return;
		this.panel.setSelected(((ObjectiveArgument) toMatch).panel.generateObjective());
	}

	@Override
	public void reset()
	{
		this.panel.reset();
	}

}
