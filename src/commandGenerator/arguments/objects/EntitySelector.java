package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Generator;

public class EntitySelector
{
	public static final String[] selectorsTypes = { "@a", "@p", "@e", "@r" }, selectorNames = { "All players", "Closest player", "All entities",
			"A random player" };
	public static final int ALL = 0, CLOSEST = 1, ENTITY = 2, RANDOM = 3, PLAYER = 4;

	private int type;
	private String name;
	private List<String[]> selectors;

	public EntitySelector(String name)
	{
		this.name = name;
		this.type = PLAYER;
	}

	/** Creates a new Entity Selector
	 * 
	 * @param type
	 *            - The Selector type.
	 * @param selectors
	 *            - The selectors. */
	public EntitySelector(int type, List<String[]> selectors)
	{
		this.type = type;
		this.selectors = selectors;
	}

	/** Generates the command structure to execute. */
	public String commandStructure()
	{
		if (type == PLAYER) return name;

		String display = selectorsTypes[type];

		if (selectors.size() > 0)
		{
			display += "[";
			for (int i = 0; i < selectors.size(); i++)
			{

				if (i != 0) display += ",";
				display += selectors.get(i)[0] + "=" + selectors.get(i)[1];

			}
			display += "]";
		}

		return display;
	}

	public List<String[]> getSelectors()
	{
		return selectors;
	}

	public int getType()
	{
		return type;
	}

	public static EntitySelector generateFrom(String text)
	{
		if (!text.startsWith("@"))
		{
			DisplayHelper.log("Created entity : " + text);
			return new EntitySelector(text);
		}

		try
		{
			int type = getTypeFromString(text.substring(0, 2));
			List<String[]> selectorList = new ArrayList<String[]>();
			if (text.length() > 2)
			{
				String[] selectorsText = text.substring(3, text.length() - 1).split(",");
				for (String data : selectorsText)
					selectorList.add(new String[] { data.split("=")[0], data.split("=")[1] });
			}

			EntitySelector sel = new EntitySelector(type, selectorList);
			DisplayHelper.log("Created entity selector : " + sel.display());
			return sel;
		} catch (Exception e)
		{
			Generator.wrong();
			return null;
		}
	}

	public String display()
	{
		if (type == PLAYER) return name;

		String display = selectorNames[type] + " with : ";
		for (int i = 0; i < selectors.size(); i++)
		{
			if (i != 0) display += " ; ";
			display += selectors.get(i)[0] + " = " + selectors.get(i)[1];
		}
		return display;
	}

	public static int getTypeFromString(String sel)
	{
		if (sel.equals("@a")) return ALL;
		if (sel.equals("@p")) return CLOSEST;
		if (sel.equals("@e")) return ENTITY;
		if (sel.equals("@r")) return RANDOM;
		return PLAYER;
	}

}
