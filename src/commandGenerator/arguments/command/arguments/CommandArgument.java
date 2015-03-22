package commandGenerator.arguments.command.arguments;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.panel.PanelCommandSelection;
import commandGenerator.main.Lang;

public class CommandArgument extends Argument
{

	public CommandArgument()
	{
		super("execute.command", Argument.MISC, true, 1);
		this.setMaximumLength(100000);
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
	public String generateCommand()
	{

		final PanelCommandSelection panelCommand = new PanelCommandSelection(false);
		panelCommand.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Lang.get("GUI:execute.command")));
		panelCommand.setPreferredSize(new Dimension(1100, 650));
		// panelCommand.setSelectedCommand(Registry.getCommandFromId(storedCommand.split(" ")[0]));
		// panelCommand.tabOptions.setupFrom(Registry.getCommandFromId(storedCommand.split(" ")[0]).generateSetup(storedCommand));

		// Setting the JDialog resizable
		panelCommand.addHierarchyListener(new HierarchyListener() {
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
		return panelCommand.generateCommand();
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}
