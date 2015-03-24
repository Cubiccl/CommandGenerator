package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.DisplayHelper;

public class StringArgument extends Argument
{
	private CEntry entry;
	private JCheckBox box;

	public StringArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.STRING, isCompulsery, 1);
	}

	@Override
	public Component generateComponent()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(this.box, gbc);
		gbc.gridx++;
		panel.add(this.entry, gbc);
		return panel;
	}

	@Override
	public void initGui()
	{
		this.entry = new CEntry(this.getId(), "GUI:" + this.getId(), "");
		this.box = new JCheckBox();
		this.box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				entry.setEnabledContent(box.isSelected());
			}
		});
		this.entry.setEnabledContent(this.isCompulsery());
		this.box.setVisible(!this.isCompulsery());
	}

	@Override
	public String generateCommand()
	{
		String name = this.entry.getText();
		if (name.equals("") || name.contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}
		return name;
	}

	@Override
	public boolean isUsed()
	{
		return this.isCompulsery() || this.box.isSelected();
	}

	@Override
	public boolean matches(List<String> data)
	{
		return true;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.box.setSelected(true);
		this.entry.setTextField(data.get(0));
	}

}
