package generator.gui.panel;

import generator.CommandGenerator;
import generator.gui.button.CButton;
import generator.gui.checkbox.CCheckbox;
import generator.gui.textfield.CEntry;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class PanelCommand extends CPanelHorizontal implements IClickEvent
{
	private static final int EDITABLE = 0, COPY = 1;
	private CButton buttonCopy;
	private CCheckbox checkboxEditable;
	private CEntry entryCommand;

	public PanelCommand()
	{
		super();
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), CommandGenerator.translate("GUI:command.command")));

		this.entryCommand = new CEntry("GUI:command.command_entry");
		this.entryCommand.getTextfield().setEditable(false);
		this.entryCommand.getTextfield().setColumns(20);
		this.checkboxEditable = new CCheckbox("GUI:command.editable");
		this.checkboxEditable.addActionListener(new ClickEvent(this, EDITABLE));
		this.buttonCopy = new CButton("GUI:command.copy");
		this.buttonCopy.addActionListener(new ClickEvent(this, COPY));

		this.add(this.entryCommand);
		this.add(this.checkboxEditable);
		this.add(this.buttonCopy);
	}

	@Override
	public void onClick(int componentID)
	{
		switch (componentID)
		{
			case EDITABLE:
				this.entryCommand.getTextfield().setEditable(this.checkboxEditable.isSelected());
				break;

			case COPY:
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.entryCommand.getText()), null);
				break;

			default:
				break;
		}
	}

	@Override
	public void updateLang()
	{
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), CommandGenerator.translate("GUI:command.command")));
		super.updateLang();
	}

}