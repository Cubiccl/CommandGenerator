package generator.registry;

import generator.main.Utils;

public class Enchantment extends ObjectWithNumId
{
	/** The maximum level this Enchantment can <i>normally</i> have. (can be set to any via comamnds) */
	private final int maxLevel;

	public Enchantment(int idNum, String idString, int maxLevel)
	{
		super(Utils.ENCHANTMENT, idNum, idString);
		this.maxLevel = maxLevel;
	}

	public int getMaxLevel()
	{
		return this.maxLevel;
	}

}
