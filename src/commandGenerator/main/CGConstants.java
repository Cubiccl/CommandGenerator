package commandGenerator.main;

public class CGConstants
{
	public static final int DETAILS_ALL = 2, DETAILS_ONE = 1, DETAILS_NONE = 0;
	/** The type of a Target Selection Panel.
	 * <ul>
	 * <li><strong>ENTITIES_PLAYERS</strong> : Can only target Players.</li>
	 * <li><strong>ENTITIES_NPCS</strong> : Can only target Non-Player Characters.</li>
	 * <li><strong>ENTITIES_ALL</strong> : Can target any Entity.</li>
	 * </ul> */
	public static final int ENTITIES_PLAYERS = 0, ENTITIES_NPCS = 1, ENTITIES_ALL = 2;
	public static final String LIST_BLOCKS = "blocks", LIST_ITEMS = "items", LIST_ICONS = "icon", LIST_CRAFT = "craft", LIST_PLACE = "place",
			LIST_MINE = "mine", LIST_USE = "use", LIST_MOBS = "mob", LIST_TILEENTITY = "tileentity";
	public static final String NBTID_COLOR = "nbt_color";

	public static final byte OBJECT_ITEM = 0, OBJECT_ENTITY = 1, OBJECT_EFFECT = 2, OBJECT_ENCHANT = 3, OBJECT_ACHIEVEMENT = 4, OBJECT_ATTRIBUTE = 5,
			OBJECT_PARTICLE = 6, OBJECT_TAG = 7, OBJECT_SOUND = 8, OBJECT_STRING = 9, OBJECT_TAG_EXPLOSION = 10, OBJECT_TAG_TRADE = 11, OBJECT_JSON = 12,
			OBJECT_TAG_PATTERN = 13, OBJECT_BLOCK = 14, OBJECT_COORD = 15, OBJECT_TARGET = 16;

	public static final String[] TYPES = { "Item", "Entity", "Effect", "Enchantment", "Achievement", "Attribute", "Particle", "NBT Tag", "Sound" };

}
