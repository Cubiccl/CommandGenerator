package commandGenerator.arguments.objects;

import javax.swing.JOptionPane;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.main.Lang;

public class Enchantment
{

	private EnchantType type;
	private int level;
	private boolean checkMax;

	/** Creates a new Enchantment.
	 * 
	 * @param type
	 *            - The Enchantment type.
	 * @param level
	 *            - The Enchantment level. */
	public Enchantment(EnchantType type, int level)
	{
		this(type, level, false);
	}

	/** Creates a new Enchantment.
	 * 
	 * @param type
	 *            - The Enchantment type.
	 * @param level
	 *            - The Enchantment level.
	 * @param checkMax
	 *            - Should the Enchantment level be valid? */
	public Enchantment(EnchantType type, int level, boolean checkMax)
	{
		this.type = type;
		this.level = level;
		this.checkMax = checkMax;
	}

	public String getId()
	{
		return type.getId();
	}

	public int getLevel()
	{
		return level;
	}

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

	public EnchantType getEnchantType()
	{
		return type;
	}

	public String display()
	{
		return Lang.get("GUI:enchant.display").replaceAll("<type>", Lang.get("ENCHANTS:" + type.getId())).replaceAll("<lvl>", Integer.toString(level + 1));
	}

}
