package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.main.DisplayHelper;

public class Coordinates
{

	public static final int X = 0, Y = 1, Z = 2;

	/** Generates Coordinates from command structure. */
	public static Coordinates generateFrom(String x, String y, String z)
	{
		double cx, cy, cz;
		boolean relative = x.startsWith("~");

		try
		{

			if (relative)
			{
				x = x.substring(1);
				y = y.substring(1);
				z = z.substring(1);
			}

			if (!x.equals("")) cx = Double.parseDouble(x);
			else cx = 0;
			if (!y.equals("")) cy = Double.parseDouble(y);
			else cy = 0;
			if (!z.equals("")) cz = Double.parseDouble(z);
			else cz = 0;

			Coordinates coords = new Coordinates(cx, cy, cz, relative);
			DisplayHelper.log("Created coordinates : " + coords.commandStructure());
			return coords;

		} catch (Exception e)
		{
			DisplayHelper.log("There was an error while creating coordinates : " + x + " " + y + " " + z);
			return null;
		}

	}

	/** Generates Coordinates from command structure. */
	public static Coordinates generateFromWithRot(String x, String y, String z, float rotX, float rotY)
	{
		double cx, cy, cz;
		boolean relative = x.startsWith("~");

		try
		{

			if (relative)
			{
				x = x.substring(1);
				y = y.substring(1);
				z = z.substring(1);
			}

			if (!x.equals("")) cx = Double.parseDouble(x);
			else cx = 0;
			if (!y.equals("")) cy = Double.parseDouble(y);
			else cy = 0;
			if (!z.equals("")) cz = Double.parseDouble(z);
			else cz = 0;

			Coordinates coords = new Coordinates(cx, cy, cz, rotX, rotY, relative);
			DisplayHelper.log("Created coordinates : " + coords.commandStructure());
			return coords;

		} catch (Exception e)
		{
			DisplayHelper.log("There was an error while creating coordinates : " + x + " " + y + " " + z);
			return null;
		}

	}
	/** Details about this Coordinates. */
	private boolean areRelative, isRotation;

	/** The Coordinates' positions. */
	private double x, y, z;

	/** The Coordinates rotations. */
	private float xRotation, yRotation;

	/** Creates new Coordinates. */
	public Coordinates(double x, double y, double z)
	{
		this(x, y, z, false);
	}

	/** Creates new Coordinates. */
	public Coordinates(double x, double y, double z, boolean areRelative)
	{

		this.x = x;
		this.y = y;
		this.z = z;
		this.areRelative = areRelative;

		this.isRotation = false;
	}

	/** Creates new Coordinates. */
	public Coordinates(double x, double y, double z, float xRotation, float yRotation, boolean areRelative)
	{

		this.x = x;
		this.y = y;
		this.z = z;
		this.xRotation = xRotation;
		this.yRotation = yRotation;
		this.areRelative = areRelative;
		this.isRotation = true;

	}

	/** Generates the command structure for the coordinates */
	public String commandStructure()
	{

		if (x == 0 && y == 0 && z == 0 && this.areRelative)
		{
			if (this.isRotation) return "~ ~ ~ " + Float.toString(xRotation) + " " + Float.toString(yRotation);
			else return "~ ~ ~";
		}

		String display = "";
		if (this.areRelative) display += "~";
		display += Double.toString(x) + " ";
		if (this.areRelative) display += "~";
		display += Double.toString(y) + " ";
		if (this.areRelative) display += "~";
		display += Double.toString(z);

		if (this.isRotation) display += " " + Float.toString(xRotation) + " " + Float.toString(yRotation);

		return display;
	}

	/** Returns the specified coordinate. */
	public double getCoord(int coord)
	{
		switch (coord)
		{
			case X:
				return x;
			case Y:
				return y;
			case Z:
				return z;
			default:
				return 0;
		}
	}

	/** Returns true if this Coordinates are relative. */
	public boolean getRelative()
	{
		return areRelative;
	}

	public float getRot(int rot)
	{
		switch (rot)
		{
			case X:
				return xRotation;
			case Y:
				return yRotation;
			default:
				return 0F;
		}
	}

	/** Returns true if this Coordinates has rotations. */
	public boolean getRotation()
	{
		return isRotation;
	}

	/** Turns the Coordinates into a TagList. */
	public List<Tag> toTagPos()
	{
		List<Tag> tag = new ArrayList<Tag>();
		tag.add(new TagDouble().setValue(x));
		tag.add(new TagDouble().setValue(y));
		tag.add(new TagDouble().setValue(z));
		return tag;
	}

}
