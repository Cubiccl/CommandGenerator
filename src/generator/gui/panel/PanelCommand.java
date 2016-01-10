package generator.gui.panel;

import generator.gui.button.CButton;
import generator.gui.checkbox.CCheckbox;
import generator.gui.textfield.CEntry;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.Text;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

/** Displays the command and several useful buttons such as copy, edit... */
@SuppressWarnings("serial")
public class PanelCommand extends CPanelHorizontal implements IClickEvent
{
	private static final int EDITABLE = 0, COPY = 1;
	private CButton buttonCopy;
	private CCheckbox checkboxEditable;
	private CEntry entryCommand;
	private Text title;

	public PanelCommand()
	{
		super();
		this.title = new Text("GUI", "object.command");
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), this.title.getValue()));

		this.entryCommand = new CEntry(new Text("GUI", "command.command"));
		this.entryCommand.getTextfield().setEditable(false);
		this.entryCommand.getTextfield().setColumns(20);
		this.checkboxEditable = new CCheckbox(new Text("GUI", "command.editable"));
		this.checkboxEditable.addActionListener(new ClickEvent(this, EDITABLE));
		this.buttonCopy = new CButton(new Text("GUI", "command.copy"));
		this.buttonCopy.addActionListener(new ClickEvent(this, COPY));

		this.add(this.entryCommand);
		this.add(this.checkboxEditable);
		this.add(this.buttonCopy);
	}

	@Override
	public void onEvent(int componentID, AWTEvent event)
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

	public void setCommand(String command)
	{
		this.entryCommand.setText(command);
	}

	@Override
	public void updateLang()
	{
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), this.title.getValue()));
		super.updateLang();
	}
}
