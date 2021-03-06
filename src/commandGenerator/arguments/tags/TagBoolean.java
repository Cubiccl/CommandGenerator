package commandGenerator.arguments.tags;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import commandGenerator.Generator;

public class TagBoolean extends Tag
{

	private JComboBox<String> combobox;
	private JLabel label;
	private boolean value;

	public TagBoolean(String id, String... applicable)
	{
		super(id, Tag.BOOLEAN, applicable);
		value = false;

		combobox = new JComboBox<String>(new String[] { Generator.translate("GENERAL:true"), Generator.translate("GENERAL:false") });
		combobox.setMinimumSize(new Dimension(200, 20));
		combobox.setPreferredSize(new Dimension(200, 20));
		label = new JLabel();
		panel.add(label, gbc);
		gbc.gridy++;
		panel.add(combobox, gbc);
	}

	@Override
	public void askValue()
	{

		label.setText("<html>" + getDescription().replaceAll("\n", "<br />") + "</html>");
		combobox.setSelectedIndex(0);
		if (!value) combobox.setSelectedIndex(1);
		if (showPanel()) return;
		value = combobox.getSelectedIndex() == 0;

	}

	@Override
	public String commandStructure()
	{
		String text = getId();
		if (!text.equals("")) text += ":";
		if (value) return text + "1b";
		else return text + "0b";
	}

	@Override
	public String display(int details, int lvls)
	{
		if (value) return getName() + " : " + Generator.translate("GENERAL:yes");
		else return getName() + " : " + Generator.translate("GENERAL:no");
	}

	public boolean getValue()
	{
		return value;
	}

	public TagBoolean setValue(boolean value)
	{
		this.value = value;
		return this;
	}

}
