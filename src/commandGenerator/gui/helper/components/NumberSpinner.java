package commandGenerator.gui.helper.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class NumberSpinner extends JPanel implements CComponent
{

	private JButton buttonMax, button0;
	private GridBagConstraints gbc = new GridBagConstraints();
	private String id;
	private CLabel label;
	private int max, min;
	private JSpinner spinner;

	public NumberSpinner(String id, String title, int min, int max, final ISpin parent)
	{
		super(new GridBagLayout());

		label = new CLabel(title);
		spinner = new JSpinner(new SpinnerNumberModel(min, min, max, 1));
		spinner.setPreferredSize(new Dimension(200, 20));
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0)
			{
				if (parent != null) parent.updateSpinner();
			}
		});

		buttonMax = new JButton("++");
		buttonMax.setPreferredSize(new Dimension(50, 20));
		buttonMax.setFont(new Font(buttonMax.getName(), Font.PLAIN, 8));
		buttonMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				setMax();
			}
		});
		button0 = new JButton("--");
		button0.setPreferredSize(new Dimension(50, 20));
		button0.setFont(new Font(button0.getName(), Font.PLAIN, 8));
		button0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				setMin();
			}
		});

		this.max = max;
		this.min = min;
		this.id = id;

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(label);
		gbc.gridx++;
		add(spinner);
		gbc.gridx++;
		add(button0);
		gbc.gridx++;
		add(buttonMax);
	}

	public int getValue()
	{
		return (int) spinner.getValue();
	}

	@Override
	public void reset()
	{
		if (min <= 0 && max >= 0) spinner.setValue(0);
		else spinner.setValue(0);
	}

	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
		label.setEnabled(enable);
		spinner.setEnabled(enable);
	}

	private void setMax()
	{
		setSelected(max);
	}

	private void setMin()
	{
		setSelected(min);
	}

	public void setSelected(int value)
	{
		if (value >= min && value <= max) spinner.setValue(value);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(CGConstants.DATAID_NONE)) spinner.setValue(data.get(id));
	}

	public void setValues(int min, int max)
	{
		this.max = max;
		this.min = min;
		int selected = (int) spinner.getValue();
		if (selected <= max) spinner.setModel(new SpinnerNumberModel(selected, min, max, 1));
		else spinner.setModel(new SpinnerNumberModel(min, min, max, 1));
	}

	public void updateLang()
	{
		label.updateLang();
	}
}
