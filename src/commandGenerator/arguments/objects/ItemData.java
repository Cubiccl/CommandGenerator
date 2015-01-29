package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class ItemData extends Item
{// TODO re-do this

	/** List of the damages this Item has. */
	private int[] damageList;

	/** Creates a new Item with specific damage.
	 * 
	 * @param isBlock
	 *            - <i>boolean</i> - Is this Item a Block?
	 * @param idNum
	 *            - <i>int</i> - The Item's numerical ID.
	 * @param idString
	 *            - <i>String</i> - The Item's text ID.
	 * @param damageList
	 *            - <i>int[]</i>- The Item's damage list.
	 * @param hasGif
	 *            - Does this Item have an animated image? */
	public ItemData(boolean isBlock, int idNum, String idString, int[] damageList)
	{
		super(isBlock, idNum, idString);
		this.damageList = damageList;
	}

	@Override
	public String getName(int damage)
	{
		return Lang.get("ITEMS:" + getId() + "_0");
	}

	@Override
	public ImageIcon getTexture(int damage)
	{

		String path = Resources.folder + "textures/";
		int damageToUse = 0;

		if (isBlock()) path += "blocks/";
		else path += "items/";

		if (damage <= getMaxDamage()) damageToUse = damage;

		path += getId() + "/" + Integer.toString(damageList[damageToUse]);

		if (hasGif()) path += ".gif";
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

}
