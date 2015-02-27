package commandGenerator.arguments.objects;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

public class SavedObjects
{
	/** Map containing saved Objects. */
	private static Map<Byte, Map<String, Object>> savedObjects = new HashMap<Byte, Map<String, Object>>();
	private static final byte[] types = { CGConstants.OBJECT_ATTRIBUTE, CGConstants.OBJECT_EFFECT, CGConstants.OBJECT_ENCHANT, CGConstants.OBJECT_ENTITY,
			CGConstants.OBJECT_ITEM, CGConstants.OBJECT_BLOCK, CGConstants.OBJECT_TAG_TRADE };
	public static final String[] typeNames = { "attribute", "effect", "enchant", "entity_nbt", "item", "block", "trade" };

	public static void load()
	{
		for (byte type : types)
			savedObjects.put(type, new HashMap<String, Object>());

		try
		{
			Scanner sc = new Scanner(new File(Resources.folder + "savedObjects.txt"));
			while (sc.hasNextLine())
				register(readData(sc.nextLine()));

			sc.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void register(String[] data)
	{
		byte type = Byte.parseByte(data[1]);
		savedObjects.get(type).put(data[0], createObject(type, data[2]));
	}

	private static Object createObject(byte type, String object)
	{
		try
		{
			switch (type)
			{
				case CGConstants.OBJECT_ATTRIBUTE:
					String[] details = object.split(" ");
					return new Attribute((AttributeType) Registry.getObjectFromId(details[0]), Double.parseDouble(details[1]), Integer.parseInt(details[2]));

				case CGConstants.OBJECT_EFFECT:
					String[] details1 = object.split(" ");
					return new Effect((EffectType) Registry.getObjectFromId(details1[0]), Integer.parseInt(details1[1]), Integer.parseInt(details1[2]),
							Boolean.parseBoolean(details1[3]));

				case CGConstants.OBJECT_ENCHANT:
					String[] details2 = object.split(" ");
					return new Enchantment((EnchantType) Registry.getObjectFromId(details2[0]), Integer.parseInt(details2[1]));

				case CGConstants.OBJECT_ENTITY:
					return DataTags.generateListFrom(object);

				case CGConstants.OBJECT_ITEM:
					return ItemStack.generateFrom(new TagCompound() {
						public void askValue()
						{}
					}.setValue(DataTags.generateListFrom(object)));

				case CGConstants.OBJECT_BLOCK:
					String[] base = object.split(" ");
					List<String> sort = new ArrayList<String>();
					for (int i = 0; i < base.length; i++)
					{
						if (i > 2) sort.set(2, sort.get(2) + base[i]);
						else sort.add(base[i]);
					}

					String[] details3 = sort.toArray(new String[0]);
					return ItemStack.generateBlockFrom(details3[0], Integer.parseInt(details3[1]), DataTags.generateListFrom(details3[2]));

				case CGConstants.OBJECT_TAG_TRADE:
					return new TagCompound() {
						public void askValue()
						{}
					}.setValue(DataTags.generateListFrom(object));

				default:
					break;
			}
		} catch (Exception e)
		{
			DisplayHelper.showWarning("editSave");
		}
		return null;
	}

	private static String[] readData(String line)
	{
		String[] elements = line.split(",");
		List<String> data = new ArrayList<String>();

		for (int i = 0; i < elements.length; i++)
		{
			if (i > 2) data.set(2, data.get(2) + elements[i]);
			else data.add(elements[i]);
		}

		return data.toArray(new String[0]);
	}

	public static void add(String name, byte type, Object object)
	{
		savedObjects.get(type).put(name, object);
		String save = createSave(name, type, object);
		if (save == null) {
			DisplayHelper.log("There was a problem saving object : " + name);
			return;
		}
		try
		{
			File file = new File(Resources.folder + "savedObjects.txt");
			Scanner sc = new Scanner(file);
			List<String> prev = new ArrayList<String>();
			while (sc.hasNextLine())
				prev.add(sc.nextLine());
			sc.close();

			file.delete();
			Writer writer = new PrintWriter(file);
			for (int i = 0; i < prev.size(); i++)
				writer.write(prev.get(i));
			writer.write(save);
			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static String createSave(String name, byte type, Object object)
	{
		switch (type)
		{
			case CGConstants.OBJECT_ATTRIBUTE:
				return ((Attribute) object).save();

			case CGConstants.OBJECT_EFFECT:
				return ((Effect) object).commandStructure();

			case CGConstants.OBJECT_ENCHANT:
				return ((Enchantment) object).getType().getId() + ((Enchantment) object).getLevel();

			case CGConstants.OBJECT_ENTITY:
				return new TagList() {
					public void askValue()
					{}
				}.setValue((List<Tag>) object).commandStructure();

			case CGConstants.OBJECT_ITEM:
				return ((ItemStack) object).toNBT("").commandStructure();

			case CGConstants.OBJECT_BLOCK:
				ItemStack stack = ((ItemStack) object);
				return stack.getItem().getId() + " " + stack.getDamage() + " " + stack.getTag().commandStructure();

			case CGConstants.OBJECT_TAG_TRADE:
				return ((TagCompound) object).commandStructure();

			default:
				break;
		}
		return null;
	}

}
