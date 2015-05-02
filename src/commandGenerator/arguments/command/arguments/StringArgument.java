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
	private JCheckBox box;
	private CEntry entry;
	private boolean spacesAllowed;

	public StringArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
		this.spacesAllowed = false;
	}

	@Override
	public String generateCommand()
	{
		String name = this.entry.getText();
		if (!this.spacesAllowed && (name.equals("") || name.contains(" ")))
		{
			DisplayHelper.warningName();
			return null;
		}
		return name;
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
		this.entry = new CEntry("GUI:" + this.getId(), "");
		this.box = new JCheckBox();
		this.box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				entry.setEnabledContent(box.isSelected());
			}
		});
		this.entry.setEnabledContent(this.isCompulsery());
		this.box.setVisible(!this.isCompulsery());
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
		this.entry.setEnabledContent(true);
		String text = "";
		for (int i = 0; i < data.size(); i++)
		{
			if (i > 0) text += " ";
			text += data.get(i);
		}
		this.entry.setTextField(text);
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof StringArgument)) return;
		if (!this.isCompulsery()) this.box.setSelected(toMatch.isUsed());
		this.entry.setTextField(((StringArgument) toMatch).entry.getText());
	}

	@Override
	public void reset()
	{
		this.entry.reset();
		if (!this.isCompulsery())
		{
			this.box.setSelected(false);
			this.entry.setEnabledContent(false);
		}
	}

	public StringArgument setSpacesAllowed()
	{
		this.spacesAllowed = true;
		this.setMaximumLength(100);
		return this;
	}

}
