package generator.registry;

import java.awt.image.BufferedImage;

import generator.main.Utils;

public class Achievement extends ObjectBase
{
	private Item itemIcon;

	public Achievement(String id, Item itemIcon)
	{
		super(Utils.ACHIEVEMENT, id);
		this.itemIcon = itemIcon;
	}
	
	@Override
	public BufferedImage getIcon()
	{
		return this.itemIcon.getIcon();
	}

}
