package commandGenerator.arguments.objects.command;

import java.util.Map;

import commandGenerator.arguments.objects.Coordinates;

public class CoordinatesArgument extends Argument
{

	public CoordinatesArgument(String id)
	{
		super(3, id);
	}

	public CoordinatesArgument(boolean compulsery, String id)
	{
		super(3, id, compulsery);
	}

	@Override
	public boolean matches(String[] elements)
	{
		try
		{
			if (elements[0].startsWith("~")) elements[0] = elements[0].substring(1);
			if (elements[1].startsWith("~")) elements[1] = elements[1].substring(1);
			if (elements[2].startsWith("~")) elements[2] = elements[2].substring(1);

			Float.parseFloat(elements[0]);
			Float.parseFloat(elements[1]);
			Float.parseFloat(elements[2]);
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}

	@Override
	public void generateData(String[] elements, Map<String, Object> data)
	{
		data.put(getId(), Coordinates.generateFrom(elements[0], elements[1], elements[2]));
	}

}
