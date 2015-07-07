package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.argumentSelection.GamerulePanel;
import commandGenerator.main.Resources;

public class GameruleArgument extends Argument
{
	private GamerulePanel panel;

	public GameruleArgument()
	{
		super("gamerule.value", Argument.NORMAL, true, 2);
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
		this.panel = new GamerulePanel();
		this.panel.setMinimumSize(new Dimension(400, 200));
		this.panel.setPreferredSize(new Dimension(600, 200));
		this.panel.setBorder(null);
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		if (data.size() != 2) return false;

		for (String value : Resources.gamerules)
		{
			if (value.equals(data.get(0))) return true;
		}
		
		return false;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setupFrom(data.get(0), data.get(1));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof GameruleArgument)) return;
		String[] data = ((GameruleArgument) toMatch).panel.generateText().split(" ");
		this.panel.setupFrom(data[0], data[1]);
	}

	@Override
	public void reset()
	{
		this.panel.reset();
	}

}
