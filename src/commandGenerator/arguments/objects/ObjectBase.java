package commandGenerator.arguments.objects;

import java.util.List;

import javax.swing.ImageIcon;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagBoolean;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.arguments.tags.specific.TagExplosion;
import commandGenerator.main.CGConstants;

public abstract class ObjectBase
{

	/** The Objects used by the Command Generator.
	 * <ul>
	 * <li><strong>ITEM</strong> = 0</li>
	 * <li><strong>ENTITY</strong> = 1</li>
	 * <li><strong>EFFECT</strong> = 2</li>
	 * <li><strong>ENCHANTMENT</strong> = 3</li>
	 * <li><strong>ACHIEVEMENT</strong> = 4</li>
	 * <li><strong>ATTRIBUTE</strong> = 5</li>
	 * <li><strong>PARTICLE</strong> = 6</li>
	 * <li><strong>TAG</strong>(NBT Tags) = 7</li>
	 * <li><strong>SOUND</strong> = 8</li>
	 * </ul>
	 * The following are used by the List Selection Panel :
	 * <ul>
	 * <li><strong>STRING</strong> = 9</li>
	 * <li><strong>TAG_EXPLOSION</strong> = 10</li>
	 * <li><strong>TAG_TRADE</strong> = 11</li>
	 * <li><strong>JSON</strong> = 12</li>
	 * <li><strong>TAG_PATTERN</strong> = 13</li>
	 * <li><strong>BLOCK</strong> = 14</li>
	 * <li><strong>COORD</strong> = 15</li>
	 * <li><strong>TARGET</strong> = 16</li>
	 * </ul> */
	public static final byte ITEM = 0, ENTITY = 1, EFFECT = 2, ENCHANTMENT = 3, ACHIEVEMENT = 4, ATTRIBUTE = 5, PARTICLE = 6, TAG = 7, SOUND = 8, STRING = 9,
			TAG_EXPLOSION = 10, TAG_TRADE = 11, JSON = 12, TAG_PATTERN = 13, BLOCK = 14, COORD = 15, TARGET = 16;

	/** Returns a String version of the Object to be displayed to the user.
	 * 
	 * @param object
	 *            - <i>Object</i> - The Object to display. */
	@SuppressWarnings("unchecked")
	public static String display(Object object)
	{
		if (object instanceof ItemStack) return ((ItemStack) object).display(CGConstants.DETAILS_ALL, 0);
		if (object instanceof Effect) return ((Effect) object).display();
		if (object instanceof Enchantment) return ((Enchantment) object).display();
		if (object instanceof Attribute) return ((Attribute) object).display();
		if (object instanceof TagCompound) return ((TagCompound) object).display(CGConstants.DETAILS_ALL, 0);
		if (object instanceof TagString) return ((TagString) object).display(CGConstants.DETAILS_ALL, 0);
		if (object instanceof Coordinates) return ((Coordinates) object).commandStructure();
		if (object instanceof List) return new TagCompound() {
			@Override
			public void askValue()
			{}
		}.setValue(((List<Tag>) object)).display(CGConstants.DETAILS_ALL, 0);
		if (object instanceof Target) return ((Target) object).display();

		return object.toString();
	}

	/** Returns a Tag version of the Object.
	 * 
	 * @param object
	 *            - <i>Object</i> - The Object to turn into Tag. */
	public static Tag toNBT(Object object)
	{
		if (object instanceof ItemStack) return ((ItemStack) object).toNBT("");
		if (object instanceof Effect) return ((Effect) object).toNBT();
		if (object instanceof Enchantment) return ((Enchantment) object).toNBT();
		if (object instanceof Attribute) return ((Attribute) object).toNBT();
		if (object instanceof TagExplosion) return ((TagExplosion) object);
		if (object instanceof TagCompound) return ((TagCompound) object);
		if (object instanceof TagString) return ((TagString) object);

		return (Tag) object;
	}

	/** Turns the NBT Tag into an Object according to the specified type of Object.
	 * 
	 * @param nbt
	 *            - <i>Tag</i> - The NBT Tag.
	 * @param type
	 *            - <i>int</i> - The type of Object wanted. */
	public static Object toObject(Tag nbt, int type)
	{
		switch (type)
		{
			case ATTRIBUTE:
				String idA = "generic.maxHealth";
				double amount = 1.0D;
				int operation = 0;
				for (int i = 0; i < ((TagCompound) nbt).size(); i++)
				{
					Tag tag = ((TagCompound) nbt).get(i);
					if (tag.getId().equals("AttributeName")) idA = ((TagString) tag).getValue();
					if (tag.getId().equals("Amount")) amount = ((TagDouble) tag).getValue();
					if (tag.getId().equals("Operation")) operation = ((TagInt) tag).getValue();
				}
				return new Attribute((AttributeType) Registry.getObjectFromId(idA), amount, operation);

			case EFFECT:
				int idEf = 1,
				levelEf = 0,
				duration = 1;
				boolean hide = false;
				for (int i = 0; i < ((TagCompound) nbt).size(); i++)
				{
					Tag tag = ((TagCompound) nbt).get(i);
					if (tag.getId().equals("Id")) idEf = ((TagInt) tag).getValue();
					if (tag.getId().equals("Amplifier")) levelEf = ((TagInt) tag).getValue();
					if (tag.getId().equals("Duration")) duration = ((TagInt) tag).getValue();
					if (tag.getId().equals("HideParticles")) hide = ((TagBoolean) tag).getValue();
				}
				return new Effect((EffectType) Registry.getObjectFromIdNum(EFFECT, idEf), levelEf, duration, !hide);

			case ENCHANTMENT:
				int idEn = 0,
				levelEn = 0;
				for (int i = 0; i < ((TagCompound) nbt).size(); i++)
				{
					Tag tag = ((TagCompound) nbt).get(i);
					if (tag.getId().equals("id")) idEn = ((TagInt) tag).getValue();
					if (tag.getId().equals("lvl")) levelEn = ((TagInt) tag).getValue();
				}
				return new Enchantment((EnchantType) Registry.getObjectFromIdNum(ENCHANTMENT, idEn), levelEn);

			case ENTITY:
				return nbt;

			case ITEM:
				return ObjectCreator.generateItemStack((TagCompound) nbt);

			case JSON:
				return nbt;

			case STRING:
				return (nbt);

			case TAG_EXPLOSION:
				return nbt;

			case TAG_TRADE:
				return nbt;

			case TAG_PATTERN:
				return nbt;

			default:
				return (nbt);
		}
	}

	/** The Object's ID. */
	private final String id;

	/** The Object's type. */
	private final byte type;

	/** Creates a new
	 * 
	 * @param id
	 *            - <i>String</i> - This Object's ID.
	 * @param type
	 *            - <i>Byte</i> - This Object's type. */
	public ObjectBase(String id, byte type)
	{
		this.id = id;
		this.type = type;
		if (type != TAG) Registry.registerObject(type, this);
	}

	/** Returns the Object's ID. */
	public String getId()
	{
		return id;
	}

	/** Returns the name to display to the user. */
	public abstract String getName();

	/** Returns the image to use when displaying the object */
	public abstract ImageIcon getTexture();

	/** Returns the Object's type. */
	public byte getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return getName();
	}
}
