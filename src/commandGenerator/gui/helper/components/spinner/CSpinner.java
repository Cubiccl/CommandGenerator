package commandGenerator.gui.helper.components.spinner;

import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.icomponent.ISpin;

@SuppressWarnings("serial")
public class CSpinner extends JSpinner implements CComponent
{
	private int min, max;
	private ISpin parent;

	public CSpinner(int min, int max, ISpin parent)
	{
		super(new SpinnerNumberModel(min, min, max, 1));

		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				select();
			}
		});
		this.setSize(200, 20);
	}

	private void select()
	{
		this.parent.updateSpinner();
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

	@Override
	public void setSize(int width, int height)
	{
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
	}

	public void setValues(int min, int max)
	{
		this.min = min;
		this.max = max;
		int value = (int) this.getValue();
		this.setModel(new SpinnerNumberModel(this.min, this.min, this.max, 1));
		if (value >= this.min && value <= this.max) this.setValue(value);
	}

}
