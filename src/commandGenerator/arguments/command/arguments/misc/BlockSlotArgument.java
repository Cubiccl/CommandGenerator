package commandGenerator.arguments.command.arguments.misc;

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
}
