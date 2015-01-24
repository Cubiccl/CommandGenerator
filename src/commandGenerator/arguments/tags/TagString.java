package commandGenerator.arguments.tags;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TagString extends Tag
{

	private String[] choices;
	private String value;
	private JLabel label;
	private JTextField textfield;
	private JComboBox<String> box;

	public TagString(String id, String... applicable)
	{
		super(id, Tag.STRING, applicable);
		value = null;
		choices = null;
		value = null;

		label = new JLabel();
		textfield = new JTextField(15);

		panel.add(label, gbc);
		gbc.gridy++;
		panel.add(textfield, gbc);
	}

	public TagString()
	{
		this("");
	}

	@Override
	public void askValue()
	{
		label.setText(getDescription());
		if (choices == null)
		{
			textfield.setText(value);
			if (showPanel()) return;
			value = textfield.getText();
		} else
		{
			box.setSelectedIndex(0);
			if (showPanel()) return;
			value = (String) box.getSelectedItem();
		}
	}

	@Override
	public String display(int details, int lvls)
	{
		if (getId().equals("")) return value;
		return getName() + " : " + value;
	}

	public String getValue()
	{
		return value;
	}

	public Tag setOptions(String[] choices)
	{
		this.choices = choices;
		box = new JComboBox<String>(choices);
		panel.remove(textfield);
		panel.add(box);
		return this;
	}

	public TagString setValue(String value)
	{
		this.value = value;
		return this;
	}

	@Override
	public String commandStructure()
	{
		if (getId().equals("")) return "\"" + value + "\"";
		return getId() + ":\"" + value + "\"";
	}
}
