package generator.registry;

import generator.CommandGenerator;
import generator.main.FileManager;

import java.awt.image.BufferedImage;

public class ItemBlock extends ObjectWithNumId
{
	/** Default texture type = same texture for all damage values. */
	private static final int DEFAULT = 100000;

	/** True if this is a block or a block represented as an item. */
	private boolean block;
	private int[] damage;
	private String langType;
	private String[] names;
	private BufferedImage[] textures;
	private int textureType;

	public ItemBlock(int type, int idNum, String idString)
	{
		this(type, idNum, idString, false);
	}

	public ItemBlock(int type, int idNum, String idString, boolean block)
	{
		super(type, idNum, idString);
		this.damage = new int[] { 0 };
		this.langType = "null";
		this.textureType = DEFAULT;
		this.block = block;
	}

	@Override
	protected void createIcon()
	{
		String path = "textures/";
		if (this.isBlock()) path += "blocks/";
		else path += "items/";
		this.textures = new BufferedImage[this.getDamage().length];

		if (textureType == DEFAULT) for (int i = 0; i < this.textures.length; i++)
			this.textures[i] = FileManager.loadTexture(path + "other/" + this.getId());

		else for (int i = 0; i < this.textures.length; i++)
			this.textures[i] = FileManager.loadTexture(path + this.getId() + "/" + this.getDamage()[i % this.textureType]);

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
		return this.getIcon();
	}

	@Override
	public String getName()
	{
		return this.getName(this.getDamage()[0]);
	}

	public String getName(int damage)
	{
		for (int i = 0; i < this.damage.length; i++)
		{
			if (this.damage[i] == damage) return this.names[i];
		}
		return this.getName();
	}

	/** @return True if this is a block or a block represented as an item. */
	public boolean isBlock()
	{
		return this.block;
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

	@Override
	public void updateLang()
	{
		String category = "ITEM";
		if (this.isBlock()) category = "BLOCK";
		this.names = new String[this.getDamage().length];
		switch (this.langType)
		{

			default:
				// 0 damage value : BLOCK:bedrock
				if (this.getDamage().length == 1) for (int i = 0; i < this.names.length; i++)
					this.names[i] = CommandGenerator.translate(category + ":" + this.getId());

				// several damage values : BLOCK:stone_0, BLOCK:stone_1...
				else for (int i = 0; i < this.names.length; i++)
					this.names[i] = CommandGenerator.translate(category + ":" + this.getId() + "_" + this.getDamage()[i]);
				break;
		}
	}
}
