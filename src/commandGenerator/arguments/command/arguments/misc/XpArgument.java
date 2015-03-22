package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;

import javax.swing.JPanel;

import commandGenerator.arguments.command.arguments.IntArgument;
import commandGenerator.gui.helper.components.CCheckBox;

public class XpArgument extends IntArgument
{
	private CCheckBox box;

	public XpArgument()
	{
		super("xp.xp", true);
	}

	@Override
	public Component generateComponent()
	{
		Component entry = super.generateComponent();
		JPanel panel = new JPanel();
		panel.add(entry);
		panel.add(this.box);
		return panel;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.box = new CCheckBox("xp.levels", "GUI:xp.levels");
	}

	@Override
	public String generateCommand()
	{
		String value = super.generateCommand();
		if (value == null) return null;
		if (this.box.isSelected()) return value + "L";
		return value;
	}

}
