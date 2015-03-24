package commandGenerator.arguments.command.arguments.misc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.panel.PanelCommandSelection;
import commandGenerator.main.Lang;

public class CommandArgument extends Argument
{
	private String storedCommand;

	public CommandArgument()
	{
		super("execute.command", Argument.MISC, true, 1);
		this.setMaximumLength(100000);
		this.storedCommand = "/achievement give * @p";
	}

	@Override
	public String generateCommand()
	{

		final PanelCommandSelection panelCommand = new PanelCommandSelection(false);
		panelCommand.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Lang.get("GUI:execute.command")));
		panelCommand.setPreferredSize(new Dimension(1100, 650));
		panelCommand.setupFrom(this.storedCommand);

		// Setting the JDialog resizable
		panelCommand.addHierarchyListener(new HierarchyListener() {
			@Override
			public void hierarchyChanged(HierarchyEvent e)
			{
				Window window = SwingUtilities.getWindowAncestor(panelCommand);
				if (window instanceof Dialog)
				{
					Dialog dialog = (Dialog) window;
					if (!dialog.isResizable())
					{
						dialog.setResizable(true);
					}
				}
			}
		});
		JOptionPane.showMessageDialog(null, panelCommand, Lang.get("GUI:execute.select"), JOptionPane.QUESTION_MESSAGE);
		return panelCommand.generate();
	}

	@Override
	public Component generateComponent()
	{
		return null;
	}

	@Override
	public void initGui()
	{}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return data.size() > 0;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.storedCommand = "";
		for (int i = 0; i < data.size(); i++)
		{
			if (i != 0) this.storedCommand += " ";
			this.storedCommand += data.get(i);
		}
	}

}
