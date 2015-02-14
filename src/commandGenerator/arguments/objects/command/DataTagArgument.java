package commandGenerator.arguments.objects.command;

import java.util.ArrayList;
import java.util.Map;

import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;

public class DataTagArgument extends Argument
{

	public DataTagArgument(String id)
	{
		super(1, id);
	}

	public DataTagArgument(String id, boolean compulsery)
	{
		super(1, id, compulsery);
	}

	@Override
	public boolean matches(String[] elements)
	{
		return elements[0].startsWith("{") && elements[0].endsWith("}");
	}

	@Override
	public void generateData(String[] elements, Map<String, Object> data)
	{
		data.put(getId(), new ArrayList<Tag>());
		data.put(getId(), DataTags.generateListFrom(elements[0]));
	}

}
