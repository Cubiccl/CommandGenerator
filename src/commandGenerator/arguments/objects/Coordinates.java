package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.main.DisplayHelper;

public class Coordinates
{

	public static final int X = 0, Y = 1, Z = 2;
	private boolean isFloat;

	/** Generates Coordinates from command structure. */
	public static Coordinates generateFrom(String x, String y, String z)
	{
		double cx, cy, cz;
		boolean[] relative = { x.startsWith("~"), y.startsWith("~"), z.startsWith("~") };

		try
		{

			if (relative[0]) x = x.substring(1);
			if (relative[1]) y = y.substring(1);
			if (relative[2]) z = z.substring(1);

			if (!x.equals("")) cx = Double.parseDouble(x);
			else cx = 0;
			if (!y.equals("")) cy = Double.parseDouble(y);
			else cy = 0;
			if (!z.equals("")) cz = Double.parseDouble(z);
			else cz = 0;

			Coordinates coords = new Coordinates(cx, cy, cz, relative, true);
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
		boolean[] relative = { x.startsWith("~"), y.startsWith("~"), z.startsWith("~") };

		try
		{

			if (relative[0]) x = x.substring(1);
			if (relative[1]) y = y.substring(1);
			if (relative[2]) z = z.substring(1);

			if (!x.equals("")) cx = Double.parseDouble(x);
			else cx = 0;
			if (!y.equals("")) cy = Double.parseDouble(y);
			else cy = 0;
			if (!z.equals("")) cz = Double.parseDouble(z);
			else cz = 0;

			Coordinates coords = new Coordinates(cx, cy, cz, rotX, rotY, relative, true);
			DisplayHelper.log("Created coordinates : " + coords.commandStructure());
			return coords;

		} catch (Exception e)
		{
			DisplayHelper.log("There was an error while creating coordinates : " + x + " " + y + " " + z);
			return null;
		}

	}

	/** Details about this Coordinates. */
	private boolean isRotation;

	/** The Coordinates' positions. */
	private double x, y, z;

	private boolean[] relativeness;

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
		if (relativeness[0]) display += "~";
		if (x != 0d || !relativeness[0])
		{
			if (isFloat) display += Double.toString(x);
			else display += Integer.toString((int) Math.floor(x));
		}
		display += " ";

		if (relativeness[1]) display += "~";
		if (y != 0d || !relativeness[1])
		{
			if (isFloat) display += Double.toString(y);
			else display += Integer.toString((int) Math.floor(y));
		}
		display += " ";

		if (relativeness[2] && relativeness[2]) display += "~";
		if (z != 0d || !relativeness[1])
		{
			if (isFloat) display += Double.toString(z);
			else display += Integer.toString((int) Math.floor(z));
		}

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
	public boolean isRelative(int coord)
	{
		if (coord > 2 || coord < 0) return false;
		return relativeness[coord];
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

	public String save()
	{
		String save = x + " " + y + " " + z + " ";
		if (isRotation) save += xRotation + " " + yRotation + " ";
		save += relativeness[0] + " " + relativeness[1] + " " + relativeness[2] + " " + isFloat;
		return save;
	}

}
