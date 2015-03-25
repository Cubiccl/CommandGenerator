package commandGenerator.arguments.command;

import java.awt.Component;
import java.util.List;

public abstract class Argument
{

	/** The different Argument types.
	 * <ul>
	 * <li><strong>NORMAL</strong> : Nothing special about this argument.</li>
	 * <li><strong>NBT</strong> : Needs another argument to generates its command.</li>
	 * </ul> */
	public static final byte NORMAL = 0, NBT = 1;

	/** The Argument ID. */
	private final String id;

	/** Whether this argument is compulsery. */
	private final boolean isCompulsery;

	/** Whether the GUI has been initialized. */
	private boolean isGuiInit;

	/** The number of elements this Argument needs. */
	private int length, maxLength;

	/** The Argument type. */
	private final byte type;

	/** Creates a new Argument. Default values : Normal type, compulsery, length = 1.
	 * 
	 * @param id
	 *            - <i>String</i> - The ID of this Argument. */
	public Argument(String id)
	{
		this(id, NORMAL, true, 1);
	}

	/** Creates a new Argument. Default values : Normal type, length = 1.
	 * 
	 * @param id
	 *            - <i>String</i> - The ID of this Argument.
	 * @param isCompulsery
	 *            - <i>boolean</i> - True if this Argument should always be included in the command. */
	public Argument(String id, boolean isCompulsery)
	{
		this(id, NORMAL, isCompulsery, 1);
	}

	/** Creates a new Argument.
	 * 
	 * @param id
	 *            - <i>String</i> - The ID of this Argument.
	 * @param type
	 *            - <i>byte</i> - The type of this Argument.
	 * @param isCompulsery
	 *            - <i>boolean</i> - True if this Argument should always be included in the command.
	 * @param length
	 *            - <i>int</i> - The number of words needed for this Argument. */
	public Argument(String id, byte type, boolean isCompulsery, int length)
	{
		this.id = id;
		this.type = type;
		this.isCompulsery = isCompulsery;
		this.length = length;
		this.isGuiInit = false;
		this.maxLength = length;
	}

	/** Generates the command part created by this Argument. */
	public abstract String generateCommand();

	/** Gets the Component representing this Argument. */
	protected abstract Component generateComponent();

	/** Returns the Component representing this Argument. */
	public Component getComponent()
	{
		if (!isGuiInit)
		{
			initGui();
			isGuiInit = true;
		}
		return generateComponent();
	}

	/** Gets this Argument's ID. */
	public String getId()
	{
		return this.id;
	}

	/** Gets this Argument's length. */
	public int getLength()
	{
		return this.length;
	}

	/** Gets this Argument's maximum length. */
	public int getMaximumLength()
	{
		return this.maxLength;
	}

	/** Gets this Argument's type. */
	public byte getType()
	{
		return this.type;
	}

	/** Initializes the GUI. */
	public abstract void initGui();

	/** True if this Argument is compulsery. */
	public boolean isCompulsery()
	{
		return this.isCompulsery;
	}

	/** True if this Argument is currently used. */
	public abstract boolean isUsed();

	/** True if this Argument matches the data.
	 * 
	 * @param data
	 *            - <i>List : String</i> - The data this Argument should match. */
	public abstract boolean matches(List<String> data);

	/** Sets this Argument's maximum length. */
	public void setMaximumLength(int maxLength)
	{
		this.maxLength = maxLength;
	}

	/** Will change the GUI in order to match the data.
	 * 
	 * @param data
	 *            - <i>List : String</i> - The data this Argument should match. */
	public abstract void setupFrom(List<String> data);
}
