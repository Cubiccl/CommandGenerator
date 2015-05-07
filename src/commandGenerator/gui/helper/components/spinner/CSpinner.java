package commandGenerator.gui.helper.components.spinner;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.icomponent.ISpin;

@SuppressWarnings("serial")
public class CSpinner extends BaseSpinner implements CComponent
{
	private int min, max;

	public CSpinner(int min, int max, ISpin parent)
	{
		super(min, max, parent);
	}

	@Override
	public void reset()
	{
		this.setValue(min);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
	}

	@Override
	public void updateLang()
	{}

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
		this.setValues(this.min, this.max);
		if (value >= this.min && value <= this.max) this.setValue(value);
	}

}
