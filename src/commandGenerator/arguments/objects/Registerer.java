package commandGenerator.arguments.objects;

import java.util.HashMap;
import java.util.Map;

import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

public class Registerer
{

	/** Map containing registered commands. */
	private static Map<String, Command> commands = new HashMap<String, Command>();
	/** Map containing registered lists. */
	private static Map<String, ObjectBase[]> list = new HashMap<String, ObjectBase[]>();
	/** Map containing registered Objects. */
	private static Map<Byte, Map<String, ObjectBase>> objects = new HashMap<Byte, Map<String, ObjectBase>>();

	/** Registers a list.
	 * 
	 * @param id
	 *            - <i>String</i> - The list's ID.
	 * @param listNew
	 *            - <i>String[]</i> - The list itself. */
	public static void registerList(String id, String[] listNew)
	{
		ObjectBase[] objects = new ObjectBase[listNew.length];
		for (int i = 0; i < objects.length; i++)
		{
			objects[i] = getObjectFromId(listNew[i]);
		}
		list.put(id, objects);

		DisplayHelper.log("New list registered : " + id);
	}

	/** Returns the Object list from its name.
	 * 
	 * @param listName
	 *            - <i>String</i> - The List name. */
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
	 *            - <i>String</i> - The Command's ID. */
	public static Command getCommandFromId(String id)
	{
		return commands.get(id);
	}

	/** Registers a command
	 * 
	 * @param command
	 *            - <i>Command</i> - The Command to register. */

	public static void registerCommand(Command command)
	{
		commands.put(command.getId(), command);
	}

	/** Returns an array containing all registered commands. */
	public static Command[] getCommandArray()
	{
		return commands.values().toArray(new Command[0]);
	}

	/** Registers an Object
	 * 
	 * @param type
	 *            - <i>byte</i> - The Object type.
	 * @param object
	 *            - <i>ObjectBase</i> - The Object to register. */
	public static void registerObject(byte type, ObjectBase object)
	{
		if (object.getId().equals("")) return;
		if (objects.get(type) == null) objects.put(type, new HashMap<String, ObjectBase>());

		objects.get(type).put(object.getId(), object);
		DisplayHelper.log("Registered " + CGConstants.TYPES[type] + " : " + object.getId());
	}

	/** Returns the Object corresponding to this ID.
	 * 
	 * @param id
	 *            - <i>String</i> - The Object's ID. */
	public static ObjectBase getObjectFromId(String id)
	{
		if (id.startsWith("minecraft:") || id.startsWith("minecraft.")) id = id.substring("minecraft:".length());

		ObjectBase object = objects.get(CGConstants.OBJECT_ITEM).get(id);
		if (object == null) object = objects.get(CGConstants.OBJECT_ACHIEVEMENT).get(id);
		if (object == null) object = objects.get(CGConstants.OBJECT_ATTRIBUTE).get(id);
		if (object == null) object = objects.get(CGConstants.OBJECT_EFFECT).get(id);
		if (object == null) object = objects.get(CGConstants.OBJECT_ENCHANT).get(id);
		if (object == null) object = objects.get(CGConstants.OBJECT_ENTITY).get(id);
		if (object == null) object = objects.get(CGConstants.OBJECT_PARTICLE).get(id);
		if (object == null) object = objects.get(CGConstants.OBJECT_SOUND).get(id);

		if (object == null) DisplayHelper.log(id + " isn't the ID of any object.");
		return object;
	}

	/** Returns the Object corresponding to this ID.
	 * 
	 * @param type
	 *            - <i>byte</i> - The type of the Object
	 * @param id
	 *            - <i>int</i> - The Object's ID. */
	public static ObjectBase getObjectFromIdNum(byte type, int id)
	{
		switch (type)
		{
			case CGConstants.OBJECT_EFFECT:
				return EffectType.getEffectFromIdNum(id);

			case CGConstants.OBJECT_ENCHANT:
				return EnchantType.getEnchantFromIdNum(id);

			default:
				DisplayHelper.log(id + " isn't the ID of any object.");
				break;
		}
		return null;
	}

	/** Returns a list containing all registered Objects of the specified type.
	 * 
	 * @param type
	 *            - <i>byte</i> - The Objects' type. */
	public static ObjectBase[] getObjectList(byte type)
	{
		return objects.get(type).values().toArray(new ObjectBase[0]);
	}

	/** Creates the Entities list. */
	public static void setupEntityList()
	{
		registerList("allEntities", objects.get(CGConstants.OBJECT_ENTITY).keySet().toArray(new String[0]));
	}

}
