package generator.registry.instance;

public class Coordinates
{
	/** Indexes for positions and relative arrays. */
	public static final int X = 0, Y = 1, Z = 2;

	/** The values of the x, y and z positions. */
	private float[] positions;
	/** A value in this array indicates if the corresponding position is relative. */
	private boolean[] relative;

	/** Creates new Coordinates, not relative.
	 * 
	 * @param x - The x position.
	 * @param y - The y position.
	 * @param z - The z position. */
	public Coordinates(float x, float y, float z)
	{
		this(x, y, z, false, false, false);
	}

	/** Creates new Coordinates.
	 * 
	 * @param x - The x position.
	 * @param y - The y position.
	 * @param z - The z position.
	 * @param xRelative - True if the x position is relative.
	 * @param yRelative - True if the y position is relative.
	 * @param zRelative - True if the z position is relative. */
	public Coordinates(float x, float y, float z, boolean xRelative, boolean yRelative, boolean zRelative)
	{
		this.positions = new float[] { x, y, z };
		this.relative = new boolean[] { xRelative, yRelative, zRelative };
	}

	/** @return An array containing the positions. */
	public float[] getPositions()
	{
		return this.positions;
	}

	/** @return An array containing the relativeness of each position. */
	public boolean[] getRelative()
	{
		return this.relative;
	}

	/** @return The x position of these Coordinates. */
	public float getX()
	{
		return this.getPositions()[X];
	}

	/** @return The y position of these Coordinates. */
	public float getY()
	{
		return this.getPositions()[Y];
	}

	/** @return The z position of these Coordinates. */
	public float getZ()
	{
		return this.getPositions()[Z];
	}

	/** @return True if the x position of these Coordinates is relative. */
	public boolean isXRelative()
	{
		return this.getRelative()[X];
	}

	/** @return True if the x position of these Coordinates is relative. */
	public boolean isYRelative()
	{
		return this.getRelative()[Y];
	}

	/** @return True if the x position of these Coordinates is relative. */
	public boolean isZRelative()
	{
		return this.getRelative()[Z];
	}

	/** @return The String representation of these Coordinates to use in a Command. */
	public String toCommand()
	{
		String command = "";

		if (this.isXRelative())
		{
			command += "~";
			if (this.getX() != 0f) command += this.getX();
		} else command += this.getX();
		command += " ";

		if (this.isYRelative())
		{
			command += "~";
			if (this.getY() != 0f) command += this.getY();
		} else command += this.getY();
		command += " ";

		if (this.isZRelative())
		{
			command += "~";
			if (this.getZ() != 0f) command += this.getZ();
		} else command += this.getZ();

		return command;
	}

}
