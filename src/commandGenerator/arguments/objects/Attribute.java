package commandGenerator.arguments.objects;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;

public class Attribute
{

	public static final String[] operations = { "add_value", "add_percent", "set_percent" };

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

		if (this.operation < 2 && this.amount >= 0) display += "+";

		if (this.operation > 0) display += Double.toString(this.amount * 100) + "% " + this.type.getName();
		else display += Double.toString(this.amount) + " " + this.type.getName();

		return display;
	}

	/** @return double - The amount given by the Attribute */
	public double getAmount()
	{
		return this.amount;
	}

	/** @return String - The name of the Attribute */
	public String getName()
	{
		return this.type.getName();
	}

	/** @return int - The operation used by the Attribute */
	public int getOperation()
	{
		return this.operation;
	}

	/** Returns this Attribute's type. */
	public AttributeType getAttributeType()
	{
		return this.type;
	}

	public String save()
	{
		return this.type.getId() + " " + this.amount + " " + this.operation;
	}

	/** @return TagCompound - A Tag version of the Attribute */
	public TagCompound toNBT()
	{
		TagCompound tag = new TagCompound() {
			@Override
			public void askValue()
			{}
		};

		tag.addTag(new TagString("AttributeName").setValue(this.type.getId()));
		tag.addTag(new TagDouble("Amount").setValue(this.amount));
		tag.addTag(new TagInt("Operation").setValue(this.operation));
		tag.addTag(new TagString("Name").setValue(getName()));
		tag.addTag(new TagInt("UUIDMost").setValue(10000));
		tag.addTag(new TagInt("UUIDLeast").setValue(100000));

		return tag;
	}

}
