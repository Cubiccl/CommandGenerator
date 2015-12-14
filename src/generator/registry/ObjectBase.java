package generator.registry;

import java.awt.image.BufferedImage;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;
import generator.main.Utils;

/** A basic game Object. */
public abstract class ObjectBase implements ITranslate
{
	/** Its ID. */
	private final String id;
	/** Its name. */
	protected String name;
	/** Its type. */
	private final int type;

	/** Creates a new Object
	 * 
	 * @param type - Its type.
	 * @param id - Its ID. */
	public ObjectBase(int type, String id)
	{
		this.id = id;
		this.type = type;
	}

	/** Finalizes this Object. Creates language & textures. */
	public void complete()
	{
		this.updateLang();
		this.createIcon();
	}

	/** Creates this Object's Icon. */
	protected void createIcon()
	{}

	/** @return This Object's description. */
	public String getDescription()
	{
		return "";
	}

	/** @return This Object's Icon. */
	public BufferedImage getIcon()
	{
		return null;
	}

	/** @return This Object's ID. */
	public String getId()
	{
		return this.id;
	}

	/** @return This Object's name. */
	public String getName()
	{
		return this.name;
	}

	/** @return This Object's type. */
	public int getType()
	{
		return this.type;
	}

	@Override
	public void updateLang()
	{
		this.name = CommandGenerator.translate(Utils.getObjectTypeNameId(this.getType()).toUpperCase() + ":" + this.getId());
	}
}
