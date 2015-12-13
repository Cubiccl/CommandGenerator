package generator.registry;

import java.awt.image.BufferedImage;

import generator.main.Utils;
import generator.main.FileManager;

public class Entity extends ObjectBase
{

	private BufferedImage icon;

	public Entity(String id)
	{
		super(Utils.ENTITY, id);
	}

	@Override
	protected void createIcon()
	{
		this.icon = FileManager.loadTexture("entities/" + this.getId());
	}

	@Override
	public BufferedImage getIcon()
	{
		return this.icon;
	}

}
