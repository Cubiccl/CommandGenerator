package generator.registry.instance;

import generator.main.Utils;
import generator.registry.Enchantment;

/** An Enchantment applied to an Item. */
public class AppliedEnchantment extends ObjectInstance
{
	/** The applied Enchantment type. */
	private Enchantment enchantment;
	/** Its level. */
	private int level;

	/** Creates a new Applied Enchantment.
	 * 
	 * @param enchantment - The applied Enchantment type.
	 * @param level - Its level. */
	public AppliedEnchantment(Enchantment enchantment, int level)
	{
		super(Utils.ENCHANTMENT);
		this.enchantment = enchantment;
		this.level = level;
	}

	/** @return The Enchantment type. */
	public Enchantment getEnchantment()
	{
		return this.enchantment;
	}

	/** @return The level of this Enchantment. */
	public int getLevel()
	{
		return this.level;
	}

	@Override
	public String toCommand()
	{
		return this.enchantment.getId() + " " + this.level;
	}

}
