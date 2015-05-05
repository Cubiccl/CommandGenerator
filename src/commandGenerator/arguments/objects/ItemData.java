package commandGenerator.arguments.objects;

import java.io.File;

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
	@Override
	public int[] getDamageList()
	{
		return this.damageList;
	}

	@Override
	public String getName(int damage)
	{
		for (int i = 0; i < this.damageList.length; i++)
			if (this.damageList[i] == damage) return this.names[i];
		return this.names[0];
	}

	@Override
	public ImageIcon getTexture(int damage)
	{
		if (this.isTextureUnique()) return this.textures[0];

		int index = 0;
		for (int i = 0; i < this.damageList.length; i++)
			if (damage == this.damageList[i]) index = i;

		return this.textures[index];
	}

	public String getDefaultName()
	{
		if (this.names == null) return this.getCommandId();
		return this.names[0];
	}

	public void registerTextures()
	{
		String path = Resources.folder + "textures/";

		if (this.isBlock()) path += "blocks/";
		else path += "items/";

		if (!this.isTextureUnique())
		{
			this.textures = new ImageIcon[this.damageList.length];
			path += this.getCommandId() + "/";

			for (int damage = 0; damage < this.damageList.length; damage++)
			{
				String finalPath = path + this.damageList[damage] % this.getTextureType() + ".png";
				if (this.getTextureType() == 1) finalPath = path + this.damageList[damage] + ".png";
				this.textures[damage] = new ImageIcon(finalPath);
				if (!new File(finalPath).exists()) DisplayHelper.missingTexture(finalPath);
			}

		} else
		{
			this.textures = new ImageIcon[1];

			if (this.isBlock()) path += "other_blocks/" + this.getCommandId() + ".png";
			else path += "other_items/" + this.getCommandId() + ".png";

			this.textures[0] = new ImageIcon(path);
			if (!new File(path).exists()) DisplayHelper.missingTexture(path);
		}
	}

	@Override
	public void updateLang()
	{
		this.names = new String[this.damageList.length];
		for (int damage = 0; damage < this.damageList.length; damage++)
		{
			if (this.langType.equals("normal")) this.names[damage] = Lang.get("ITEMS:" + this.getId() + "_" + this.damageList[damage]);
			else if (this.langType.startsWith("half_"))
			{
				if (this.damageList[damage] < 8) this.names[damage] = Lang.get("ITEMS:" + this.getId() + "_" + damage);
				else this.names[damage] = Lang.get("ITEMS:" + this.getId() + "_" + (this.damageList[damage] - 8)) + " " + Lang.get("ITEMS:" + this.langType);
			} else this.names[damage] = Lang.get("ITEMS:" + this.getId()) + " " + Lang.get("ITEMS:" + this.langType + "_" + this.damageList[damage]);
		}
	}
}
