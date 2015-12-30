package generator.registry;

import generator.CommandGenerator;
import generator.main.FileManager;

import java.awt.image.BufferedImage;

/** Represents an Item or a Block. */
public class ItemBlock extends ObjectWithNumId
{
	/** Default texture type = same texture for all damage values. */
	private static final int DEFAULT = 10000;

	/** True if this is a block or a block represented as an item. */
	private boolean block;
	/** The damage values this Item/Block can get. */
	private int[] damage;
	/** True if this Item has durability, thus should always have the same texture and name. */
	private boolean hasDurability;
	/** Allows easier name handling. */
	private String langType;
	/** This Item/Block's names. */
	private String[] names;
	/** This Item/Block's textures. */
	private BufferedImage[] textures;
	/** Allows easier texture handling. */
	private int textureType;

	/** Creates a new Item/Block
	 * 
	 * @param type - Utils.ITEM or Utils.BLOCK
	 * @param idNum - Its numerical ID.
	 * @param idString - Its String ID.
	 * @param block - True if it is a Block, false if Item. */
	public ItemBlock(int type, int idNum, String idString, boolean block)
	{
		super(type, idNum, idString);
		this.damage = new int[] { 0 };
		this.langType = "null";
		this.textureType = DEFAULT;
		this.block = block;
		this.hasDurability = false;
	}

	@Override
	protected void createIcon()
	{
		String path = "";
		if (this.isBlock()) path += "blocks/";
		else path += "items/";
		if (this.textureType == 0 || this.hasDurability() || this.getDamage().length == 1)
		{
			this.textures = new BufferedImage[] { FileManager.loadTexture(path + "other/" + this.getId()) };
			return;
		}
		this.textures = new BufferedImage[this.getDamage().length];

		for (int i = 0; i < this.textures.length; i++)
			this.textures[i] = FileManager.loadTexture(path + this.getId() + "/" + this.getDamage()[i % this.textureType]);

	}

	/** Called by {@link ItemBlock#updateLang()}
	 * 
	 * @param damage - The input damage.
	 * @return The name of this Object for the given damage. */
	private String generateName(int damage)
	{
		if (this.getLangType().equals("null")) return CommandGenerator.translate("ITEM:" + this.getId() + "_" + damage);
		if (this.getLangType().equals("item")) return CommandGenerator.translate("ITEM:" + this.getId() + "_item_" + damage);
		if (this.getLangType().equals("half_slab"))
		{
			if (damage < 8) return CommandGenerator.translate("ITEM:" + this.getId() + "_" + damage % 8);
			else return CommandGenerator.translate("ITEM:" + this.getId() + "_" + damage % 8) + " " + CommandGenerator.translate("ITEM:half_slab");
		}
		if (this.getLangType().equals("color")) return CommandGenerator.translate("ITEM:color" + "_" + damage) + " "
				+ CommandGenerator.translate("ITEM:" + this.getId());
		return CommandGenerator.translate("ITEM:" + this.getId()) + " " + CommandGenerator.translate("ITEM:" + this.getLangType() + "_" + damage);

	}

	/** @return The damage values this Item/Block can get. */
	public int[] getDamage()
	{
		return this.damage;
	}

	@Override
	public BufferedImage getIcon()
	{
		return this.getIcon(this.getDamage()[0]);
	}

	/** @param damage - The input damage
	 * @return The Icon for the given damage. */
	public BufferedImage getIcon(int damage)
	{
		for (int i = 0; i < this.damage.length; i++)
		{
			if (this.damage[i] == damage) return this.textures[i];
		}
		return this.getIcon();
	}

	/** @return The language type of this Item/Block. */
	public String getLangType()
	{
		return this.langType;
	}

	/** @see ItemBlock#getName(int) */
	@Deprecated
	@Override
	public String getName()
	{
		return this.getName(this.getDamage()[0]);
	}

	/** @param damage - The input damage
	 * @return The name for the given damage. */
	public String getName(int damage)
	{
		for (int i = 0; i < this.damage.length; i++)
		{
			if (this.damage[i] == damage) return this.names[i];
		}
		return this.getName();
	}

	/** @return True if this Item has durability. */
	public boolean hasDurability()
	{
		return this.hasDurability;
	}

	/** @return True if this is a block or a block represented as an item. */
	public boolean isBlock()
	{
		return this.block;
	}

	/** Sets the damage values to (0 to maxDamage)
	 * 
	 * @param maximumDamage - The maximum damage. */
	public void setDamage(int maximumDamage)
	{
		this.damage = new int[maximumDamage + 1];
		for (int i = 0; i < this.damage.length; i++)
		{
			this.damage[i] = i;
		}
	}

	/** @param damage - The new damage values. */
	public void setDamage(int[] damage)
	{
		this.damage = damage;
	}

	/** @param durability - The new durability. */
	public void setDurability(int durability)
	{
		this.hasDurability = true;
		this.setDamage(durability);
	}

	/** @param langType - The new langType. */
	public void setLangType(String langType)
	{
		this.langType = langType;
	}

	/** @param textureType - The new textureType. */
	public void setTextureType(int textureType)
	{
		this.textureType = textureType;
	}

	@Override
	public void updateLang()
	{
		if (this.hasDurability())
		{
			this.names = new String[] { CommandGenerator.translate("ITEM:" + this.getId()) };
			return;
		}
		this.names = new String[this.getDamage().length];

		switch (this.langType)
		{

			default:
				// 0 damage value : BLOCK:bedrock
				if (this.getDamage().length == 1) for (int i = 0; i < this.names.length; i++)
					this.names[i] = CommandGenerator.translate("ITEM:" + this.getId());

				// several damage values : BLOCK:stone_0, BLOCK:stone_1...
				else for (int i = 0; i < this.names.length; i++)
					this.names[i] = this.generateName(this.getDamage()[i]);
				break;
		}
	}
}
