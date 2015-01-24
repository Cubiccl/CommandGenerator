package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class ItemData extends Item {

	private int[] damageList;

	/** Creates a new Item with specific damage.
	 * 
	 * @param isBlock
	 *            - Is this Item a Block?
	 * @param idNum
	 *            - The Item's numerical ID.
	 * @param idString
	 *            - The Item's text ID.
	 * @param damageList
	 *            - The Item's damage list.
	 * @param hasGif
	 *            - Does this Item have an animated image? */
	public ItemData(boolean isBlock, int idNum, String idString, int[] damageList) {
		super(isBlock, idNum, idString);
		this.damageList = damageList;
	}

	public int getDamage(int damage) {
		return damageList[damage];
	}

	@Override
	public String getName(int damage) {
		
		String category;
		if (isBlock()) category = "BLOCKS:";
		else category = "ITEMS:";
		
		if (damage >= damageList.length) return Lang.get(category + getId() + "_" + damageList[0]);
		return Lang.get(category + getId() + "_" + damageList[damage]);
	}
	
	public int getMaxDamage() {
		return damageList.length - 1;
	}

	@Override
	public ImageIcon getTexture(int damage) {

		String path = Resources.folder + "textures/";
		int damageToUse = 0;

		if (isBlock()) path += "blocks/";
		else path += "items/";

		if (damage <= getMaxDamage()) damageToUse = damage;

		path += getId() + "/" + Integer.toString(damageList[damageToUse]);

		if (hasGif()) path += ".gif";
		else path += ".png";
		try {
			return new ImageIcon(path);
		} catch (Exception ex) {
			DisplayHelper.missingTexture(path);
			return null;
		}
	}

}
