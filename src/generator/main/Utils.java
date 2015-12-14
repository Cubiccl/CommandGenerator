package generator.main;

import generator.CommandGenerator;

import java.awt.Color;
import java.awt.Font;

/** Contains various constants and and methods. */
public final class Utils
{
	/** Background Color of a Component being clicked. */
	public static final Color BACKGROUND_CLICKED = new Color(250, 220, 220);
	/** Background Color of a Component being hovered. */
	public static final Color BACKGROUND_HOVERED = new Color(220, 250, 220);
	/** Background Color of a regular Component. */
	public static final Color BACKGROUND_NORMAL = new Color(220, 220, 250);
	/** Border Color of a Component. */
	public static final Color BORDER_COLOR = Color.GRAY;
	/** Font to be used by most Components. */
	public static final Font font = new Font("Dialog", Font.BOLD, 13);

	/** Object types :
	 * <ul>
	 * <li>ITEM = 0;</li>
	 * <li>BLOCK = 1;</li>
	 * <li>ENTITY = 2;</li>
	 * <li>EFFECT = 3;</li>
	 * <li>ENCHANTMENT = 4;</li>
	 * <li>ACHIEVEMENT = 5;</li>
	 * <li>ATTRIBUTE = 6;</li>
	 * <li>PARTICLE = 7;</li>
	 * <li>SOUND = 8;</li>
	 * <li>COMMAND = 100;</li>
	 * <li>STRUCTURE = 101;</li>
	 * </ul> */
	public static final int ITEM = 0, BLOCK = 1, ENTITY = 2, EFFECT = 3, ENCHANTMENT = 4, ACHIEVEMENT = 5, ATTRIBUTE = 6, PARTICLE = 7, SOUND = 8,
			COMMAND = 100, STRUCTURE = 101;
	/** Names for Objects types. */
	public static final String[] OBJECT_TYPES_NAMES = { "item", "block", "entity", "effect", "enchantment", "achievement", "attribute", "particle", "sound" };

	/** @param type - The type of the object.
	 * @return - Its name.
	 * @see Utils#ITEM */
	public static String getObjectTypeName(int type)
	{
		return CommandGenerator.translate("GUI:object." + getObjectTypeNameId(type));
	}

	/** @param type - The type of the object.
	 * @return - Its name ID.
	 * @see Utils#ITEM */
	public static String getObjectTypeNameId(int type)
	{
		if (type == COMMAND) return "command";
		if (type == STRUCTURE) return "structure";
		if (type < 0 || type >= OBJECT_TYPES_NAMES.length) return "object";
		return OBJECT_TYPES_NAMES[type];
	}

	/** @param array - The array.
	 * @param separator - A String to put between each component of the array.
	 * @return A String representation of the array. */
	public static String toString(String[] array, String separator)
	{
		String string = "";
		for (int i = 0; i < array.length; i++)
		{
			if (i != 0) string += separator;
			string += array[i];
		}
		return string;
	}

}
