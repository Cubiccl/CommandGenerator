package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.arguments.tags.specific.TagExplosion;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

public abstract class ObjectBase
{

	private final String id;
	private final byte type;

	public ObjectBase(String id, byte type)
	{
		this.id = id;
		this.type = type;
	}

	/** Returns the object's String ID. */
	public String getId()
	{
		return id;
	}

	public byte getType()
	{
		return type;
	}

	/** Returns the name to display to the user. */
	public abstract String getName();

	/** Returns the image to use when displaying the object */
	public abstract ImageIcon getTexture();

	public String toString()
	{
		return getName();
	}

	/** Returns the Object corresponding to this ID. */
	public static ObjectBase getObjectFromId(String id)
	{
		if (id.startsWith("minecraft")) id = id.substring("minecraft:".length());
		ObjectBase object = Item.getItemFromId(id);
		if (object == null) object = Achievement.getAchievementFromId(id);
		if (object == null) object = AttributeType.getAttributeFromId(id);
		if (object == null) object = EffectType.getEffectFromId(id);
		if (object == null) object = EnchantType.getEnchantFromId(id);
		if (object == null) object = Entity.getEntityFromId(id);
		if (object == null) object = Particle.getParticleFromId(id);
		if (object == null) object = Sound.getSoundFromId(id);

		if (object == null) DisplayHelper.log(id + " isn't the ID of any object.");
		return object;
	}

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

	public static String display(Object object)
	{
		if (object instanceof ItemStack) return ((ItemStack) object).display(CGConstants.DETAILS_ALL, 0);
		if (object instanceof Effect) return ((Effect) object).display();
		if (object instanceof Enchantment) return ((Enchantment) object).display();
		if (object instanceof Attribute) return ((Attribute) object).display();
		if (object instanceof TagCompound) return ((TagCompound) object).display(CGConstants.DETAILS_ALL, 0);
		if (object instanceof TagString) return ((TagString) object).display(CGConstants.DETAILS_ALL, 0);

		return object.toString();
	}

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
}
