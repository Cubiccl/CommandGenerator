package commandGenerator.arguments.tags;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import commandGenerator.Generator;
import commandGenerator.gui.helper.components.CTextField;

public class TagFloat extends Tag
{

	private JLabel label;
	private CTextField textfield;
	private int type;
	private float value, min, max;

	public TagFloat()
	{
		this("");
	}

	public TagFloat(String id, String... applicable)
	{
		super(id, Tag.FLOAT, applicable);
		value = 0.0F;

		label = new JLabel();
		textfield = new CTextField(15);
		panel.add(label, gbc);
		gbc.gridy++;
		panel.add(textfield, gbc);
	}

	@Override
	public void askValue()
	{
		label.setText("<html>" + getDescription().replaceAll("\n", "<br />") + "</html>");
		textfield.setText(Float.toString(value));
		if (showPanel()) return;
		if (!isValueOk(textfield.getText())) return;
		value = Float.parseFloat(textfield.getText());

	}

	@Override
	public String commandStructure()
	{
		if (getId().equals("")) return Float.toString(value) + "F";
		return getId() + ":" + value + "F";
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

		String error = Generator.translate("WARNING:" + errorMessage);
		error = error.replaceAll("<min>", Float.toString(min));
		error = error.replaceAll("<max>", Float.toString(max));
		if (flag) JOptionPane.showMessageDialog(null, Generator.translate("WARNING:tag.error") + "\n" + error, Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);

		return !flag;
	}

	public void setMax(float max)
	{
		if (type == NONE) type = MAX;
		else if (type == MIN) type = BOTH;
		this.max = max;
	}

	public void setMin(float min)
	{
		if (type == NONE) type = MIN;
		else if (type == MAX) type = BOTH;
		this.min = min;
	}

	public Tag setValue(float value)
	{
		this.value = value;
		return this;
	}

}
