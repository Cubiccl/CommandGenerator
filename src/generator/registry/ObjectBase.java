package generator.registry;

import generator.main.Text;
import generator.main.Utils;

import java.awt.image.BufferedImage;

/** A basic game Object. */
public abstract class ObjectBase
{
	/** Its ID. */
	private final String id;
	/** Its name. */
	protected Text name;
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
		this.name = new Text(Utils.getObjectTypeNameId(this.getType()).toUpperCase(), this.getId());
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
		return this.name.getValue();
	}

	/** @return This Object's type. */
	public int getType()
	{
		return this.type;
	}
}
