package commandGenerator.arguments.tags;

import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import commandGenerator.Generator;
import commandGenerator.gui.helper.components.CTextField;

public class TagInt extends Tag
{

	private String[] choices;
	private String choicesId;
	private JLabel label;
	protected CTextField textfield;
	protected int value, type, min, max;
	private int[] values;

	public TagInt()
	{
		this("");
	}

	public TagInt(String id, String... applicable)
	{
		super(id, Tag.INT, applicable);
		value = 0;
		choicesId = null;
		choices = new String[0];

		panel = new JPanel(new GridBagLayout());
		gbc.gridy++;
		label = new JLabel();
	}

	@Override
	public void askValue()
	{

		panel.removeAll();
		gbc.gridy = 0;
		panel.add(label, gbc);
		label.setText("<html>" + getDescription().replaceAll("\n", "<br />") + "</html>");

		if (choicesId == null)
		{
			textfield = new CTextField(15);
			textfield.setText(Integer.toString(value));
			gbc.gridy++;
			panel.add(textfield, gbc);
			if (showPanel()) return;
			if (!isValueOk(textfield.getText())) return;
			value = Integer.parseInt(textfield.getText());
		} else
		{
			String[] names = new String[choices.length];
			for (int i = 0; i < names.length; i++)
				names[i] = Generator.translate("RESOURCES:" + choicesId + "." + choices[i]);

			JComboBox<String> combobox = new JComboBox<String>(names);
			if (value > 0 && value < combobox.getItemCount()) combobox.setSelectedIndex(value);
			if (this.values != null) for (int i = 0; i < this.values.length; i++)
				if (this.values[i] == this.value) combobox.setSelectedIndex(i);
			gbc.gridy++;
			panel.add(combobox, gbc);

			if (showPanel()) return;
			if (values == null) value = combobox.getSelectedIndex();
			else value = values[combobox.getSelectedIndex()];
		}

	}

	@Override
	public String commandStructure()
	{
		if (getId().equals("")) return Integer.toString(value);
		return getId() + ":" + value;
	}

	@Override
	public String display(int details, int lvls)
	{
		if (getId().equals("")) return Integer.toString(value);
		return getName() + " : " + value;
	}

	public int getValue()
	{
		return value;
	}

	private boolean isValueOk(String value)
	{
		boolean flag = false;

		try
		{
			int test = Integer.parseInt(value);
			if ((type == Tag.MIN && test < min) || (type == Tag.MAX && test > max) || (type == Tag.BOTH && (test < min || test > max))) flag = true;
		} catch (Exception e)
		{
			flag = true;
		}

		String errorMessage = "integer";
		if (type == MIN) errorMessage = "min";
		if (type == MAX) errorMessage = "max";
		if (type == BOTH) errorMessage = "integer_bound";

		String error = Generator.translate("WARNING:" + errorMessage);
		error = error.replaceAll("<min>", Integer.toString(min));
		error = error.replaceAll("<max>", Integer.toString(max));
		if (flag) JOptionPane.showMessageDialog(null, Generator.translate("WARNING:tag.error") + "\n" + error, Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);

		return !flag;
	}

	public void setChoicesId(String id)
	{
		this.choicesId = id;
	}

	public void setMax(int max)
	{
		if (type == NONE) type = MAX;
		else if (type == MIN) type = BOTH;
		this.max = max;
	}

	public void setMin(int min)
	{
		if (type == NONE) type = MIN;
		else if (type == MAX) type = BOTH;
		this.min = min;
	}

	public TagInt setValue(int value)
	{
		this.value = value;
		return this;
	}

	public void setChoices(String[] choices)
	{
		this.choices = choices;
	}

	public void setValues(String[] values)
	{
		this.values = new int[values.length];
		for (int i = 0; i < values.length; i++)
		{
			try
			{
				this.values[i] = Integer.parseInt(values[i]);
			} catch (Exception e)
			{
				this.values[i] = -1;
				System.out.println("Error creating values for tag : " + this.getId());
			}
		}
	}

}
