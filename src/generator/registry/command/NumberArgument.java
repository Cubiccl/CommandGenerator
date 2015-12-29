package generator.registry.command;

import generator.CommandGenerator;
import generator.main.GenerationException;

/** A number argument. Can have bounds and be an integer or a float. */
public class NumberArgument extends StringArgument
{
	/** True if the number should be an integer. */
	private boolean isInteger;
	/** Bounds for the value. */
	private float min, max;

	public NumberArgument(boolean isCompulsory, String textID)
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
	protected void verifyValue() throws GenerationException
	{
		super.verifyValue();

		if (this.isInteger) try
		{
			Integer.parseInt(this.getValue());
		} catch (NumberFormatException e)
		{
			throw new GenerationException(CommandGenerator.translate("GUI:error.integer").replaceAll("<value>", this.getValue()));
		}

		try
		{
			float value = Float.parseFloat(this.getValue());
			if (value < this.min || value > this.max)
			{
				if (this.min != Float.NEGATIVE_INFINITY && this.max != Float.POSITIVE_INFINITY) throw new GenerationException(CommandGenerator
						.translate("GUI:error.number.bounds").replaceAll("<value>", this.getValue()).replaceAll("<min>", Float.toString(this.min))
						.replaceAll("<max>", Float.toString(this.max)));
				else if (this.min != Float.NEGATIVE_INFINITY) throw new GenerationException(CommandGenerator.translate("GUI:error.number.min")
						.replaceAll("<value>", this.getValue()).replaceAll("<min>", Float.toString(this.min)));
				else if (this.max != Float.POSITIVE_INFINITY) throw new GenerationException(CommandGenerator.translate("GUI:error.number.max")
						.replaceAll("<value>", this.getValue()).replaceAll("<max>", Float.toString(this.max)));
			}
		} catch (NumberFormatException e)
		{
			throw new GenerationException(CommandGenerator.translate("GUI:error.number").replaceAll("<value>", this.getValue()));
		}
	}
}
