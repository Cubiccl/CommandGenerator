package generator.registry;

import java.awt.image.BufferedImage;

public class ItemBlock extends ObjectWithNumId
{
	private int[] damage;
	private String langType;
	private BufferedImage[] textures;
	private int textureType;

	public ItemBlock(int type, int idNum, String idString)
	{
		super(type, idNum, idString);
		this.damage = new int[] { 0 };
		this.langType = "null";
		this.textureType = 100000;
	}

	public int[] getDamage()
	{
		return this.damage;
	}

	@Override
	public BufferedImage getIcon()
	{
		return this.getIcon(this.getDamage()[0]);
	}

	public BufferedImage getIcon(int damage)
	{
		for (int i = 0; i < this.damage.length; i++)
		{
			if (this.damage[i] == damage) return this.textures[i];
		}
		return null;
	}

	public void setDamage(int maximumDamage)
	{
		this.damage = new int[maximumDamage + 1];
		for (int i = 0; i < this.damage.length; i++)
		{
			this.damage[i] = i;
		}
	}

	public void setDamage(int[] damage)
	{
		this.damage = damage;
	}

	public void setLangType(String langType)
	{
		this.langType = langType;
	}

	public void setTextureType(int textureType)
	{
		this.textureType = textureType;
	}

}
