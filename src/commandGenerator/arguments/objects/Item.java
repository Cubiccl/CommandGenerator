package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class Item extends ObjectBase
{

	/** List containing all items which have durability */
	public static List<String> durabilityList = new ArrayList<String>();
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
		super(idString, Constants.OBJECT_ITEM);
		this.isBlock = isBlock;
		this.idNum = idNum;
		this.maxDamage = 0;
		this.durability = 0;
	}

	public String getCommandId()
	{
		String id = getId();
		if (id.endsWith("_item")) return id.substring(0, id.length() - "_item".length());
		else return id;
	}

	/** Returns this Item's durability. */
	public int getDurability()
	{
		return durability;
	}

	/** Returns this Item's numerical ID. */
	public int getIdNum()
	{
		return idNum;
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
		return getName(0);
	}

	/** Returns this Item's name. */
	public String getName(int damage)
	{
		if (getMaxDamage() == 0) return Lang.get("ITEMS:" + getId());
		return Lang.get("ITEMS:" + getId() + "_" + damage);
	}

	/** Returns this Item's texture. */
	@Override
	public ImageIcon getTexture()
	{
		return getTexture(0);
	}

	/** Returns this Item's texture according to the specified damage.
	 * 
	 * @param damage
	 *            - <i>int</i> - The damage. */
	public ImageIcon getTexture(int damage)
	{

		String path = Resources.folder + "textures/";
		int damageToUse = 0;

		if (isBlock) path += "blocks/";
		else path += "items/";

		if (damage <= maxDamage) damageToUse = damage;

		if (maxDamage > 0) path += getCommandId() + "/" + Integer.toString(damageToUse) + ".png";
		else
		{
			if (isBlock) path += "other_blocks/" + getCommandId() + ".png";
			else path += "other_items/" + getCommandId() + ".png";
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
		return isBlock;
	}

	/** Sets this Item's durability. */
	public void setDurability(String durability)
	{
		this.durability = Integer.parseInt(durability);
		durabilityList.add(this.getId());
	}

	/** Sets this Item's maximum damage. */
	public void setMaxDamage(String damage)
	{
		this.maxDamage = Integer.parseInt(damage);
	}
}
