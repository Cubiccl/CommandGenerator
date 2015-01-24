package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class Item extends ObjectBase
{

	private boolean hasGif, isBlock;
	private int idNum, maxDamage, durability;
	private static Map<String, Item> list = new HashMap<String, Item>();
	private static List<String> ids = new ArrayList<String>();
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
		list.put(idString, this);
		ids.add(idString);
	}

	public int getIdNum()
	{
		return idNum;
	}

	public int getMaxDamage()
	{
		return this.maxDamage;
	}

	public String getName(int damage)
	{
		String category;
		if (isBlock) category = "BLOCKS:";
		else category = "ITEMS:";
		if (getMaxDamage() == 0) return Lang.get(category + getId());
		return Lang.get(category + getId() + "_" + damage);
	}

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

	public boolean hasGif()
	{
		return hasGif;
	}

	public void setHasGif(String data)
	{
		this.hasGif = Boolean.parseBoolean(data);
	}

	public boolean isBlock()
	{
		return isBlock;
	}

	public static Item getItemFromId(String id)
	{
		return list.get(id);
	}

	@Override
	public String getName()
	{
		return getName(0);
	}

	public void setMaxDamage(String damage)
	{
		this.maxDamage = Integer.parseInt(damage);
	}

	public void setDurability(String durability)
	{
		this.durability = Integer.parseInt(durability);
		durabilityList.add(this.getId());
	}

	public int getDurability()
	{
		return durability;
	}

	@Override
	public ImageIcon getTexture()
	{
		return getTexture(0);
	}

	public static Object[] generateFrom(String id, int damage, int count, int slot)
	{
		DisplayHelper.log("Created Item : " + count + " " + ((Item) ObjectBase.getObjectFromId(id)).getName(damage) + " in slot " + slot);
		return new Object[] { id, damage, count, slot };
	}

	public static Object[] generateBlockFrom(String id, int damage)
	{
		DisplayHelper.log("Created block : " + ((Item) ObjectBase.getObjectFromId(id)).getName(damage));
		return new Object[] { id, damage };
	}
}
