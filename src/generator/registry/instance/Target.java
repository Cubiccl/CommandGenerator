package generator.registry.instance;

import generator.main.Utils;

import java.util.ArrayList;

/** Represents Entities targeted by commands. */
public class Target extends ObjectInstance
{
	/** The different types a Target can be.
	 * <ul>
	 * <li>PLAYERS_ALL = 0;</li>
	 * <li>PLAYER_CLOSEST = 1;</li>
	 * <li>PLAYER_RANDOM = 2;</li>
	 * <li>ENTITIES = 3;</li>
	 * <li>PLAYER_NAME = 4;</li>
	 * </ul> */
	public static final int PLAYERS_ALL = 0, PLAYER_CLOSEST = 1, PLAYER_RANDOM = 2, ENTITIES = 3, PLAYER_NAME = 4;
	/** The values for the selectors. */
	public static final String[] SELECTORS = { "@a", "@p", "@r", "@e", "player_name" };

	/** If selector, its arguments. */
	private ArrayList<TargetArgument> arguments;
	/** If player name, the name. */
	private String name;
	/** The type. Determines what to target.
	 * 
	 * @see Target#PLAYERS_ALL */
	private int type;

	/** Creates a new Target.
	 * 
	 * @param type - Its type. */
	public Target(int type)
	{
		this(type, new ArrayList<TargetArgument>());
	}

	/** Creates a new Target.
	 * 
	 * @param type - Its type.
	 * @param arguments - Its arguments. */
	public Target(int type, ArrayList<TargetArgument> arguments)
	{
		super(Utils.TARGET);
		this.type = type;
		this.arguments = arguments;
		this.name = null;
	}

	/** Creates a new Target, a Player name.
	 * 
	 * @param playerName - The name. */
	public Target(String playerName)
	{
		super(Utils.TARGET);
		this.type = PLAYER_NAME;
		this.name = playerName;
		this.arguments = null;
	}

	/** @return The arguments of this Target. */
	public ArrayList<TargetArgument> getArguments()
	{
		return this.arguments;
	}

	/** @return The Player name targeted. */
	public String getName()
	{
		return this.name;
	}

	/** @return The type of this Target. */
	public int getTargetType()
	{
		return this.type;
	}

	@Override
	public String toCommand()
	{
		if (this.type == PLAYER_NAME) return this.name;

		String command = SELECTORS[this.getTargetType()];
		if (this.arguments.isEmpty()) return command;

		for (int i = 0; i < this.arguments.size(); i++)
		{
			if (i == 0) command += "[";
			else command += ",";
			command += this.arguments.get(i).toCommand();
		}

		return command + "]";
	}

}
