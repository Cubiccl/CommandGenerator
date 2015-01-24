package commandGenerator.arguments.tags;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import commandGenerator.main.Lang;

public class TagDouble extends Tag
{

	private double value, min, max;
	private int type;
	private JLabel label;
	private JTextField textfield;

	public TagDouble(String id, String... applicable)
	{
		super(id, Tag.INT, applicable);
		value = 0.0D;

		label = new JLabel();
		textfield = new JTextField(15);
		panel.add(label, gbc);
		gbc.gridy++;
		panel.add(textfield, gbc);
	}

	public TagDouble()
	{
		this("");
	}

	@Override
	public void askValue()
	{
		label.setText(getDescription());
		textfield.setText(Double.toString(value));
		if (showPanel()) return;
		if (!isValueOk(textfield.getText())) return;
		value = Double.parseDouble(textfield.getText());

	}

	@Override
	public String display(int details, int lvls)
	{
		if (getId().equals("")) return Double.toString(value);
		return getName() + " : " + value;
	}

	private boolean isValueOk(String value)
	{
		boolean flag = false;

		try
		{
			double test = Double.parseDouble(value);
			if ((type == Tag.MIN && test < min) || (type == Tag.MAX && test > max) || (type == Tag.BOTH && (test < min || test > max))) flag = true;
		} catch (Exception e)
		{
			flag = true;
		}

		String errorMessage = "number";
		if (type == MIN) errorMessage = "min";
		if (type == MAX) errorMessage = "max";
		if (type == BOTH) errorMessage = "integer_bound";

		String error = Lang.get("WARNING:" + errorMessage);
		error = error.replaceAll("<min>", Double.toString(min));
		error = error.replaceAll("<max>", Double.toString(max));
		if (flag) JOptionPane.showMessageDialog(null, Lang.get("WARNING:tag.error") + "\n" + error, Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);

		return !flag;
	}

	public TagDouble setOptions(int type, double max, double min)
	{
		this.type = type;
		this.max = max;
		this.min = min;

		return this;
	}

	public TagDouble setValue(double value)
	{
		this.value = value;
		return this;
	}

	@Override
	public String commandStructure()
	{
		return getId() + ":" + Double.toString(value) + "d";
	}

}
