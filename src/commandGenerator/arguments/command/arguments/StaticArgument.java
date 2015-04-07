package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.main.DisplayHelper;

public class StaticArgument extends Argument
{
	private final String value;

	public StaticArgument(String value)
	{
		super("static");
		this.value = value;
	}

	@Override
	public String generateCommand()
	{
		return this.value;
	}

	@Override
	public Component generateComponent()
	{
		return null;
	}

	@Override
	public void initGui()
	{}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		if (!data.get(0).equals(this.value))
		{
			DisplayHelper.log(data.get(0) + " isn't a valid argument.");
		}
		return data.get(0).equals(this.value);
	}

	@Override
	public void setupFrom(List<String> data)
	{}

	@Override
	public void synchronize(Argument toMatch)
	{}

	@Override
	public void reset()
	{}

}
