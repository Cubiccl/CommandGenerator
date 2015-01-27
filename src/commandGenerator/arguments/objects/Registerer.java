package commandGenerator.arguments.objects;

import java.util.HashMap;
import java.util.Map;

import commandGenerator.main.DisplayHelper;

public class Registerer
{

	/** Map containing registered commands. */
	private static Map<String, Command> commands = new HashMap<String, Command>();
	/** Map containing registered lists. */
	private static Map<String, ObjectBase[]> list = new HashMap<String, ObjectBase[]>();
	/** Map containing registered Objects. */
	private static Map<String, Map<String, ObjectBase>> objects = new HashMap<String, Map<String, ObjectBase>>();

	/** Registers a list.
	 * 
	 * @param id
	 *            - String - The list's ID.
	 * @param listNew
	 *            - String[] - The list itself. */
	public static void registerList(String id, String[] listNew)
	{
		ObjectBase[] objects = new ObjectBase[listNew.length];
		for (int i = 0; i < objects.length; i++)
		{
			objects[i] = ObjectBase.getObjectFromId(listNew[i]);
		}
		list.put(id, objects);

		DisplayHelper.log("New list registered : " + id);
	}

	/** Returns the Object list from its name.
	 * 
	 * @param listName
	 *            - String - The List name. */
	public static ObjectBase[] getList(String listName)
	{
		if (list.get(listName) == null)
		{
			DisplayHelper.log(listName + " isn't the ID of any list.");
			return new ObjectBase[0];
		}
		return list.get(listName);
	}

	/** Returns a Command from its ID.
	 * 
	 * @param id
	 *            - String - The Command's ID. */
	public static Command getCommandFromId(String id)
	{
		return commands.get(id);
	}

	/** Registers a command
	 * 
	 * @param command
	 *            - Command - The Command to register. */

	public static void registerCommand(Command command)
	{
		commands.put(command.getId(), command);
	}

	/** Returns an array containing all registered commands. */
	public static Command[] getCommandArray()
	{
		return commands.values().toArray(new Command[0]);
	}

}
