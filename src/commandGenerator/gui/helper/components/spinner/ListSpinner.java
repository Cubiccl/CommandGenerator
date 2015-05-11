package commandGenerator.gui.helper.components.spinner;

import java.awt.Dimension;
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
public class ListSpinner extends JPanel implements CComponent
{

	private BaseButton buttonMax, buttonMin;
	private CLabel label;
	private BaseSpinner spinner;
	private int[] values;

	public ListSpinner(String labelTextId, int[] values, ISpin parent)
	{
		super(new GridBagLayout());
		this.values = values;

		this.label = new CLabel(labelTextId);

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

		this.spinner = new BaseSpinner(this.values, parent);
		this.spinner.setPreferredSize(new Dimension(200, 20));
		this.spinner.setMinimumSize(new Dimension(200, 20));

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

	private void setMin()
	{
		if (this.values.length > 0) this.spinner.setValue(this.values[0]);
	}

	private void setMax()
	{
		if (this.values.length > 0) this.spinner.setValue(this.values[this.values.length - 1]);
	}

	@Override
	public void reset()
	{
		this.spinner.setValues(this.values);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
		this.spinner.setEnabled(enable);
		this.label.setEnabledContent(enable);
		this.buttonMax.setEnabledContent(enable);
		this.buttonMin.setEnabledContent(enable);
	}

	@Override
	public void updateLang()
	{
		this.label.updateLang();
	}

	public int getValue()
	{
		return (int) this.spinner.getValue();
	}

	public void setSelected(int value)
	{
		for (int i = 0; i < this.values.length; i++)
			if (this.values[i] == value) this.spinner.setValue(value);
	}

	public void setValues(int[] values)
	{
		this.values = values;
		this.reset();
	}

}
