package commandGenerator.gui.helper.components.spinner;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.button.BaseButton;
import commandGenerator.gui.helper.components.icomponent.ISpin;

@SuppressWarnings("serial")
public class NumberSpinner extends JPanel implements CComponent
{

	private BaseButton buttonMax, buttonMin;
	private CLabel label;
	private CSpinner spinner;

	public NumberSpinner(String labelTextId, int min, int max, ISpin parent)
	{
		super(new GridBagLayout());

		this.label = new CLabel(labelTextId);

		this.spinner = new CSpinner(min, max, parent);

		this.buttonMax = new BaseButton("++");
		this.buttonMax.setSize(30, 20);
		this.buttonMax.setFont(GuiHandler.SPINNER);
		this.buttonMax.setDrawType(GuiHandler.LEFT);
		this.buttonMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setMax();
			}
		});

		this.buttonMin = new BaseButton("--");
		this.buttonMin.setSize(30, 20);
		this.buttonMin.setFont(GuiHandler.SPINNER);
		this.buttonMin.setDrawType(GuiHandler.FULL);
		this.buttonMin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setMin();
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.label);
		gbc.gridx++;
		this.add(this.spinner);
		gbc.gridx++;
		this.add(this.buttonMin);
		gbc.gridx++;
		this.add(this.buttonMax);
	}

	public int getValue()
	{
		return (int) this.spinner.getValue();
	}

	@Override
	public void reset()
	{
		this.spinner.reset();
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
		this.label.setEnabledContent(enable);
		this.spinner.setEnabledContent(enable);
		this.buttonMax.setEnabledContent(enable);
		this.buttonMin.setEnabledContent(enable);
	}

	private void setMax()
	{
		this.setSelected(this.spinner.getMax());
	}

	private void setMin()
	{
		this.setSelected(this.spinner.getMin());
	}

	public void setSelected(int value)
	{
		if (value >= this.spinner.getMin() && value <= this.spinner.getMax()) spinner.setValue(value);
	}

	public void setValues(int min, int max)
	{
		this.spinner.setValues(min, max);
	}

	@Override
	public void updateLang()
	{
		label.updateLang();
	}
}
