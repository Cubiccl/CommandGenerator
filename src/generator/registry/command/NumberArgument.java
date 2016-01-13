package generator.registry.command;

import generator.main.GenerationException;
import generator.main.Text;

/** A number argument. Can have bounds and be an integer or a float. */
public class NumberArgument extends StringArgument
{
	/** True if the number should be an integer. */
	private boolean isInteger;
	/** Bounds for the value. */
	private float min, max;

	public NumberArgument(boolean isCompulsory, Text textID)
	{
		super(isCompulsory, textID);
		this.min = Float.NEGATIVE_INFINITY;
		this.max = Float.POSITIVE_INFINITY;
		this.isInteger = false;
	}

	/** Sets this Argument to Integer mode. */
	public void setInteger()
	{
		this.isInteger = true;
	}

	public void setMax(float max)
	{
		this.max = max;
	}

	public void setMin(float min)
	{
		this.min = min;
	}

	@Override
	protected void verifyValue(String value) throws GenerationException
	{
		super.verifyValue(value);

		if (this.isInteger) try
		{
			Integer.parseInt(value);
		} catch (NumberFormatException e)
		{
			throw new GenerationException(new Text("GUI", "error.integer").addReplacement("<value>", value));
		}

		try
		{
			float valueF = Float.parseFloat(value);
			if (valueF < this.min || valueF > this.max)
			{
				if (this.min != Float.NEGATIVE_INFINITY && this.max != Float.POSITIVE_INFINITY) throw new GenerationException(new Text("GUI",
						"error.number.bounds", false).addReplacement("<value>", value).addReplacement("<min>", Float.toString(this.min))
						.addReplacement("<max>", Float.toString(this.max)));
				else if (this.min != Float.NEGATIVE_INFINITY) throw new GenerationException(new Text("GUI", "error.number.min", false).addReplacement(
						"<value>", value).addReplacement("<min>", Float.toString(this.min)));
				else if (this.max != Float.POSITIVE_INFINITY) throw new GenerationException(new Text("GUI", "error.number.max", false).addReplacement(
						"<value>", value).addReplacement("<max>", Float.toString(this.max)));
			}
		} catch (NumberFormatException e)
		{
			throw new GenerationException(new Text("GUI", "error.number", false).addReplacement("<value>", value));
		}
	}
}
