package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.main.DisplayHelper;

public class ObjectCreator
{
	/** Generates an Achievement from its command structure.
	 * 
	 * @param data
	 *            - <i>String</i> - The command structure. */
	public static Achievement generateAchievement(String data)
	{
		if (data.contains("achievement.")) data = data.substring("achievement.".length());

		Achievement achievement = (Achievement) Registry.getObjectFromId(data);
		if (achievement != null) DisplayHelper.log("Created achievement : " + achievement.getId());
		return achievement;
	}

	/** Generates Coordinates from command structure.
	 * 
	 * @param x
	 *            - <i>String</i> - The X Coordinate.
	 * @param y
	 *            - <i>String</i> - The Y Coordinate.
	 * @param z
	 *            - <i>String</i> - The Z Coordinate. */
	public static Coordinates generateCoordinates(String x, String y, String z)
	{
		double cx, cy, cz;
		boolean[] relative = { x.startsWith("~"), y.startsWith("~"), z.startsWith("~") };

		try
		{

			if (relative[0]) x = x.substring(1);
			if (relative[1]) y = y.substring(1);
			if (relative[2]) z = z.substring(1);

			if (!x.equals("")) cx = Double.parseDouble(x);
			else cx = 0;
			if (!y.equals("")) cy = Double.parseDouble(y);
			else cy = 0;
			if (!z.equals("")) cz = Double.parseDouble(z);
			else cz = 0;

			Coordinates coords = new Coordinates(cx, cy, cz, relative, true);
			DisplayHelper.log("Created coordinates : " + coords.commandStructure());
			return coords;

		} catch (Exception e)
		{
			DisplayHelper.log("There was an error while creating coordinates : " + x + " " + y + " " + z);
			return null;
		}

	}

	/** Generates Coordinates with rotations from command structure.
	 * 
	 * @param x
	 *            - <i>String</i> - The X Coordinate.
	 * @param y
	 *            - <i>String</i> - The Y Coordinate.
	 * @param z
	 *            - <i>String</i> - The Z Coordinate.
	 * @param rotX
	 *            - <i>float</i> - The X Rotation.
	 * @param rotY
	 *            - <i>float</i> - The Y Rotation. */
	public static Coordinates generateCoordinatesWithRotations(String x, String y, String z, float rotX, float rotY)
	{
		double cx, cy, cz;
		boolean[] relative = { x.startsWith("~"), y.startsWith("~"), z.startsWith("~") };

		try
		{

			if (relative[0]) x = x.substring(1);
			if (relative[1]) y = y.substring(1);
			if (relative[2]) z = z.substring(1);

			if (!x.equals("")) cx = Double.parseDouble(x);
			else cx = 0;
			if (!y.equals("")) cy = Double.parseDouble(y);
			else cy = 0;
			if (!z.equals("")) cz = Double.parseDouble(z);
			else cz = 0;

			Coordinates coords = new Coordinates(cx, cy, cz, rotX, rotY, relative, true);
			DisplayHelper.log("Created coordinates : " + coords.commandStructure());
			return coords;

		} catch (Exception e)
		{
			DisplayHelper.log("There was an error while creating coordinates : " + x + " " + y + " " + z);
			return null;
		}

	}

	/** Generates a new ItemStack from the following data :
	 * 
	 * @param id
	 *            - <i>String</i> - The Item ID.
	 * @param damage
	 *            - <i>int</i> - The Item damage.
	 * @param nbt
	 *            - <i>ArrayList:Tag</i> - The NBT Tags as a list. */
	public static ItemStack generateBlockStack(String id, int damage, List<Tag> nbt)
	{
		ItemStack stack = generateItemStack(id, damage, -1, nbt, -1);
		return new ItemStack((Item) Registry.getObjectFromId(id), stack.getDamage(), -1, stack.getTag(), -1);
	}

	/** Generates a new ItemStack from the following data :
	 * 
	 * @param id
	 *            - <i>String</i> - The Item ID.
	 * @param damage
	 *            - <i>int</i> - The Item damage.
	 * @param count
	 *            - <i>int</i> - The number of Items.
	 * @param nbt
	 *            - <i>ArrayList:Tag</i> - The NBT Tags as a list.
	 * @param slot
	 *            - <i>int</i> - The slot of this stack. */
	public static ItemStack generateItemStack(String id, int damage, int count, List<Tag> nbt, int slot)
	{
		Item item = (Item) Registry.getObjectFromId(id);
		if (item.isBlock()) item = (Item) Registry.getObjectFromId(id + "_item");
		if (item == null) item = (Item) Registry.getObjectFromId(id);
		TagCompound tag = new TagCompound("tag") {
			@Override
			public void askValue()
			{}
		};
		tag.setValue(nbt);

		DisplayHelper.log("Created Item : " + count + " " + ((Item) Registry.getObjectFromId(id)).getName(damage) + " in slot " + slot);
		return new ItemStack(item, damage, count, tag, slot);
	}

	/** Generates an ItemStack from a TagCompound.
	 * 
	 * @param tag
	 *            - <i>TagCompount</i> - The tag used to generate. */
	public static ItemStack generateItemStack(TagCompound tag)
	{
		Item item = (Item) Registry.getObjectFromId("stone");
		int damage = 0, count = 1, slot = -1;
		TagCompound nbt = new TagCompound() {
			@Override
			public void askValue()
			{}
		};
		for (int i = 0; i < tag.size(); i++)
		{
			Tag part = tag.get(i);
			if (part.getId().equals("id")) item = (Item) Registry.getObjectFromId(((TagString) part).getValue());
			if (part.getId().equals("Damage")) damage = ((TagInt) part).getValue();
			if (part.getId().equals("Count")) count = ((TagInt) part).getValue();
			if (part.getId().equals("Slot")) slot = ((TagInt) part).getValue();
			if (part.getId().equals("tag")) nbt = ((TagCompound) part);
		}

		return new ItemStack(item, damage, count, nbt, slot);
	}

	/** Generates a target from a generated command.
	 * 
	 * @param text
	 *            - <i>String</i> - The generated command. */
	public static Target generateTarget(String text)
	{
		if (!text.startsWith("@"))
		{
			DisplayHelper.log("Created entity : " + text);
			return new Target(text);
		}

		try
		{
			int type = getTargetType(text.substring(0, 2));
			List<String[]> selectorList = new ArrayList<String[]>();
			if (text.length() > 2)
			{
				String[] selectorsText = text.substring(3, text.length() - 1).split(",");
				for (String data : selectorsText)
				{
					String id = data.split("=")[0];
					String value = data.split("=")[1];
					String not = "false";
					if (value.startsWith("!"))
					{
						not = "true";
						value = value.substring(1);
					}
					selectorList.add(new String[] { id, value, not });
				}
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
	public static int getTargetType(String sel)
	{
		if (sel.equals("@a")) return Target.ALL;
		if (sel.equals("@p")) return Target.CLOSEST;
		if (sel.equals("@e")) return Target.ENTITY;
		if (sel.equals("@r")) return Target.RANDOM;
		return Target.PLAYER;
	}

}
