package commandGenerator.gui.helper.components.spinner;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.button.BaseButton;
import commandGenerator.gui.helper.components.icomponent.ISpin;

@SuppressWarnings("serial")
public class ListSpinner extends JPanel implements CComponent
{

	private BaseButton buttonMax, buttonMin;
	private CLabel label;
	private JSpinner spinner;
	private int[] values;
	private ISpin parent;

	public ListSpinner(String labelTextId, int[] values, ISpin parent)
	{
		super(new GridBagLayout());
		this.values = values;
		this.parent = parent;

		this.label = new CLabel(labelTextId);

		this.buttonMax = new BaseButton("++");
		this.buttonMax.setSize(50, 20);
		this.buttonMax.setFont(new Font(this.buttonMax.getName(), Font.PLAIN, 8));
		this.buttonMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setMax();
			}
		});

		this.buttonMin = new BaseButton("--");
		this.buttonMin.setSize(50, 20);
		this.buttonMin.setFont(new Font(this.buttonMin.getName(), Font.PLAIN, 8));
		this.buttonMin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setMin();
			}
		});

		ArrayList<Integer> names = new ArrayList<Integer>();
		for (int value : this.values)
			names.add(value);
		if (names.size() == 0) names.add(0);

		this.spinner = new JSpinner(new SpinnerListModel(names));
		this.spinner.setPreferredSize(new Dimension(200, 20));
		this.spinner.setMinimumSize(new Dimension(200, 20));
		this.spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				select();
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

	private void select()
	{
		this.parent.updateSpinner();
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
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int value : this.values)
			values.add(value);
		if (values.size() == 0) values.add(0);

		this.spinner.setModel(new SpinnerListModel(values));
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
