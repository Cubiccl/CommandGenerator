package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.button.CMessageButton;
import generator.gui.panel.CPanelHorizontal;
import generator.gui.textfield.CCheckEntry;
import generator.gui.textfield.CEntry;
import generator.main.GenerationException;

import java.awt.Component;

/** A text Argument. */
public class StringArgument extends Argument
{
	/** The Help button if this has info. */
	private CMessageButton buttonHelp;
	/** The Entry if not compulsory. */
	private CCheckEntry checkEntry;
	/** The Entry for user input. */
	private CEntry entry;
	/** True if a button displaying additional information should be added. */
	private boolean hasInfo;
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
		this.hasInfo = false;
	}

	/** Adds a help button, displaying information about the value to input. */
	public void addInfo()
	{
		this.hasInfo = true;
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

		if (this.hasInfo)
		{
			this.buttonHelp = new CMessageButton();
			this.updateLang();
			this.panel.add(this.buttonHelp);
		}
	}

	@Override
	public String generate() throws GenerationException
	{
		this.verifyValue();
		return this.getValue();
	}

	@Override
	public Component getComponent()
	{
		return this.panel;
	}

	/** @return The value input by the user. */
	protected String getValue()
	{
		// If compulsory use the entry, else use the check entry.
		return this.isCompulsory() ? this.entry.getText() : this.checkEntry.getText();
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
		if (this.buttonHelp != null)
		{
			this.buttonHelp.setTitle(CommandGenerator.translate("HELP:main").replaceAll("<object>", CommandGenerator.translate("GUI:" + this.textID)));
			this.buttonHelp.setMessage(CommandGenerator.translate("HELP:" + this.textID));
		}
	}

	/** Verifies the value input by the user. Called when generating.
	 * 
	 * @throws GenerationException if the value cannot be accepted. */
	protected void verifyValue() throws GenerationException
	{
		if (!this.hasSpaces && this.getValue().contains(" ")) throw new GenerationException(CommandGenerator.translate("GUI:error.space").replaceAll("<value>",
				this.getValue()));
	}
}
