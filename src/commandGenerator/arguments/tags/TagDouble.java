package commandGenerator.arguments.tags;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import commandGenerator.Generator;
import commandGenerator.gui.helper.components.CTextField;

public class TagDouble extends Tag
{

	private JLabel label;
	private CTextField textfield;
	private int type;
	private double value, min, max;

	public TagDouble()
	{
		this("");
	}

	public TagDouble(String id, String... applicable)
	{
		super(id, Tag.INT, applicable);
		value = 0.0D;

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
		textfield.setText(Double.toString(value));
		if (showPanel()) return;
		if (!isValueOk(textfield.getText())) return;
		value = Double.parseDouble(textfield.getText());

	}

	@Override
	public String commandStructure()
	{
		if (getId().equals("")) return Double.toString(value) + "D";
		return getId() + ":" + value + "D";
	}

	@Override
	public String display(int details, int lvls)
	{
		if (getId().equals("")) return Double.toString(value);
		return getName() + " : " + value;
	}

	public double getValue()
	{
		return value;
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

		String error = Generator.translate("WARNING:" + errorMessage);
		error = error.replaceAll("<min>", Double.toString(min));
		error = error.replaceAll("<max>", Double.toString(max));
		if (flag) JOptionPane.showMessageDialog(null, Generator.translate("WARNING:tag.error") + "\n" + error, Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);

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

}
