package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class ItemData extends Item
{

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
	 *            - <i>int[]</i>- The Item's damage list. */
	public ItemData(boolean isBlock, int idNum, String idString, int[] damageList)
	{
		super(isBlock, idNum, idString);
		this.damageList = damageList;
	}

	/** Returns the different damage list. */
	public int[] getDamageList()
	{
		return this.damageList;
	}

	@Override
	public String getName(int damage)
	{
		return Lang.get("ITEMS:" + this.getId() + "_" + damage);
	}

	@Override
	public ImageIcon getTexture(int damage)
	{

		String path = Resources.folder + "textures/";

		if (this.isBlock()) path += "blocks/";
		else path += "items/";

		path += this.getCommandId() + "/" + Integer.toString(damage) + ".png";

		try
		{
			return new ImageIcon(path);
		} catch (Exception ex)
		{
			DisplayHelper.missingTexture(path);
			return null;
		}
	}

	public String getDefaultName()
	{
		return Lang.get("ITEMS:" + this.getId() + "_" + this.damageList[0]);
	}

}
