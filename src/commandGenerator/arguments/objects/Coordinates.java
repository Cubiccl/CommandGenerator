package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.main.DisplayHelper;

public class Coordinates
{

	public static final int X = 0, Y = 1, Z = 2;

	private double x, y, z;

	float xRotation, yRotation;
	private boolean areRelative, isRotation;

	/** Creates new Coordinates. */
	public Coordinates(double x, double y, double z)
	{

		this.x = x;
		this.y = y;
		this.z = z;

		this.areRelative = false;
		this.isRotation = false;

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

	/** Creates new Coordinates. */
	public Coordinates(int x, int y, int z, int xRotation, int yRotation)
	{

		this.x = x;
		this.y = y;
		this.z = z;
		this.xRotation = xRotation;
		this.yRotation = yRotation;

		this.areRelative = false;
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

	/** Turns the Coordinates into a TagList. */
	public List<Tag> toTagPos()
	{
		List<Tag> tag = new ArrayList<Tag>();
		tag.add(new TagDouble().setValue(x));
		tag.add(new TagDouble().setValue(y));
		tag.add(new TagDouble().setValue(z));
		return tag;
	}

	public boolean getRelative()
	{
		return areRelative;
	}

	public boolean getRotation()
	{
		return isRotation;
	}

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

}
