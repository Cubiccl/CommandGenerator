package generator.registry;

import java.awt.image.BufferedImage;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;
import generator.main.Constants;

public abstract class ObjectBase implements ITranslate
{
	private final String id;
	protected String name;
	private final int type;

	public ObjectBase(int type, String id)
	{
		this.id = id;
		this.type = type;
	}

	protected void createIcon()
	{}

	public void complete()
	{
		this.updateLang();
		this.createIcon();
	}

	public String getDescription()
	{
		return "";
	}

	public BufferedImage getIcon()
	{
		return null;
	}

	public String getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public int getType()
	{
		return this.type;
	}

	@Override
	public void updateLang()
	{
		this.name = CommandGenerator.translate(Constants.OBJECT_NAMES[this.getType()].toUpperCase() + ":" + this.getId());
	}
}
