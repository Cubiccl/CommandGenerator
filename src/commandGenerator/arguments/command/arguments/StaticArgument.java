package commandGenerator.arguments.command.arguments;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;

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

}
