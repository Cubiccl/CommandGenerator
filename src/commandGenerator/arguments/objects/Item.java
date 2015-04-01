package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
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
		if (this.getMaxDamage() == 0) return Lang.get("ITEMS:" + this.getId());
		return Lang.get("ITEMS:" + this.getId() + "_" + damage);
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

		String path = Resources.folder + "textures/";
		int damageToUse = 0;

		if (this.isBlock) path += "blocks/";
		else path += "items/";

		if (damage <= this.maxDamage) damageToUse = damage;

		if (this.maxDamage > 0) path += this.getCommandId() + "/" + Integer.toString(damageToUse) + ".png";
		else
		{
			if (this.isBlock) path += "other_blocks/" + this.getCommandId() + ".png";
			else path += "other_items/" + this.getCommandId() + ".png";
		}

		try
		{
			return new ImageIcon(path);
		} catch (Exception ex)
		{
			DisplayHelper.missingTexture(path);
			return null;
		}
	}

	/** Returns true if this Item is a Block. */
	public boolean isBlock()
	{
		return this.isBlock;
	}

	/** Sets this Item's durability. */
	public void setDurability(int durability)
	{
		this.durability = durability;
		Registry.registerDurableItem(this);
	}

	/** Sets this Item's maximum damage. */
	public void setMaxDamage(int maxDamage)
	{
		this.maxDamage = maxDamage;
	}
}
