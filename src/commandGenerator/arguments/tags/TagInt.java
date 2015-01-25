package commandGenerator.arguments.tags;

import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import commandGenerator.main.Lang;

public class TagInt extends Tag
{

	protected int value, type, min, max;
	private JLabel label;
	protected JTextField textfield;
	private String choicesId;
	private int[] values;

	public TagInt(String id, String... applicable)
	{
		super(id, Tag.INT, applicable);
		value = 0;
		choicesId = null;

		panel = new JPanel(new GridBagLayout());
		gbc.gridy++;
		label = new JLabel();
	}

	public TagInt()
	{
		this("");
	}

	@Override
	public void askValue()
	{

		panel.removeAll();
		gbc.gridy = 0;
		panel.add(label, gbc);
		label.setText(getDescription());

		if (choicesId == null)
		{
			textfield = new JTextField(15);
			textfield.setText(Integer.toString(value));
			gbc.gridx++;
			panel.add(textfield, gbc);
			if (showPanel()) return;
			if (!isValueOk(textfield.getText())) return;
			value = Integer.parseInt(textfield.getText());
		} else
		{
			String[] names = new String[max - min];
			for (int i = 0; i < names.length; i++)
				names[i] = Lang.get("RESOURCES:" + choicesId + "_" + i);

			JComboBox<String> combobox = new JComboBox<String>(names);
			gbc.gridx++;
			panel.add(combobox, gbc);

			if (showPanel()) return;
			if (values == null) value = combobox.getSelectedIndex();
			else value = values[combobox.getSelectedIndex()];
		}

	}

	@Override
	public String display(int details, int lvls)
	{
		if (getId().equals("")) return Integer.toString(value);
		return getName() + " : " + value;
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

		String error = Lang.get("WARNING:" + errorMessage);
		error = error.replaceAll("<min>", Integer.toString(min));
		error = error.replaceAll("<max>", Integer.toString(max));
		if (flag) JOptionPane.showMessageDialog(null, Lang.get("WARNING:tag.error") + "\n" + error, Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);

		return !flag;
	}

	public TagInt setChoices(String id)
	{
		this.choicesId = id;
		return this;
	}

	public TagInt setValue(int value)
	{
		this.value = value;
		return this;
	}

	@Override
	public String commandStructure()
	{
		if (getId().equals("")) return Integer.toString(value);
		return getId() + ":" + value;
	}

	public int getValue()
	{
		return value;
	}

	public void setMin(int min)
	{
		if (type == NONE) type = MIN;
		else if (type == MAX) type = BOTH;
		this.min = min;
	}

	public void setMax(int max)
	{
		if (type == NONE) type = MAX;
		else if (type == MIN) type = BOTH;
		this.max = max;
	}

}
