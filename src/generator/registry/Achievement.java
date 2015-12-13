package generator.registry;

import java.awt.image.BufferedImage;

import generator.main.Constants;

public class Achievement extends ObjectBase
{
	private Item itemIcon;

	public Achievement(String id, Item itemIcon)
	{
		super(Constants.ACHIEVEMENT, id);
		this.itemIcon = itemIcon;
	}
	
	@Override
	public BufferedImage getIcon()
	{
		return this.itemIcon.getIcon();
	}

}
