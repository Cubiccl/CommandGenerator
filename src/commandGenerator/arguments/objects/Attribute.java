package commandGenerator.arguments.objects;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;

public class Attribute
{

	/** The amount given by the Attribute */
	private double amount;
	/** The operation used by the Attribute */
	private int operation;
	/** The type of the Attribute */
	private AttributeType type;

	/** New Attribute
	 * 
	 * @param type
	 *            - <i>AttributeType</i> - The type of the Attribute
	 * @param amount
	 *            - <i>double</i> - The amount given by the Attribute
	 * @param operation
	 *            - <i>int</i> - The operation used by the Attribute */
	public Attribute(AttributeType type, double amount, int operation)
	{
		this.type = type;
		this.amount = amount;
		this.operation = operation;
	}

	/** Returns a String version of this Attribute to be displayed to the user. */
	public String display()
	{
		String display = "";

		if (operation < 2 && amount >= 0) display += "+";

		if (operation > 0) display += Double.toString(amount * 100) + "% " + type.getName();
		else display += Double.toString(amount) + " " + type.getName();

		return display;
	}

	/** @return double - The amount given by the Attribute */
	public double getAmount()
	{
		return amount;
	}

	/** @return String - The name of the Attribute */
	public String getName()
	{
		return type.getName();
	}

	/** @return int - The operation used by the Attribute */
	public int getOperation()
	{
		return operation;
	}

	/** Returns this Attribute's type. */
	public AttributeType getType()
	{
		return type;
	}

	/** @return TagCompound - A Tag version of the Attribute */
	public TagCompound toNBT()
	{
		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};

		tag.addTag(new TagString("AttributeName").setValue(type.getId()));
		tag.addTag(new TagDouble("Amount").setValue(amount));
		tag.addTag(new TagInt("Operation").setValue(operation));
		tag.addTag(new TagString("Name").setValue(getName()));
		tag.addTag(new TagInt("UUIDMost").setValue(10000));
		tag.addTag(new TagInt("UUIDLeast").setValue(100000));

		return tag;
	}

}
