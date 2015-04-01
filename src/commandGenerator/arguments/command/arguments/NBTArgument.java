package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;

public class NBTArgument extends Argument
{

	private final String argId;

	public NBTArgument(String id, boolean isCompulsery, String argId)
	{
		super(id, Argument.NBT, isCompulsery, 1);
		this.argId = argId;
	}

	@Override
	/**
	 * For a NBT Argument, it will return the ID of the Argument which the NBT Tags come from.
	 */
	public String generateCommand()
	{
		return this.argId;
	}

	@Override
	public Component generateComponent()
	{
		return null;
	}

	public String getTarget()
	{
		return this.argId;
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
		return data.get(0).startsWith("{") && data.get(0).endsWith("}");
	}

	@Override
	public void setupFrom(List<String> data)
	{
		// TODO NBT Setup
	}

	@Override
	public void synchronize(Argument toMatch)
	{}

}
