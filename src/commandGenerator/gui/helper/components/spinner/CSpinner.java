package commandGenerator.gui.helper.components.spinner;

import commandGenerator.gui.helper.components.icomponent.ISpin;

@SuppressWarnings("serial")
public class CSpinner extends BaseSpinner
{
	private int min, max;

	public CSpinner(int min, int max, ISpin parent)
	{
		super(min, max, parent);
	}

	public int getMin()
	{
		return this.min;
	}

	public int getMax()
	{
		return this.max;
	}

	public void setValues(int min, int max)
	{
		this.min = min;
		this.max = max;
		int value = (int) this.getValue();
		super.setValues(min, max);
		if (value >= this.min && value <= this.max) this.setValue(value);
	}

}
