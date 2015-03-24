package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.main.DisplayHelper;

public class StaticArgument extends Argument
{
	private String value;

	public StaticArgument(String value)
	{
		super(null, Argument.STATIC, true, 1);
		this.value = value;
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
	public String generateCommand()
	{
		return this.value;
	}

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

}
