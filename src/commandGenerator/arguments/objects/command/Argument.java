package commandGenerator.arguments.objects.command;

import java.util.Map;

public abstract class Argument
{

	private String id;
	private int length;
	private boolean compulsery;

	public Argument(int length, String id)
	{
		this(length, id, true);
	}

	public Argument(int length, String id, boolean compulsery)
	{
		this.length = length;
		this.id = id;
		this.compulsery = compulsery;
	}

	public boolean isCompulsery()
	{
		return compulsery;
	}

	public int getLength()
	{
		return length;
	}

	public String getId()
	{
		return id;
	}

	public abstract boolean matches(String[] elements);

	public abstract void generateData(String[] elements, Map<String, Object> data);

}
