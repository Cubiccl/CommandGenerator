package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class Item extends ObjectBase
{

	/** True if this Item's texture is an animated Gif. */
	private boolean hasGif;
	/** True if this Item is a Block. */
	private boolean isBlock;
	/** This Item's numerical ID. */
	private int idNum;
	/** This Item's maximum damage. */
	private int maxDamage;
	/** This Item's durability. */
	private int durability;
	public static List<String> durabilityList = new ArrayList<String>();

	/** Creates a new Item.
	 * 
	 * @param isBlock
	 *            - Is this Item a Block?
	 * @param idNum
	 *            - The Item's numerical ID.
	 * @param idString
	 *            - The Item's text ID.
	 * @param maxDamage
	 *            - The Item's maximum damage.
	 * @param hasGif
	 *            - Does this Item have an animated image? */
	public Item(boolean isBlock, int idNum, String idString)
	{
		super(idString, CGConstants.OBJECT_ITEM);
		this.isBlock = isBlock;
		this.idNum = idNum;
		this.maxDamage = 0;
		this.durability = 0;
		this.hasGif = false;
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
	public String getName(int damage)
	{
		String category;
		if (isBlock) category = "BLOCKS:";
		else category = "ITEMS:";
		if (getMaxDamage() == 0) return Lang.get(category + getId());
		return Lang.get(category + getId() + "_" + damage);
	}

	/** Returns this Item's texture according to the specified damage.
	 * 
	 * @param damage
	 *            - Int - The damage. */
	public ImageIcon getTexture(int damage)
	{

		String path = Resources.folder + "textures/";
		int damageToUse = 0;

		if (isBlock) path += "blocks/";
		else path += "items/";

		if (damage <= maxDamage) damageToUse = damage;

		if (maxDamage > 0) path += getId() + "/" + Integer.toString(damageToUse);
		else
		{
			if (isBlock) path += "other_blocks/" + getId();
			else path += "other_items/" + getId();
		}

		if (hasGif) path += ".gif";
		else path += ".png";
		try
		{
			return new ImageIcon(path);
		} catch (Exception ex)
		{
			DisplayHelper.missingTexture(path);
			return null;
		}
	}

	/** Returns true if this Items has an animated Gif as texture. */
	public boolean hasGif()
	{
		return hasGif;
	}

	/** Sets this Item's Gif property */
	public void setHasGif(String data)
	{
		this.hasGif = Boolean.parseBoolean(data);
	}

	/** Returns true if this Item is a Block. */
	public boolean isBlock()
	{
		return isBlock;
	}

	/** Returns this Item's name. */
	@Override
	public String getName()
	{
		return getName(0);
	}

	/** Sets this Item's maximum damage. */
	public void setMaxDamage(String damage)
	{
		this.maxDamage = Integer.parseInt(damage);
	}

	/** Sets this Item's durability. */
	public void setDurability(String durability)
	{
		this.durability = Integer.parseInt(durability);
		durabilityList.add(this.getId());
	}

	/** Returns this Item's durability. */
	public int getDurability()
	{
		return durability;
	}

	/** Returns this Item's texture. */
	@Override
	public ImageIcon getTexture()
	{
		return getTexture(0);
	}
}
