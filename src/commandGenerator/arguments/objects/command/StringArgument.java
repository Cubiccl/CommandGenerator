package commandGenerator.arguments.objects.command;

import java.util.Map;

public class StringArgument extends Argument
{

	private String[] choices;

	public StringArgument(String id, String... choices)
	{
		super(1, id);
	}

	public StringArgument(String id, boolean compulsery, String... choices)
	{
		super(1, id, compulsery);
		this.choices = choices;
	}

	@Override
	public boolean matches(String[] elements)
	{
		for (int i = 0; i < choices.length; i++)
			if (choices[i].equals(elements[0])) return true;

		return false;
	}

	@Override
	public void generateData(String[] elements, Map<String, Object> data)
	{
		data.put(getId(), 0);
		for (int i = 0; i < choices.length; i++)
			if (choices[i].equals(elements[0])) data.put(getId(), i);
	}

}
