package commandGenerator.arguments.command;

import java.awt.Component;

public abstract class Argument
{

	public static final byte STATIC = 0, BOOLEAN = 1, CHOICE = 2, STRING = 3, INT = 4, FLOAT = 5, COORD = 6, TARGET = 7, BLOCK = 8, ITEM = 9, ENTITY = 10,
			ACHIEVEMENT = 11, JSON = 12, EFFECT = 13, ENCHANTMENT = 14, SOUND = 15, PARTICLE = 16, NBT = 17, MISC = 18;

	/** The Argument ID. */
	private final String id;

	/** The Argument type. */
	private final byte type;

	/** Whether this argument is compulsery. */
	private final boolean isCompulsery;
	private boolean isGuiInit, hasSpace;

	/** The number of elements this Argument needs. */
	private int length, maxLength;

	public Argument(String id, byte type, boolean isCompulsery, int length)
	{
		this.id = id;
		this.type = type;
		this.isCompulsery = isCompulsery;
		this.length = length;
		this.isGuiInit = false;
		this.hasSpace = true;
		this.maxLength = -1;
	}

	public String getId()
	{
		return this.id;
	}

	public boolean isCompulsery()
	{
		return this.isCompulsery;
	}

	public byte getType()
	{
		return this.type;
	}

	public int getLength()
	{
		return this.length;
	}

	public void setMaximumLength(int maxLength)
	{
		this.maxLength = maxLength;
	}

	public Component getComponent()
	{
		if (!isGuiInit)
		{
			initGui();
			isGuiInit = true;
		}
		return generateComponent();
	}

	public Argument setHasNoSpace()
	{
		this.hasSpace = false;
		return this;
	}

	public abstract Component generateComponent();

	public abstract void initGui();

	public abstract String generateCommand();

	public abstract boolean isUsed();

	public boolean hasSpace()
	{
		return this.hasSpace;
	}
}
