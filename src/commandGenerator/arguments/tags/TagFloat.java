package commandGenerator.arguments.tags;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import commandGenerator.main.Lang;

public class TagFloat extends Tag
{

	private float value, min, max;
	private int type;
	private JLabel label;
	private JTextField textfield;

	public TagFloat(String id, String... applicable)
	{
		super(id, Tag.FLOAT, applicable);
		value = 0.0F;

		label = new JLabel();
		textfield = new JTextField(15);
		panel.add(label, gbc);
		gbc.gridy++;
		panel.add(textfield, gbc);
	}

	public TagFloat()
	{
		this("");
	}

	@Override
	public void askValue()
	{
		label.setText(getDescription());
		textfield.setText(Float.toString(value));
		if (showPanel()) return;
		if (!isValueOk(textfield.getText())) return;
		value = Float.parseFloat(textfield.getText());

	}

	@Override
	public String display(int details, int lvls)
	{
		if (getId().equals("")) return Float.toString(value);
		return getName() + " : " + value;
	}

	public float getValue()
	{
		return value;
	}

	private boolean isValueOk(String value)
	{
		boolean flag = false;

		try
		{
			double test = Float.parseFloat(value);
			if ((type == Tag.MIN && test < min) || (type == Tag.MAX && test > max) || (type == Tag.BOTH && (test < min || test > max))) flag = true;
		} catch (Exception e)
		{
			flag = true;
		}

		String errorMessage = "float";
		if (type == MIN) errorMessage = "min";
		if (type == MAX) errorMessage = "max";
		if (type == BOTH) errorMessage = "integer_bound";

		String error = Lang.get("WARNING:" + errorMessage);
		error = error.replaceAll("<min>", Float.toString(min));
		error = error.replaceAll("<max>", Float.toString(max));
		if (flag) JOptionPane.showMessageDialog(null, Lang.get("WARNING:tag.error") + "\n" + error, Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);

		return !flag;
	}

	public Tag setValue(float value)
	{
		this.value = value;
		return this;
	}

	@Override
	public String commandStructure()
	{
		if (getId().equals("")) return Float.toString(value) + "F";
		return getId() + ":" + value + "F";
	}

	public void setMin(float min)
	{
		if (type == NONE) type = MIN;
		else if (type == MAX) type = BOTH;
		this.min = min;
	}

	public void setMax(float max)
	{
		if (type == NONE) type = MAX;
		else if (type == MIN) type = BOTH;
		this.max = max;
	}

}
