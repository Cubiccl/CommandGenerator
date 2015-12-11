package generator.main;

import generator.CommandGenerator;

import java.awt.Color;
import java.awt.Font;

public final class Constants
{
	public static final Color BACKGROUND_CLICKED = new Color(250, 220, 220);
	public static final Color BACKGROUND_HOVERED = new Color(220, 250, 220);
	public static final Color BACKGROUND_NORMAL = new Color(220, 220, 250);
	public static final Color BORDER_COLOR = Color.GRAY;
	public static final Font font = new Font("Dialog", Font.BOLD, 13);
	public static final int ITEM = 0, BLOCK = 1, ENTITY = 2, EFFECT = 3, ENCHANTMENT = 4, ACHIEVEMENT = 5, ATTRIBUTE = 6, PARTICLE = 7, SOUND = 8;
	public static final String[] OBJECT_NAMES = { "item", "sound", "particle", "achievement", "entity", "attribute", "effect", "enchantment" };

	public static String getObjectName(int type)
	{
		if (type < 0 || type >= OBJECT_NAMES.length) return "object";
		return CommandGenerator.translate("GUI:object." + OBJECT_NAMES[type]);
	}

}
