package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagDouble;

public class Coordinates
{

	/** Used to get parts of Coordinates. */
	public static final int X = 0, Y = 1, Z = 2;

	private boolean isFloat;

	/** Details about this Coordinates. */
	private boolean isRotation;

	private boolean[] relativeness;

	/** The Coordinates' positions. */
	private double x, y, z;

	/** The Coordinates rotations. */
	private float xRotation, yRotation;

	/** Creates new Coordinates. */
	public Coordinates(double x, double y, double z, boolean isFloat)
	{
		this(x, y, z, new boolean[] { false, false, false }, isFloat);
	}

	/** Creates new Coordinates. */
	public Coordinates(double x, double y, double z, boolean[] relative, boolean isFloat)
	{

		this.x = x;
		this.y = y;
		this.z = z;
		this.relativeness = relative;
		this.isFloat = isFloat;

		this.isRotation = false;
	}

	/** Creates new Coordinates. */
	public Coordinates(double x, double y, double z, float xRotation, float yRotation, boolean[] relative, boolean isFloat)
	{

		this.x = x;
		this.y = y;
		this.z = z;
		this.xRotation = xRotation;
		this.yRotation = yRotation;
		this.relativeness = relative;
		this.isRotation = true;
		this.isFloat = isFloat;

	}

	/** Generates the command structure for the coordinates */
	public String commandStructure()
	{

		String display = "";
		if (this.relativeness[0]) display += "~";
		if (this.x != 0d || !this.relativeness[0])
		{
			if (this.isFloat) display += Double.toString(this.x);
			else display += Integer.toString((int) Math.floor(this.x));
		}
		display += " ";

		if (this.relativeness[1]) display += "~";
		if (this.y != 0d || !this.relativeness[1])
		{
			if (this.isFloat) display += Double.toString(this.y);
			else display += Integer.toString((int) Math.floor(this.y));
		}
		display += " ";

		if (this.relativeness[2] && this.relativeness[2]) display += "~";
		if (this.z != 0d || !this.relativeness[1])
		{
			if (this.isFloat) display += Double.toString(z);
			else display += Integer.toString((int) Math.floor(z));
		}

		if (this.isRotation) display += " " + Float.toString(this.xRotation) + " " + Float.toString(this.yRotation);

		return display;
	}

	/** Returns the specified coordinate. */
	public double getCoord(int coord)
	{
		switch (coord)
		{
			case X:
				return this.x;
			case Y:
				return this.y;
			case Z:
				return this.z;
			default:
				return 0;
		}
	}

	public float getRot(int rot)
	{
		switch (rot)
		{
			case X:
				return this.xRotation;
			case Y:
				return this.yRotation;
			default:
				return 0F;
		}
	}

	/** Returns true if this Coordinates has rotations. */
	public boolean getRotation()
	{
		return this.isRotation;
	}

	/** Returns true if this Coordinates are relative. */
	public boolean isRelative(int coord)
	{
		if (coord > 2 || coord < 0) return false;
		return this.relativeness[coord];
	}

	public String save()
	{
		String save = this.x + " " + this.y + " " + this.z + " ";
		if (this.isRotation) save += this.xRotation + " " + this.yRotation + " ";
		save += this.relativeness[0] + " " + this.relativeness[1] + " " + this.relativeness[2] + " " + this.isFloat;
		return save;
	}

	/** Turns the Coordinates into a TagList. */
	public List<Tag> toTagPos()
	{
		List<Tag> tag = new ArrayList<Tag>();
		tag.add(new TagDouble().setValue(this.x));
		tag.add(new TagDouble().setValue(this.y));
		tag.add(new TagDouble().setValue(this.z));
		return tag;
	}

}
