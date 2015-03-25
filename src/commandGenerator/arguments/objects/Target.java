package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

public class Target
{
	/** The Target types */
	public static final int ALL = 0, CLOSEST = 1, ENTITY = 2, RANDOM = 3, PLAYER = 4;
	/** Names of the selectors. */
	public static final String[] selectorNames = { "All players", "Closest player", "All entities", "A random player" };
	/** All selector types. */
	public static final String[] selectorsTypes = { "@a", "@p", "@e", "@r" };

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
		this.selectors = new ArrayList<String[]>();
	}

	/** Generates the command structure to execute. */
	public String commandStructure()
	{
		if (type == PLAYER) return this.name;

		String display = selectorsTypes[type];

		if (this.selectors.size() > 0)
		{
			display += "[";
			for (int i = 0; i < this.selectors.size(); i++)
			{

				if (i != 0) display += ",";
				display += this.selectors.get(i)[0] + "=" + this.selectors.get(i)[1];

			}
			display += "]";
		}

		return display;
	}

	/** Returns a String version of this Target to be displayed to the user. */
	public String display()
	{
		if (type == PLAYER) return this.name;

		String display = selectorNames[type] + " with : ";
		for (int i = 0; i < this.selectors.size(); i++)
		{
			if (i != 0) display += " ; ";
			display += this.selectors.get(i)[0] + " = " + this.selectors.get(i)[1];
		}
		return display;
	}

	/** Returns this Target's selectors. */
	public List<String[]> getSelectors()
	{
		return this.selectors;
	}

	/** Returns this Target's type. */
	public int getType()
	{
		return this.type;
	}

}
