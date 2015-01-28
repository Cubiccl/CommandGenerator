package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

public class Achievement extends ObjectBase
{
	/** The Item which texture should be used as this Achievement's texture. */
	private Item itemTexture;

	/** Creates a new Achievement
	 * 
	 * @param id
	 *            - <i>String</i> - The achievement ID
	 * @param itemTexture
	 *            - <i>Item</i> - The item which will give its texture to the achievement */
	public Achievement(String id, Item itemTexture)
	{
		super(id, CGConstants.OBJECT_ACHIEVEMENT);
		this.itemTexture = itemTexture;
	}

	/** Generates the command structure for this Achievement. */
	public String commandStructure()
	{
		return "achievement." + getId();
	}

	/** Returns this Achievement's name. */
	public String getName()
	{
		return Lang.get("ACHIEVEMENTS:" + getId());
	}

	/** Returns this Achievement's texture */
	public ImageIcon getTexture()
	{
		return itemTexture.getTexture(0);
	}

	/** Generates an Achievement from its command structure.
	 * 
	 * @param data
	 *            - <i>String</i> - The command structure. */
	public static Achievement generateFrom(String data)
	{
		if (data.contains("achievement.")) data = data.substring("achievement.".length());

		Achievement ach = (Achievement) Registerer.getObjectFromId(data);
		if (ach != null) DisplayHelper.log("Created achievement " + ach.getId());
		return ach;
	}

}
