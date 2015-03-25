package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.Lang;

public class EnchantType extends ObjectBase
{
	/** This Enchantment's numercial ID. */
	private int idNum;

	/** This Enchantment's maximum level. */
	private int max;

	/** Creates a new Enchantment type.
	 * 
	 * @param idNum
	 *            - <i>int</i> - The Enchantment's numerical ID.
	 * @param idString
	 *            - <i>String</i> - The Enchantment's ID.
	 * @param max
	 *            - <i>int</i> - The Enchantment's max level. */
	public EnchantType(int idNum, String idString, int max)
	{
		super(idString, ObjectBase.ENCHANTMENT);
		this.idNum = idNum;
		this.max = max;
		Registry.registerEnchant(this);
	}

	/** Returns this Enchantment's numerical ID. */
	public int getIdNum()
	{
		return this.idNum;
	}

	/** Returns this Enchantment's maximum level. */
	public int getMaxLevel()
	{
		return this.max;
	}

	/** Returns this Enchantment's name. */
	@Override
	public String getName()
	{
		return Lang.get("ENCHANTS:" + this.getId());
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

}
