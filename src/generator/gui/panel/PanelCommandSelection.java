package generator.gui.panel;

import generator.CommandGenerator;
import generator.gui.CLabel;
import generator.gui.CTabbedPane;
import generator.gui.combobox.CCombobox;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.registry.command.Command;
import generator.registry.command.Structure;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

/** Used to select a Command and a Structure. */
@SuppressWarnings("serial")
public class PanelCommandSelection extends CPanel implements IClickEvent
{
	private static final int COMMAND = 0;

	private CCombobox comboboxCommand;
	private Command[] commands;
	private CLabel labelCommand;
	private CTabbedPane tabbedPaneStructure;

	public PanelCommandSelection(CTabbedPane tabbedPaneStructure)
	{
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.tabbedPaneStructure = tabbedPaneStructure;

		this.labelCommand = new CLabel("GUI:command.command");
		this.comboboxCommand = new CCombobox(new String[] { "give", "tp" });

		this.comboboxCommand.addActionListener(new ClickEvent(this, COMMAND));

		this.add(this.labelCommand, this.gbc);

		this.gbc.gridx++;
		this.add(this.comboboxCommand, this.gbc);

		this.commands = CommandGenerator.getRegistry().getCommands();
		this.updateLang();
		this.onCommandChange();
	}

	public Command getSelectedCommand()
	{
		return this.commands[this.comboboxCommand.getSelectedIndex()];
	}

	@Override
	public void onClick(int componentID)
	{
		switch (componentID)
		{
			case COMMAND:
				this.onCommandChange();
				break;

			default:
				break;
		}
	}

	/** Called when the user changes the command. */
	private void onCommandChange()
	{
		Structure[] structures = this.getSelectedCommand().getStructures();
		String[] structureNames = new String[structures.length];
		this.tabbedPaneStructure.removeAll();

		for (int i = 0; i < structureNames.length; i++)
		{
			structureNames[i] = structures[i].getName();
			this.tabbedPaneStructure.add(structures[i].getName(), structures[i].getComponent());
		}

	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		String[] commandNames = new String[this.commands.length];
		for (int i = 0; i < commandNames.length; i++)
			commandNames[i] = this.commands[i].getName();

		this.comboboxCommand.setValues(commandNames);

		Structure[] structures = this.getSelectedCommand().getStructures();
		String[] structureNames = new String[structures.length];
		for (int i = 0; i < structureNames.length; i++)
			structureNames[i] = structures[i].getName();
	}

}
