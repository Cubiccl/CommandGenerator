package commandGenerator.arguments.objects.command;

import java.util.Map;

import commandGenerator.arguments.objects.Target;

public class TargetArgument extends Argument
{

	public TargetArgument(String id)
	{
		super(1, id);
	}

	public TargetArgument(String id, boolean compulsery)
	{
		super(1, id, compulsery);
	}

	@Override
	public boolean matches(String[] elements)
	{
		return true;
	}

	@Override
	public void generateData(String[] elements, Map<String, Object> data)
	{
		data.put(getId(), Target.generateFrom(elements[0]));
	}

}
