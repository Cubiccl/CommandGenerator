package commandGenerator.gui.helper.components.spinner;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.NumberFormatter;

import commandGenerator.gui.helper.components.button.ArrowButton;
import commandGenerator.gui.helper.components.icomponent.ISpin;

@SuppressWarnings("serial")
public class BaseSpinner extends JPanel
{
	private int[] values;
	private int selectedIndex;
	private JFormattedTextField textfield;
	private JButton buttonPlus, buttonMinus;
	private ISpin parent;

	public BaseSpinner(int[] values, ISpin parent)
	{
		super(new GridBagLayout());
		this.values = values;
		this.parent = parent;
		this.selectedIndex = 0;

		NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		formatter.setAllowsInvalid(false);

		this.textfield = new JFormattedTextField(formatter);
		this.textfield.setMinimumSize(new Dimension(180, 20));
		this.textfield.setPreferredSize(new Dimension(180, 20));

		this.buttonPlus = new ArrowButton(BasicArrowButton.NORTH);
		this.buttonPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				plus();
			}
		});
		this.buttonMinus = new ArrowButton(BasicArrowButton.SOUTH);
		this.buttonMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				minus();
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.textfield, gbc);

		JPanel panelButtons = new JPanel(new GridLayout(2, 1));
		panelButtons.add(this.buttonPlus);
		panelButtons.add(this.buttonMinus);
		panelButtons.setMinimumSize(new Dimension(18, 18));
		panelButtons.setPreferredSize(new Dimension(18, 18));
		gbc.gridx++;
		this.add(panelButtons, gbc);

		this.updateDisplay();
		this.textfield.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e)
			{}

			public void keyReleased(KeyEvent e)
			{
				search(e.getKeyCode(), true);
			}

			public void keyPressed(KeyEvent e)
			{
				search(e.getKeyCode(), false);
			}
		});
		this.textfield.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e)
			{
				validateSearch();
			}

			public void focusGained(FocusEvent e)
			{}
		});
	}

	private void search(int keyCode, boolean release)
	{
		switch (keyCode)
		{
			case KeyEvent.VK_ENTER:
				if (release) this.validateSearch();
				break;

			case KeyEvent.VK_UP:
				if (!release)
				{
					this.validateSearch();
					this.plus();
				}
				break;

			case KeyEvent.VK_DOWN:
				if (!release)
				{
					this.validateSearch();
					this.minus();
				}
				break;

			default:
				break;
		}
	}

	private void minus()
	{
		this.setValue(this.getFieldValue() - 1);
	}

	private void plus()
	{
		this.setValue(this.getFieldValue() + 1);
	}

	private void validateSearch()
	{
		int value = this.getFieldValue();
		boolean accepted = false;
		for (int i = 0; i < this.values.length; i++)
		{
			if (this.values[i] == value)
			{
				this.textfield.setText(String.valueOf(value));
				accepted = true;
			}
		}

		if (!accepted)
		{
			if (this.values.length == 0) this.textfield.setValue(0);
			if (value > this.values[this.values.length - 1]) this.textfield.setValue(this.values[this.values.length - 1]);
			else this.textfield.setValue(this.values[0]);
		}
	}

	private int getFieldValue()
	{
		Object object = this.textfield.getValue();
		int value = 0;
		if (object instanceof Long) value = (int) ((long) object);
		if (object instanceof Integer) value = (int) object;
		return value;
	}

	public BaseSpinner(int min, int max, ISpin parent)
	{
		this(getValuesFrom(min, max), parent);
	}

	private static int[] getValuesFrom(int min, int max) throws IllegalArgumentException
	{
		if (max < min) throw new IllegalArgumentException("The maximum value can't be inferior to the minimum value.");
		int[] values = new int[max - min + 1];
		for (int i = 0; i < values.length; i++)
		{
			values[i] = min + i;
		}
		return values;
	}

	public void setValue(int value)
	{
		for (int i = 0; i < this.values.length; i++)
		{
			if (this.values[i] == value) this.setSelectedIndex(i);
		}
	}

	private void setSelectedIndex(int index)
	{
		this.selectedIndex = index;
		this.updateDisplay();
		if (parent != null) this.parent.updateSpinner();
	}

	private void updateDisplay()
	{
		this.textfield.setValue(this.getValue());
	}

	public void setValues(int[] values)
	{
		this.values = values;
		this.selectedIndex = 0;
		this.updateDisplay();
	}

	public int getValue()
	{
		if (this.values.length == 0) return 0;
		return this.values[this.selectedIndex];
	}

}
