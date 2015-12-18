package generator.gui.panel;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

import generator.gui.CLabel;
import generator.gui.combobox.CCombobox;

/** Used to select a Command and a Structure. */
@SuppressWarnings("serial")
public class PanelCommandSelection extends CPanel
{
	private CLabel labelCommand;
	private CCombobox comboboxCommand;

	public PanelCommandSelection()
	{
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		this.labelCommand = new CLabel("GUI:command.command");
		this.comboboxCommand = new CCombobox(new String[] { "give", "tp" });

		this.add(this.labelCommand, this.gbc);
		
		this.gbc.gridx++;
		this.add(this.comboboxCommand, this.gbc);
	}

}
