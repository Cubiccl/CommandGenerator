package commandGenerator.main;

public class CGConstants
{

	public static final String DATAID_CLEAR_ITEM = "clear_item";

	public static final String DATAID_NONE = "none", DATAID_ENTITY = "entity", DATAID_MODE = "mode", DATAID_MODE2 = "mode2", DATAID_VALUE = "value",
			DATAID_NAME = "name", DATAID_NAME2 = "name2", DATAID_CHECK = "check";

	public static final String DATAID_SOUND_COORDS = "snd_coo", DATAID_SOUND_OPTIONS = "snd_opt", DATAID_SOUND_VOL = "snd_vol",
			DATAID_SOUND_PITCH = "snd_pitch", DATAID_SOUND_VOLMIN = "snd_vol_min";

	public static final String DATAID_SPREAD_X = "spread_x", DATAID_SPREAD_Z = "spread_z", DATAID_SPREAD_DIST = "spread_dis",
			DATAID_SPREAD_DISTMAX = "spread_disMax";

	public static final int DETAILS_ALL = 2, DETAILS_ONE = 1, DETAILS_NONE = 0;
	/** The type of a Target Selection Panel.
	 * <ul>
	 * <li><strong>ENTITIES_PLAYERS</strong> : Can only target Players.</li>
	 * <li><strong>ENTITIES_NPCS</strong> : Can only target Non-Player Characters.</li>
	 * <li><strong>ENTITIES_ALL</strong> : Can target any Entity.</li>
	 * </ul> */
	public static final int ENTITIES_PLAYERS = 0, ENTITIES_NPCS = 1, ENTITIES_ALL = 2;
	public static final String LIST_BLOCKS = "blocks", LIST_ITEMS = "items", LIST_ICONS = "icon", LIST_CRAFT = "craft", LIST_PLACE = "place",
			LIST_MINE = "mine", LIST_USE = "use", LIST_MOBS = "mob";
	public static final String NBTID_COLOR = "nbt_color";

	public static final byte OBJECT_ITEM = 0, OBJECT_ENTITY = 1, OBJECT_EFFECT = 2, OBJECT_ENCHANT = 3, OBJECT_ACHIEVEMENT = 4, OBJECT_ATTRIBUTE = 5,
			OBJECT_PARTICLE = 6, OBJECT_TAG = 7, OBJECT_SOUND = 8, OBJECT_STRING = 9, OBJECT_TAG_EXPLOSION = 10, OBJECT_TAG_TRADE = 11, OBJECT_JSON = 12,
			OBJECT_TAG_PATTERN = 13, OBJECT_BLOCK = 14;

	public static final String PANELID_TARGET = "Ptarget", PANELID_TARGET2 = "Ptarget2", PANELID_ACHIEVEMENT = "Pach", PANELID_XP = "Pxp",
			PANELID_EFFECT = "Peff", PANELID_ENCHANT = "Pench", PANELID_COORDS = "Pcoo", PANELID_COORDS_START = "PcooS", PANELID_COORDS_END = "PcooE",
			PANELID_PARTICLE = "Ppart", PANELID_SOUND = "Psound", PANELID_NONE = "Pnone", PANELID_OPTIONS = "Popt", PANELID_BLOCK = "Pblo",
			PANELID_NBT = "Pnbt", PANELID_ITEM = "Pitem", PANELID_JSON = "Pjson", PANELID_ENTITY = "Pent";

	public static final String[] TYPES = { "Item", "Entity", "Effect", "Enchantment", "Achievement", "Attribute", "Particle", "NBT Tag", "Sound" };

}
