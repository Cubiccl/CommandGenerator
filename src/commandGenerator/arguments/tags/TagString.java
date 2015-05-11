package commandGenerator.arguments.tags;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import commandGenerator.gui.helper.components.CTextField;

public class TagString extends Tag
{

	private JComboBox<String> box;
	private String[] choices;
	private JLabel label;
	private CTextField textfield;
	private String value;

	public TagString()
	{
		this("");
	}

	public TagString(String id, String... applicable)
	{
		super(id, Tag.STRING, applicable);
		value = null;
		choices = null;
		value = null;

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
	public String commandStructure()
	{
		if (getId().equals("")) return "\"" + value + "\"";
		return getId() + ":\"" + value + "\"";
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
}
