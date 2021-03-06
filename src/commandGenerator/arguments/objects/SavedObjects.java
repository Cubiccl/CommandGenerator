package commandGenerator.arguments.objects;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import commandGenerator.Generator;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.components.panel.LoadPanel;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

public class SavedObjects
{
	/** Map containing saved Objects. */
	private static Map<Byte, Map<String, Object>> savedObjects = new HashMap<Byte, Map<String, Object>>();
	public static final String[] typeNames = { "attribute", "effect", "enchant", "item", "block", "entity", "coordinate", "trade", "target" };
	public static final byte[] types = { ObjectBase.ATTRIBUTE, ObjectBase.EFFECT, ObjectBase.ENCHANTMENT, ObjectBase.ITEM,
			ObjectBase.BLOCK, ObjectBase.ENTITY, ObjectBase.COORD, ObjectBase.TAG_TRADE, ObjectBase.TARGET };

	public static void add(String name, byte type, Object object)
	{
		savedObjects.get(type).put(name, object);
		save();
	}

	public static Object askObjectToLoad(byte type)
	{
		LoadPanel panel = new LoadPanel(type);
		if (DisplayHelper.showQuestion(panel, Generator.translate("GENERAL:load"))) return null;
		return panel.getSelection();
	}

	private static Object createObject(byte type, String textData)
	{
		try
		{
			switch (type)
			{
				case ObjectBase.ATTRIBUTE:
					String[] details = textData.split(" ");
					return new Attribute((AttributeType) Generator.registry.getObjectFromId(details[0]), Double.parseDouble(details[1]), Integer.parseInt(details[2]));

				case ObjectBase.EFFECT:
					String[] details1 = textData.split(" ");
					return new Effect((EffectType) Generator.registry.getObjectFromId(details1[0]), Integer.parseInt(details1[2]), Integer.parseInt(details1[1]),
							!Boolean.parseBoolean(details1[3]));

				case ObjectBase.ENCHANTMENT:
					String[] details2 = textData.split(" ");
					return new Enchantment((EnchantType) Generator.registry.getObjectFromId(details2[0]), Integer.parseInt(details2[1]));

				case ObjectBase.ENTITY:
					return DataTags.generateListFrom(textData);

				case ObjectBase.ITEM:
					String[] base = textData.split(" ");
					List<String> sort = new ArrayList<String>();
					for (int i = 0; i < base.length; i++)
					{
						if (i > 4) sort.set(4, sort.get(4) + " " + base[i]);
						else sort.add(base[i]);
					}

					String[] details3 = sort.toArray(new String[0]);
					return ObjectCreator.generateItemStack(details3[0], Integer.parseInt(details3[1]), Integer.parseInt(details3[2]),
							DataTags.generateListFrom(details3[4]), Integer.parseInt(details3[3]));

				case ObjectBase.BLOCK:
					String[] base2 = textData.split(" ");
					List<String> sort2 = new ArrayList<String>();
					for (int i = 0; i < base2.length; i++)
					{
						if (i > 2) sort2.set(2, sort2.get(2) + base2[i]);
						else sort2.add(base2[i]);
					}

					String[] details4 = sort2.toArray(new String[0]);
					return ObjectCreator.generateBlockStack(details4[0], Integer.parseInt(details4[1]), DataTags.generateListFrom(details4[2]));

				case ObjectBase.COORD:
					String[] details5 = textData.split(" ");
					if (details5.length == 7) return new Coordinates(Double.parseDouble(details5[0]), Double.parseDouble(details5[1]),
							Double.parseDouble(details5[2]), new boolean[] { Boolean.parseBoolean(details5[3]), Boolean.parseBoolean(details5[4]),
									Boolean.parseBoolean(details5[5]) }, Boolean.parseBoolean(details5[6]));
					return new Coordinates(Double.parseDouble(details5[0]), Double.parseDouble(details5[1]), Double.parseDouble(details5[2]),
							Float.parseFloat(details5[3]), Float.parseFloat(details5[4]), new boolean[] { Boolean.parseBoolean(details5[5]),
									Boolean.parseBoolean(details5[6]), Boolean.parseBoolean(details5[7]) }, Boolean.parseBoolean(details5[8]));

				case ObjectBase.TAG_TRADE:
					return new TagCompound() {
						@Override
						public void askValue()
						{}
					}.setValue(DataTags.generateListFrom(textData));

				case ObjectBase.TARGET:
					return ObjectCreator.generateTarget(textData);

				default:
					break;
			}
		} catch (Exception e)
		{
			DisplayHelper.showWarning("WARNING:edit_save");
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static String createSave(String name, byte type, Object object)
	{
		String save = "";
		switch (type)
		{
			case ObjectBase.ATTRIBUTE:
				save = ((Attribute) object).save();
				break;

			case ObjectBase.EFFECT:
				save = ((Effect) object).commandStructure();
				break;

			case ObjectBase.ENCHANTMENT:
				save = ((Enchantment) object).getEnchantType().getId() + " " + ((Enchantment) object).getLevel();
				break;

			case ObjectBase.ENTITY:
				save = new TagCompound() {
					@Override
					public void askValue()
					{}
				}.setValue((List<Tag>) object).commandStructure();
				break;

			case ObjectBase.ITEM:
				ItemStack stack = ((ItemStack) object);
				save = stack.getItem().getId() + " " + stack.getDamage() + " " + stack.getCount() + " " + stack.getSlot() + " "
						+ stack.getTag().commandStructure().substring(stack.getTag().getId().length() + 1);
				break;

			case ObjectBase.BLOCK:
				ItemStack stack2 = ((ItemStack) object);
				save = stack2.getItem().getId() + " " + stack2.getDamage() + " "
						+ stack2.getTag().commandStructure().substring(stack2.getTag().getId().length() + 1);
				break;

			case ObjectBase.COORD:
				save = ((Coordinates) object).save();
				break;

			case ObjectBase.TAG_TRADE:
				save = ((TagCompound) object).commandStructure();
				break;

			case ObjectBase.TARGET:
				save = ((Target) object).commandStructure();
				break;

			default:
				break;
		}
		return name + " ; " + type + " ; " + save;
	}

	public static Map<String, Object> getList(byte type)
	{
		return savedObjects.get(type);
	}

	public static void load()
	{
		for (byte type : types)
			savedObjects.put(type, new HashMap<String, Object>());

		try
		{
			Scanner sc = new Scanner(new File(Resources.folder + "savedObjects.txt"));
			while (sc.hasNextLine())
			{
				String line = sc.nextLine();
				if (line != null && !line.equals("")) register(readData(line));
			}

			sc.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String[] readData(String line)
	{
		String[] elements = line.split(" ; ");
		List<String> data = new ArrayList<String>();

		for (int i = 0; i < elements.length; i++)
		{
			if (i > 2) data.set(2, data.get(2) + " ; " + elements[i]);
			else data.add(elements[i]);
		}

		return data.toArray(new String[0]);
	}

	private static void register(String[] data)
	{
		byte type = Byte.parseByte(data[1]);
		savedObjects.get(type).put(data[0], createObject(type, data[2]));
	}

	public static void remove(byte type, String name)
	{
		savedObjects.get(type).remove(name);
		save();
	}

	private static void save()
	{
		File file = new File(Resources.folder + "savedObjects.txt");
		try
		{
			Writer writer = new PrintWriter(file);
			for (int i = 0; i < types.length; i++)
			{
				String[] names = savedObjects.get(types[i]).keySet().toArray(new String[0]);
				Object[] values = savedObjects.get(types[i]).values().toArray(new Object[0]);
				for (int j = 0; j < names.length; j++)
				{
					String save = createSave(names[j], types[i], values[j]);
					if (save == null) DisplayHelper.log("There was a problem saving object : " + names[i]);
					else writer.write(save + "\n");
				}
			}
			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
