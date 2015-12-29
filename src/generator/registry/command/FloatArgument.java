package generator.registry.command;

import generator.CommandGenerator;
import generator.main.GenerationException;

public class FloatArgument extends StringArgument
{
	/** Bounds for the value. */
	private float min, max;

	public FloatArgument(boolean isCompulsory, String textID)
	{
		super(isCompulsory, textID);
		this.min = Float.MIN_VALUE;
		this.max = Float.MAX_VALUE;
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
		try
		{
			float value = Float.parseFloat(this.getValue());
			if (value < this.min || value > this.max)
			{
				if (this.min != Float.MIN_VALUE && this.max != Float.MAX_VALUE) throw new GenerationException(CommandGenerator
						.translate("GUI:error.number.bounds").replaceAll("<value>", this.getValue()).replaceAll("<min>", Float.toString(this.min))
						.replaceAll("<max>", Float.toString(this.max)));
				else if (this.min != Float.MIN_VALUE) throw new GenerationException(CommandGenerator.translate("GUI:error.number.min")
						.replaceAll("<value>", this.getValue()).replaceAll("<min>", Float.toString(this.min)));
				else if (this.max != Float.MAX_VALUE) throw new GenerationException(CommandGenerator.translate("GUI:error.number.max")
						.replaceAll("<value>", this.getValue()).replaceAll("<max>", Float.toString(this.max)));
			}
		} catch (NumberFormatException e)
		{
			CommandGenerator.log(e);
			throw new GenerationException(CommandGenerator.translate("GUI:error.number").replaceAll("<value>", this.getValue()));
		}
	}
}
