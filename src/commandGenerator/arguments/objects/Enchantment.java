package commandGenerator.arguments.objects;

import javax.swing.JOptionPane;

import commandGenerator.Generator;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;

public class Enchantment
{

	/** True if this Enchantment should respect Survival maximum levels. */
	private boolean checkMax;
	/** This Enchantment's level. */
	private int level;
	/** This Enchantment's type. */
	private EnchantType type;

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

	/** Returns a String version of this Enchantment to be displayed to the user. */
	public String display()
	{
		return Generator.translate("GUI:enchant.display").replaceAll("<type>", Generator.translate("ENCHANTS:" + this.type.getId())).replaceAll("<lvl>", Integer.toString(this.level));
	}

	/** Returns this Enchantment's level. */
	public int getLevel()
	{
		return this.level;
	}

	/** Returns this Enchantment's name. */
	public String getName()
	{

		return this.type.getName();
	}

	/** Returns this Enchantment's type. */
	public EnchantType getEnchantType()
	{
		return this.type;
	}

	/** Checks if the Enchantment is valid. */
	public boolean isCorrect()
	{
		if (this.type == null) return false;
		if (this.checkMax && (this.level > this.type.getMaxLevel() || this.level < 1))
		{
			JOptionPane.showMessageDialog(null, Generator.translate("WARNING:enchant.wrong_level").replaceAll("<max>", Integer.toString(this.type.getMaxLevel())),
					Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else return true;
	}

	/** Turns the Enchantment into a TagCompound. */
	public Tag toNBT()
	{
		TagCompound tag = new TagCompound() {
			@Override
			public void askValue()
			{}
		};
		tag.addTag(new TagInt("id").setValue(this.type.getIdNum()));
		tag.addTag(new TagInt("lvl").setValue(this.level));
		return tag;
	}

}
