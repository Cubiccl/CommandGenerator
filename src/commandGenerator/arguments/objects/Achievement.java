package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.Lang;

public class Achievement extends ObjectBase
{

	/** The Item which texture should be used as this Achievement's texture. */
	private Item itemTexture;

	/** Creates a new Achievement
	 * 
	 * @param id
	 *            - <i>String</i> - The achievement ID
	 * @param this.itemTexture - <i>Item</i> - The item which will give its texture to the achievement */
	public Achievement(String id, Item itemTexture)
	{
		super(id, ACHIEVEMENT);
		this.itemTexture = itemTexture;
	}

	/** Generates the command structure for this Achievement. */
	public String commandStructure()
	{
		return "achievement." + this.getId();
	}

	/** Returns this Achievement's name. */
	@Override
	public String getName()
	{
		return Lang.get("ACHIEVEMENTS:" + this.getId());
	}

	/** Returns this Achievement's texture */
	@Override
	public ImageIcon getTexture()
	{
		return this.itemTexture.getTexture(0);
	}

}
