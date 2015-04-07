package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

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
	public String generateCommand()
	{
		String value = super.generateCommand();
		if (value == null) return null;
		if (this.box.isSelected()) return value + "L";
		return value;
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
		this.box = new CCheckBox("GUI:xp.levels");
	}

	@Override
	public boolean matches(List<String> data)
	{
		String value = data.get(0);
		if (value.endsWith("L")) data.set(0, value.substring(0, value.length() - 1));
		return super.matches(data);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		String value = data.get(0);
		this.box.setSelected(value.endsWith("L"));
		if (value.endsWith("L")) value = value.substring(0, value.length() - 1);
		data.set(0, value);
		super.setupFrom(data);
	}

	public void reset()
	{
		super.reset();
		this.box.setSelected(false);
	}

}
