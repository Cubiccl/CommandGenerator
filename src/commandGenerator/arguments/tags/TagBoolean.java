package commandGenerator.arguments.tags;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import commandGenerator.main.Lang;

public class TagBoolean extends Tag
{

	private boolean value;
	private JLabel label;
	private JComboBox<String> combobox;

	public TagBoolean(String id, String... applicable)
	{
		super(id, Tag.BOOLEAN, applicable);
		value = false;

		combobox = new JComboBox<String>(new String[] { Lang.get("GENERAL:true"), Lang.get("GENERAL:false") });
		combobox.setPreferredSize(new Dimension(200, 20));
		label = new JLabel();
		panel.add(label, gbc);
		gbc.gridy++;
		panel.add(combobox, gbc);
	}

	@Override
	public void askValue()
	{

		label.setText(getDescription());
		combobox.setSelectedIndex(0);
		if (!value) combobox.setSelectedIndex(1);
		if (showPanel()) return;
		value = combobox.getSelectedIndex() == 0;

	}

	@Override
	public String display(int details, int lvls)
	{
		if (value) return getName() + " : " + Lang.get("GENERAL:yes");
		else return getName() + " : " + Lang.get("GENERAL:no");
	}

	public TagBoolean setValue(boolean value)
	{
		this.value = value;
		return this;
	}

	@Override
	public String commandStructure()
	{
		if (value) return getId() + ":1b";
		else return getId() + ":0b";
	}

}
