package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commandGenerator.Generator;
import commandGenerator.arguments.command.Command;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Settings;

public class Registry
{
	public Registry()
	{
		this.commands = new HashMap<String, Command>();
		this.list = new HashMap<String, ObjectBase[]>();
		this.objects = new HashMap<Byte, Map<String, ObjectBase>>();
		this.effects = new HashMap<Integer, EffectType>();
		this.enchants = new HashMap<Integer, EnchantType>();
		this.durabilityList = new ArrayList<String>();
	}

	/** Map containing registered commands. */
	private Map<String, Command> commands;

	/** Map containing registered lists. */
	private Map<String, ObjectBase[]> list;

	/** Map containing registered Objects. */
	private Map<Byte, Map<String, ObjectBase>> objects;

	/** Map containing registered Effect Types. */
	private Map<Integer, EffectType> effects;

	/** Map containing registered Enchantment Types. */
	private Map<Integer, EnchantType> enchants;

	/** List containing all items which have durability */
	public List<String> durabilityList;

	/** Displays the list of all registered objects of the specified type.
	 * 
	 * @param objectType
	 *            - <i>byte</i> - The Objects' type. */
	private void displayList(byte objectType)
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
	public void end()
	{
		registerNames();
		registerTextures();
		registerList("allEntities", objects.get(ObjectBase.ENTITY).keySet().toArray(new String[0]));
		displayList(ObjectBase.ITEM);
		displayList(ObjectBase.ENTITY);
		displayList(ObjectBase.ENCHANTMENT);
		displayList(ObjectBase.EFFECT);
		displayList(ObjectBase.ACHIEVEMENT);
		displayList(ObjectBase.ATTRIBUTE);
		displayList(ObjectBase.SOUND);
		displayList(ObjectBase.PARTICLE);
	}

	public void registerNames()
	{
		for (ObjectBase item : getObjectList(ObjectBase.ITEM))
			item.registerLang();
		for (ObjectBase item : getObjectList(ObjectBase.ENTITY))
			item.registerLang();
		for (ObjectBase item : getObjectList(ObjectBase.ENCHANTMENT))
			item.registerLang();
		for (ObjectBase item : getObjectList(ObjectBase.EFFECT))
			item.registerLang();
		for (ObjectBase item : getObjectList(ObjectBase.ACHIEVEMENT))
			item.registerLang();
		for (ObjectBase item : getObjectList(ObjectBase.ATTRIBUTE))
			item.registerLang();
		for (ObjectBase item : getObjectList(ObjectBase.PARTICLE))
			item.registerLang();
	}

	private void registerTextures()
	{
		for (ObjectBase item : getObjectList(ObjectBase.ITEM))
			((Item) item).registerTextures();
		for (ObjectBase entity : getObjectList(ObjectBase.ENTITY))
			((Entity) entity).registerTexture();
		for (ObjectBase effect : getObjectList(ObjectBase.EFFECT))
			((EffectType) effect).registerTexture();
	}

	public boolean exists(String id, byte objectType)
	{
		return objects.get(objectType).containsKey(id);
	}

	/** Returns a Command from its ID.
	 * 
	 * @param id
	 *            - <i>String</i> - The Command's ID. */
	public Command getCommandFromId(String id)
	{
		return commands.get(id);
	}

	/** Returns an array containing all registered commands. */
	public Command[] getCommands()
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
	public ObjectBase[] getList(String listName)
	{
		if (list.get(listName) == null || list.get(listName).length == 0)
		{
			DisplayHelper.log(listName + " isn't the ID of any list.");
			return new ObjectBase[0];
		}

		ObjectBase[] found = list.get(listName);
		List<ObjectBase> toSort = new ArrayList<ObjectBase>();
		for (ObjectBase o : found)
			toSort.add(o);

		if (Generator.opt.getSortType().equals(Settings.IDS))
		{
			if (found[0].getType() == ObjectBase.ITEM || found[0].getType() == ObjectBase.ENCHANTMENT || found[0].getType() == ObjectBase.EFFECT) sortIdsNum(toSort);
			else sortIds(toSort);
		} else sortNames(toSort);

		return toSort.toArray(new ObjectBase[0]);
	}

	/** Returns the Object corresponding to this ID.
	 * 
	 * @param id
	 *            - <i>String</i> - The Object's ID. */
	public ObjectBase getObjectFromId(String id)
	{
		if (id.startsWith("minecraft:") || id.startsWith("minecraft.")) id = id.substring("minecraft:".length());

		ObjectBase object = objects.get(ObjectBase.ITEM).get(id);
		if (object == null) object = objects.get(ObjectBase.ACHIEVEMENT).get(id);
		if (object == null) object = objects.get(ObjectBase.ATTRIBUTE).get(id);
		if (object == null) object = objects.get(ObjectBase.EFFECT).get(id);
		if (object == null) object = objects.get(ObjectBase.ENCHANTMENT).get(id);
		if (object == null) object = objects.get(ObjectBase.ENTITY).get(id);
		if (object == null) object = objects.get(ObjectBase.PARTICLE).get(id);
		if (object == null) object = objects.get(ObjectBase.SOUND).get(id);

		 if (object == null) DisplayHelper.log(id + " isn't the ID of any object.");
		return object;
	}

	/** Returns the Object corresponding to this ID.
	 * 
	 * @param type
	 *            - <i>byte</i> - The type of the Object
	 * @param id
	 *            - <i>int</i> - The Object's ID. */
	public ObjectBase getObjectFromIdNum(byte type, int id)
	{
		switch (type)
		{
			case ObjectBase.EFFECT:
				return effects.get(id);

			case ObjectBase.ENCHANTMENT:
				return enchants.get(id);

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
	public ObjectBase[] getObjectList(byte type)
	{
		ObjectBase[] objectArray = objects.get(type).values().toArray(new ObjectBase[0]);
		List<ObjectBase> objectList = new ArrayList<ObjectBase>();
		for (int i = 0; i < objectArray.length; i++)
			objectList.add(objectArray[i]);

		if (Generator.opt.getSortType().equals(Settings.IDS))
		{
			if (type == ObjectBase.ITEM || type == ObjectBase.ENCHANTMENT || type == ObjectBase.EFFECT) sortIdsNum(objectList);
			else sortIds(objectList);
		} else sortNames(objectList);
		return objectList.toArray(new ObjectBase[0]);
	}

	/** Registers a command
	 * 
	 * @param command
	 *            - <i>Command</i> - The Command to register. */

	public void registerCommand(Command command)
	{
		commands.put(command.getId(), command);
	}

	/** Registers a list.
	 * 
	 * @param id
	 *            - <i>String</i> - The list's ID.
	 * @param listNew
	 *            - <i>String[]</i> - The list itself. */
	public void registerList(String id, String[] listNew)
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
	public void registerObject(byte type, ObjectBase object)
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

	/** Sorts the Object list according to these Objects' names.
	 * 
	 * @param objectType
	 *            - <i>List:ObjectBase</i> - The list of Objects to sort. */
	private static void sortNames(List<ObjectBase> list)
	{
		list.sort(new Comparator<ObjectBase>() {
			@Override
			public int compare(ObjectBase o1, ObjectBase o2)
			{
				int diff = o1.getName().compareTo(o2.getName());
				if (o1 instanceof Item) diff = o1.getId().compareTo(o2.getId());
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
				if (o1.getType() == ObjectBase.ITEM) diff = ((Item) o1).getIdNum() - ((Item) o2).getIdNum();
				if (o1.getType() == ObjectBase.ENCHANTMENT) diff = ((EnchantType) o1).getIdNum() - ((EnchantType) o2).getIdNum();
				if (o1.getType() == ObjectBase.EFFECT) diff = ((EffectType) o1).getIdNum() - ((EffectType) o2).getIdNum();
				if (diff < 0) diff = -1;
				if (diff > 0) diff = 1;
				return diff;
			}
		});
	}

	public void registerEffect(EffectType effectType)
	{
		effects.put(effectType.getIdNum(), effectType);
	}

	public void registerEnchant(EnchantType enchantType)
	{
		enchants.put(enchantType.getIdNum(), enchantType);
	}

	/** Returns an array containing all registered Entities, except Player. */
	public Entity[] getListNoPlayer()
	{
		List<Entity> entityList = new ArrayList<Entity>();
		ObjectBase[] list = getObjectList(ObjectBase.ENTITY);
		for (int i = 0; i < list.length; i++)
			if (!list[i].getId().equals("Player")) entityList.add((Entity) list[i]);

		return entityList.toArray(new Entity[0]);
	}

	public void registerDurableItem(Item item)
	{
		durabilityList.add(item.getId());
	}

	public Particle getParticleFrom(String id)
	{
		Particle part = (Particle) getObjectFromId(id);
		if (part != null) return part;

		if (id.startsWith("blockcrack")) return (Particle) getObjectFromId("blockcrack");
		return (Particle) getObjectFromId("iconcrack");
	}

}
