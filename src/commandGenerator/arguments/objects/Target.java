package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.main.DisplayHelper;

public class Target
{
	/** The Target types */
	public static final int ALL = 0, CLOSEST = 1, ENTITY = 2, RANDOM = 3, PLAYER = 4;
	/** Names of the selectors. */
	public static final String[] selectorNames = { "All players", "Closest player", "All entities", "A random player" };
	/** All selector types. */
	public static final String[] selectorsTypes = { "@a", "@p", "@e", "@r" };

	/** Generates a target from a generated command.
	 * 
	 * @param text
	 *            - <i>String</i> - The generated command. */
	public static Target generateFrom(String text)
	{
		if (!text.startsWith("@"))
		{
			DisplayHelper.log("Created entity : " + text);
			return new Target(text);
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

			Target sel = new Target(type, selectorList);
			DisplayHelper.log("Created entity selector : " + sel.display());
			return sel;
		} catch (Exception e)
		{
			DisplayHelper.log("Error while creating target : " + text);
			return null;
		}
	}

	/** Returns the type of the Target from the String input.
	 * 
	 * @param sel
	 *            - <i>String</i> - The text. */
	public static int getTypeFromString(String sel)
	{
		if (sel.equals("@a")) return ALL;
		if (sel.equals("@p")) return CLOSEST;
		if (sel.equals("@e")) return ENTITY;
		if (sel.equals("@r")) return RANDOM;
		return PLAYER;
	}

	/** This Target's name. */
	private String name;

	/** This Target's selectors. */
	private List<String[]> selectors;

	/** This Target's type. */
	private int type;

	/** Creates a new Target
	 * 
	 * @param type
	 *            - <i>int</i> - The Target type.
	 * @param selectors
	 *            - <i>ArrayList:String[]</i> - The selectors. */
	public Target(int type, List<String[]> selectors)
	{
		this.type = type;
		this.selectors = selectors;
	}

	/** Creates a new Target
	 * 
	 * @param name
	 *            - <i>String</i> - The Target's name. */
	public Target(String name)
	{
		this.name = name;
		this.type = PLAYER;
		selectors = new ArrayList<String[]>();
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

	/** Returns a String version of this Target to be displayed to the user. */
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

	/** Returns this Target's selectors. */
	public List<String[]> getSelectors()
	{
		return selectors;
	}

	/** Returns this Target's type. */
	public int getType()
	{
		return type;
	}

}
