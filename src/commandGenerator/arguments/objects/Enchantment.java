package commandGenerator.arguments.objects;

import javax.swing.JOptionPane;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.main.Lang;

public class Enchantment
{

	/** This Enchantment's type. */
	private EnchantType type;
	/** This Enchantment's level. */
	private int level;
	/** True if this Enchantment should respect Survival maximum levels. */
	private boolean checkMax;

	/** Creates a new Enchantment.
	 * 
	 * @param type
	 *            - <i>EnchantType</i> - The Enchantment type.
	 * @param level
	 *            - <i>int</i> - The Enchantment level. */
	public Enchantment(EnchantType type, int level)
	{
		this(type, level, false);
	}

	/** Creates a new Enchantment.
	 * 
	 * @param type
	 *            - <i>EnchantType</i> - The Enchantment type.
	 * @param level
	 *            - <i>int</i> - The Enchantment level.
	 * @param checkMax
	 *            - <i>boolean</i> - Should the Enchantment level be valid? */
	public Enchantment(EnchantType type, int level, boolean checkMax)
	{
		this.type = type;
		this.level = level;
		this.checkMax = checkMax;
	}

	/** Returns this Enchantment's type. */
	public EnchantType getType()
	{
		return type;
	}

	/** Returns this Enchantment's level. */
	public int getLevel()
	{
		return level;
	}

	/** Returns this Enchantment's name. */
	public String getName()
	{

		return type.getName();
	}

	/** Checks if the Enchantment is valid. */
	public boolean isCorrect()
	{
		if (checkMax && (level > type.getMax() || level < 0))
		{
			JOptionPane.showMessageDialog(null, Lang.get("WARNING:enchant.wrong_level").replaceAll("<max>", Integer.toString(type.getMax())),
					Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else return true;
	}

	/** Turns the Enchantment into a TagCompound. */
	public Tag toNBT()
	{
		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};
		tag.addTag(new TagInt("id").setValue(type.getIdNum()));
		tag.addTag(new TagInt("lvl").setValue(level));
		return tag;
	}

	/** Returns a String version of this Enchantment to be displayed to the user. */
	public String display()
	{
		return Lang.get("GUI:enchant.display").replaceAll("<type>", Lang.get("ENCHANTS:" + type.getId())).replaceAll("<lvl>", Integer.toString(level + 1));
	}

}
