package commandGenerator.arguments.objects;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;

public class Attribute
{

	/** The type of the Attribute */
	private AttributeType type;
	/** The amount given by the Attribute */
	private double amount;
	/** The operation used by the Attribute */
	private int operation;

	/** New Attribute
	 * 
	 * @param type
	 *            - The type of the Attribute
	 * @param amount
	 *            - The amount given by the Attribute
	 * @param operation
	 *            - The operation used by the Attribute */
	public Attribute(AttributeType type, double amount, int operation)
	{
		this.type = type;
		this.amount = amount;
		this.operation = operation;
	}

	/** @return String - The displayed Attribute */
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

	/** @return TagCompound - A Tag version of the Attribute */
	public TagCompound toNBT()
	{
		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};

		tag.addTag(new TagString("AttributeName").setValue(getName()));
		tag.addTag(new TagDouble("Amount").setValue(amount));
		tag.addTag(new TagInt("Operation").setValue(operation));
		tag.addTag(new TagString("Name").setValue(getName()));
		tag.addTag(new TagInt("UUIDMost").setValue(10000));
		tag.addTag(new TagInt("UUIDLeast").setValue(100000));

		return tag;
	}

	public AttributeType getType()
	{
		return type;
	}

}
