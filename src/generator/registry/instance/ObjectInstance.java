package generator.registry.instance;

import generator.main.Utils;

/** Represents an actual instanciated object of the game (Itemstack, active effect...) */
public abstract class ObjectInstance
{

	/** This Object's type.
	 * 
	 * @see Utils#ITEM */
	private final int type;

	public ObjectInstance(int type)
	{
		this.type = type;
	}

	/** @return The type of this Object instance. */
	public int getType()
	{
		return this.type;
	}

	/** @return The String representation of this Object instance to use in a Command. */
	public abstract String toCommand();

}
