package commandGenerator.arguments.command.arguments.misc;

import java.util.List;

import commandGenerator.arguments.command.arguments.IntArgument;

public class BlockSlotArgument extends IntArgument
{

	public BlockSlotArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
	}

	@Override
	public String generateCommand()
	{
		String value = super.generateCommand();
		if (value == null) return null;
		else return "container.slot." + value;
	}

	@Override
	public boolean matches(List<String> data)
	{
		data.set(0, data.get(0).substring("container.slot.".length()));
		return super.matches(data);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		data.set(0, data.get(0).substring("container.slot.".length()));
		super.setupFrom(data);
	}
}
