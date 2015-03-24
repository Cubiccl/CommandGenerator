package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commandGenerator.arguments.command.Command;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

public class Registry
{

	/** Map containing registered commands. */
	private static Map<String, Command> commands = new HashMap<String, Command>();
	/** Map containing registered lists. */
	private static Map<String, ObjectBase[]> list = new HashMap<String, ObjectBase[]>();
	/** Map containing registered Objects. */
	private static Map<Byte, Map<String, ObjectBase>> objects = new HashMap<Byte, Map<String, ObjectBase>>();

	/** Displays the list of all registered objects of the specified type.
	 * 
	 * @param objectType
	 *            - <i>byte</i> - The Objects' type. */
	private static void displayList(byte objectType)
	{
		String log = "Registered " + CGConstants.TYPES[objectType] + "s : ";
		ObjectBase[] items = getObjectList(objectType);
		int size = 0;
		for (int i = 0; i < objects.get(objectType).size(); i++)
		{
			log += items[i].getId() + ", ";
			size += items[i].getId().length();
			if (size >= 400)
			{
				log += "\n";
				size = 0;
			}
		}

		log = log.substring(0, log.length() - 2) + ".";

		DisplayHelper.log(log);
	}

	/** Finalizes the registration. */
	public static void end()
	{
		registerList("allEntities", objects.get(CGConstants.OBJECT_ENTITY).keySet().toArray(new String[0]));
		displayList(CGConstants.OBJECT_ITEM);
		displayList(CGConstants.OBJECT_ENTITY);
		displayList(CGConstants.OBJECT_ENCHANT);
		displayList(CGConstants.OBJECT_EFFECT);
		displayList(CGConstants.OBJECT_ACHIEVEMENT);
		displayList(CGConstants.OBJECT_ATTRIBUTE);
		displayList(CGConstants.OBJECT_SOUND);
		displayList(CGConstants.OBJECT_PARTICLE);

	}

	public static boolean exists(String id, byte objectType)
	{
		return objects.get(objectType).containsKey(id);
	}

	/** Returns a Command from its ID.
	 * 
	 * @param id
	 *            - <i>String</i> - The Command's ID. */
	public static Command getCommandFromId(String id)
	{
		return commands.get(id);
	}

	/** Returns an array containing all registered commands. */
	public static Command[] getCommands()
	{
		Command[] commandArray = commands.values().toArray(new Command[0]);
		List<Command> commandList = new ArrayList<Command>();
		for (int i = 0; i < commandArray.length; i++)
			commandList.add(commandArray[i]);

		commandList.sort(new Comparator<Command>() {
			@Override
			public int compare(Command c1, Command c2)
			{
				int diff = c1.getId().compareTo(c2.getId());
				if (diff < 0) diff = -1;
				if (diff > 0) diff = 1;
				return diff;
			}
		});

		return commandList.toArray(new Command[0]);
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

		// if (object == null) DisplayHelper.log(id + " isn't the ID of any object.");
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
		ObjectBase[] objectArray = objects.get(type).values().toArray(new ObjectBase[0]);
		List<ObjectBase> objectList = new ArrayList<ObjectBase>();
		for (int i = 0; i < objectArray.length; i++)
			objectList.add(objectArray[i]);

		if (type == CGConstants.OBJECT_ITEM || type == CGConstants.OBJECT_ENCHANT || type == CGConstants.OBJECT_EFFECT) sortIdsNum(objectList);
		else sortIds(objectList);
		return objectList.toArray(new ObjectBase[0]);
	}

	/** Registers a command
	 * 
	 * @param command
	 *            - <i>Command</i> - The Command to register. */

	public static void registerCommand(Command command)
	{
		commands.put(command.getId(), command);
	}

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
			objects[i] = getObjectFromId(listNew[i]);

		list.put(id, objects);
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
	}

	/** Sorts the Object list according to these Objects' IDs.
	 * 
	 * @param objectType
	 *            - <i>List:ObjectBase</i> - The list of Objects to sort. */
	private static void sortIds(List<ObjectBase> list)
	{
		list.sort(new Comparator<ObjectBase>() {
			@Override
			public int compare(ObjectBase o1, ObjectBase o2)
			{
				int diff = o1.getId().compareTo(o2.getId());
				if (diff < 0) diff = -1;
				if (diff > 0) diff = 1;
				return diff;
			}
		});
	}

	/** Sorts the Object list according to these Objects' numerical IDs.
	 * 
	 * @param objectType
	 *            - <i>List:ObjectBase</i> - The list of Objects to sort. */
	private static void sortIdsNum(List<ObjectBase> list)
	{
		list.sort(new Comparator<ObjectBase>() {
			@Override
			public int compare(ObjectBase o1, ObjectBase o2)
			{
				int diff = 0;
				if (o1.getType() == CGConstants.OBJECT_ITEM) diff = ((Item) o1).getIdNum() - ((Item) o2).getIdNum();
				if (o1.getType() == CGConstants.OBJECT_ENCHANT) diff = ((EnchantType) o1).getIdNum() - ((EnchantType) o2).getIdNum();
				if (o1.getType() == CGConstants.OBJECT_EFFECT) diff = ((EffectType) o1).getIdNum() - ((EffectType) o2).getIdNum();
				if (diff < 0) diff = -1;
				if (diff > 0) diff = 1;
				return diff;
			}
		});
	}

}
