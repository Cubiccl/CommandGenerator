package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.panel.CPanelHorizontal;
import generator.gui.textfield.CCheckEntry;
import generator.gui.textfield.CEntry;
import generator.main.GenerationException;

import java.awt.Component;

/** A text Argument. */
public class StringArgument extends Argument
{
	/** The Entry if not compulsory. */
	private CCheckEntry checkEntry;
	/** The Entry for user input. */
	private CEntry entry;
	/** True if the input String can contain spaces. */
	private boolean hasSpaces;
	/** The Panel containing all the GUI. */
	private CPanelHorizontal panel;
	/** The ID of the Label. */
	private String textID;

	/** Creates a new String Argument.
	 * 
	 * @param isCompulsory - True if compulsory.
	 * @param textID - The ID of the Label. */
	public StringArgument(boolean isCompulsory, String textID)
	{
		super(isCompulsory, 1);
		this.textID = textID;
		this.hasSpaces = false;
	}

	@Override
	public void createGui()
	{
		this.panel = new CPanelHorizontal();

		if (this.isCompulsory())
		{
			this.entry = new CEntry("GUI:" + this.textID);
			this.panel.add(this.entry);
		} else
		{
			this.checkEntry = new CCheckEntry("GUI:" + this.textID);
			this.panel.add(this.checkEntry);
		}
	}

	@Override
	public String generate() throws GenerationException
	{
		// If compulsory use the entry, else use the check entry.

		if (!this.hasSpaces)
		{
			if (this.isCompulsory() && this.entry.getText().contains(" ")) throw new GenerationException(CommandGenerator.translate("GUI:error.space"));
			else if (!this.isCompulsory() && this.checkEntry.getText().contains(" ")) throw new GenerationException(
					CommandGenerator.translate("GUI:error.space"));
		}
		return this.isCompulsory() ? this.entry.getText() : this.checkEntry.getText();
	}

	@Override
	public Component getComponent()
	{
		return this.panel;
	}

	@Override
	public boolean isUsed()
	{
		return this.isCompulsory() || this.checkEntry.getCheckbox().isSelected();
	}

	/** Allows this String Argument to generate text with spaces. */
	public void setHasSpaces()
	{
		this.hasSpaces = true;
		this.setLength(-1);
	}

	@Override
	public void updateLang()
	{
		if (this.entry != null) this.entry.updateLang();
		if (this.checkEntry != null) this.checkEntry.updateLang();
	}

}
