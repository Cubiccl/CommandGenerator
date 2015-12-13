package generator.registry;

import generator.main.Utils;

import java.awt.image.BufferedImage;

public class Enchantment extends ObjectWithNumId
{
	private final int maxLevel;
	private BufferedImage icon;

	public Enchantment(int idNum, String idString, int maxLevel)
	{
		super(Utils.ENCHANTMENT, idNum, idString);
		this.maxLevel = maxLevel;
	}

	public int getMaxLevel()
	{
		return this.maxLevel;
	}

	@Override
	public BufferedImage getIcon()
	{
		return this.icon;
	}

}
