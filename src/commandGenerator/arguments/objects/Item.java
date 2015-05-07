package commandGenerator.arguments.objects;

import java.io.File;

import javax.swing.ImageIcon;

import commandGenerator.Generator;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

public class Item extends ObjectBase
{

	/** This Item's durability. */
	private int durability;

	/** This Item's numerical ID. */
	private int idNum;

	/** True if this Item is a Block. */
	private boolean isBlock;

	/** This Item's maximum damage. */
	private int maxDamage;

	/** Contains all of this Item's textures. */
	protected ImageIcon[] textures;

	/** Contains all of this Item's names. */
	protected String[] names;

	/** The type of texture to use. */
	private int textureType;

	/** The type of the tranlation to use. */
	protected String langType;

	/** Creates a new Item.
	 * 
	 * @param isBlock
	 *            - <i>boolean</i> - Is this Item a Block?
	 * @param idNum
	 *            - <i>int</i> - The Item's numerical ID.
	 * @param idString
	 *            - <i>String</i> - The Item's text ID. */
	public Item(boolean isBlock, int idNum, String idString)
	{
		super(idString, ObjectBase.ITEM);
		this.isBlock = isBlock;
		this.idNum = idNum;
		this.maxDamage = 0;
		this.durability = 0;
		this.textureType = 100000;
		this.langType = "normal";
	}

	public String getCommandId()
	{
		String id = this.getId();
		if (id.endsWith("_item")) return id.substring(0, id.length() - "_item".length());
		else return id;
	}

	/** Returns this Item's durability. */
	public int getDurability()
	{
		return this.durability;
	}

	/** Returns this Item's numerical ID. */
	public int getIdNum()
	{
		return this.idNum;
	}

	/** Returns this Item's maximum damage. */
	public int getMaxDamage()
	{
		return this.maxDamage;
	}

	/** Returns this Item's name. */
	@Override
	public String getName()
	{
		if (this instanceof ItemData) return ((ItemData) this).getDefaultName();
		return this.getName(0);
	}

	/** Returns this Item's name. */
	public String getName(int damage)
	{
		if (this.names == null) return this.getCommandId();
		if (damage > this.names.length || damage < 0) return this.names[0];
		return this.names[damage];
	}

	/** Returns this Item's texture. */
	@Override
	public ImageIcon getTexture()
	{
		return this.getTexture(0);
	}

	/** Returns this Item's texture according to the specified damage.
	 * 
	 * @param damage
	 *            - <i>int</i> - The damage. */
	public ImageIcon getTexture(int damage)
	{
		if (damage > this.textures.length || damage < 0) return this.textures[0];
		else if (this.isTextureUnique()) return this.textures[0];
		else return this.textures[damage];
	}

	/** Returns true if this Item is a Block. */
	public boolean isBlock()
	{
		return this.isBlock;
	}

	/** Returns true if this Item has a unique Texture. */
	public boolean isTextureUnique()
	{
		return this.textureType == 0;
	}

	/** Sets this Item's durability. */
	public void setDurability(int durability)
	{
		this.durability = durability;
		Generator.registry.registerDurableItem(this);
	}

	/** Sets this Item's maximum damage. */
	public void setMaxDamage(int maxDamage)
	{
		this.maxDamage = maxDamage;
	}

	public void registerTextures()
	{

		String path = Resources.folder + "textures/";

		if (this.isBlock) path += "blocks/";
		else path += "items/";

		if (this.getMaxDamage() > 0 && !this.isTextureUnique())
		{
			this.textures = new ImageIcon[this.getMaxDamage() + 1];
			path += this.getCommandId() + "/";

			for (int damage = 0; damage <= this.getMaxDamage(); damage++)
			{
				String finalPath = path + (damage % this.textureType) + ".png";
				this.textures[damage] = new ImageIcon(finalPath);
				if (!new File(finalPath).exists()) DisplayHelper.missingTexture(finalPath);
			}

		} else
		{
			this.textures = new ImageIcon[1];

			if (this.isBlock) path += "other_blocks/" + this.getCommandId() + ".png";
			else path += "other_items/" + this.getCommandId() + ".png";

			this.textures[0] = new ImageIcon(path);
			if (!new File(path).exists()) DisplayHelper.missingTexture(path);
		}
	}

	public void setTextureType(int textureType)
	{
		this.textureType = textureType;
	}

	public void setLangType(String langType)
	{
		this.langType = langType;
	}

	@Override
	public void updateLang()
	{
		if (this.getMaxDamage() > 0)
		{
			this.names = new String[this.getMaxDamage() + 1];
			for (int damage = 0; damage <= this.getMaxDamage(); damage++)
			{
				if (this.langType.equals("normal")) this.names[damage] = Generator.translate("ITEMS:" + this.getId() + "_" + damage);
				else if (this.langType.startsWith("half_"))
				{
					if (damage < 8) this.names[damage] = Generator.translate("ITEMS:" + this.getId() + "_" + damage);
					else this.names[damage] = Generator.translate("ITEMS:" + this.getId() + "_" + (damage - 8)) + " "
							+ Generator.translate("ITEMS:" + this.langType);
				} else this.names[damage] = Generator.translate("ITEMS:" + this.getId()) + " " + Generator.translate("ITEMS:" + this.langType + "_" + damage);
			}
		} else
		{
			this.names = new String[1];
			this.names[0] = Generator.translate("ITEMS:" + this.getId());
		}
	}

	protected int getTextureType()
	{
		return this.textureType;
	}

	public int[] getDamageList()
	{
		int maxDamage = this.getMaxDamage();
		if (this.getDurability() > 0) maxDamage = this.getDurability();

		int[] damageList = new int[maxDamage + 1];
		for (int i = 0; i < damageList.length; i++)
			damageList[i] = i;

		return damageList;
	}
}
