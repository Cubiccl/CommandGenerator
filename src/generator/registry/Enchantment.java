package generator.registry;

import java.awt.image.BufferedImage;

import generator.main.Constants;
import generator.main.FileManager;

public class Enchantment extends ObjectWithNumId
{
	private final int maxLevel;
	private BufferedImage icon;

	public Enchantment(int idNum, String idString, int maxLevel)
	{
		super(Constants.ENCHANTMENT, idNum, idString);
		this.maxLevel = maxLevel;
	}

	public int getMaxLevel()
	{
		return this.maxLevel;
	}

	@Override
	protected void createIcon()
	{
		this.icon = FileManager.loadTexture("enchantments/" + this.getId());
	}

	@Override
	public BufferedImage getIcon()
	{
		return this.icon;
	}

}
